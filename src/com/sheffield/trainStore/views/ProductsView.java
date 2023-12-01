package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.Product;
import com.sheffield.trainStore.model.Order;
import com.sheffield.trainStore.model.OrderLine;
import com.sheffield.trainStore.model.OrderStatus;
import com.sheffield.trainStore.model.CurrentUser;
import com.sheffield.trainStore.model.CurrentUserManager;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import java.math.BigDecimal;
public class ProductsView extends JFrame {

    private JComboBox<String> productComboBox;
    private final DatabaseOperations databaseOperations;
    private List<OrderLine> order = new ArrayList<>();
    private int orderNumber;
    private int orderLineNumber;
    private OrderStatus orderStatus = null;

    /**
     * Constructor for the PromoteUserView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public ProductsView(Connection connection) throws SQLException {
        // Initialize DatabaseOperations

        databaseOperations = new DatabaseOperations();

        // Set properties for the new window
        setTitle("Products");
        setSize(400, 200);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        // Create a JPanel for the new window
        JPanel panel = new JPanel();
        add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(8, 3));
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        panel.setBorder(blackline);

        productComboBox = new JComboBox<>();
        productComboBox.setSize (300, 20);

        // Populate the combo box with user data
        populateProductComboBox(connection);
        panel.add(productComboBox);


        JButton addProductButton = new JButton("Add Product to Order");
        panel.add(addProductButton);

        panel.add(new JLabel());
        panel.add(new JLabel());

        JButton confirmOrderButton = new JButton("Confirm Order");

        panel.add(new JLabel());
        panel.add(confirmOrderButton);

        JButton viewOrderButton = new JButton("View Order");

        panel.add(new JLabel());
        panel.add(viewOrderButton);


        // Add action listener to the button
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get the selected product from the combo box


                String selectedProduct = String.valueOf(productComboBox.getSelectedItem());
                orderStatus = OrderStatus.PENDING;

                // Check if a user is selected
                if (selectedProduct != null) {

                    try {
                        ResultSet productResult = databaseOperations.getProduct(connection, selectedProduct);
                        if (productResult.next()) {
                            String selectedProductCode = productResult.getString("productCode");
                            BigDecimal selectedProductPrice = productResult.getBigDecimal("retailPrice");
                            int selectedProductStock = productResult.getInt("stock");
                            try {
                                String quantityString = JOptionPane.showInputDialog("Quantity: ");
                                int quantity = Integer.parseInt(quantityString);
                                if (quantity <= selectedProductStock) {

                                    orderStatus = OrderStatus.PENDING;
                                    Date tempDate = new Date();
                                    CurrentUser tempCurrentUser = CurrentUserManager.getCurrentUser();
                                    String tempCurrentUserID = tempCurrentUser.getUserId();

                                    Order tempOrder = new Order(orderNumber, tempDate, orderStatus, tempCurrentUserID);
                                    databaseOperations.addOrder(connection, tempOrder);

                                    OrderLine currentOrderLine = new OrderLine(orderNumber, orderLineNumber, quantity,
                                            selectedProductPrice, selectedProductCode);
                                    order.add(currentOrderLine);
                                    databaseOperations.addOrderLine(connection, currentOrderLine);

                                } else {
                                    JOptionPane.showMessageDialog(null, "Item out of stock.");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid quantity.");
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product.");
                }
            }
        });

        confirmOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                orderStatus = OrderStatus.CONFIRMED;
                Date currentDate = new Date();
                CurrentUser currentUser = CurrentUserManager.getCurrentUser();
                String currentUserID = currentUser.getUserId();

                Order currentOrder = new Order(orderNumber, currentDate, orderStatus, currentUserID, order);
                try {
                    databaseOperations.addOrder(connection, currentOrder);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        viewOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    OrderView orderView = new OrderView(connection, orderNumber);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    /**
     * Populates the products combo box with product data from the database.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    private void populateProductComboBox(Connection connection) throws SQLException {

        ResultSet resultSet = databaseOperations.getProducts(connection);


        // Populate the JTable with the query results
        while (resultSet.next()) {
            String productName = resultSet.getString("productName");
            productComboBox.addItem(productName);
        }

    }
}
