package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.Product;
import com.sheffield.trainStore.model.OrderLine;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import java.math.BigDecimal;
public class ProductsView extends JFrame {

    private JComboBox<String> productComboBox;
    private final DatabaseOperations databaseOperations;
    private List<OrderLine> order;
    private int orderNumber = 1;
    private int orderLineNumber = 1;

    /**
     * Constructor for the PromoteUserView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public ProductsView(Connection connection) throws SQLException {
        // Initialize DatabaseOperations
        // DatabaseOperationsUser databaseOperationsUser = new DatabaseOperationsUser();
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

        /*JLabel emailLabel = new JLabel("Enter Email ID:");
        JTextField emailField= new JTextField(20);*/

        JButton confirmOrderButton = new JButton("Confirm Order");

        /*panel.add(emailLabel);
        panel.add(emailField);*/

        panel.add(new JLabel());
        panel.add(confirmOrderButton);


        // Add action listener to the button
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if (isUserAuthorised(Role.ADMIN)) {
                // Get the selected user from the combo box


                String selectedProduct = String.valueOf(productComboBox.getSelectedItem());

                // Check if a user is selected
                if (selectedProduct != null) {

                    // Ask for confirmation
                    /*int dialogResult = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to Remove   " + selectedUser + " from Staff Role?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);

                    // Check the user's choice
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        // User confirmed, promote the selected user to Moderator
                        // databaseOperationsUser.promoteToStaff(connection, selectedUser);
                        databaseOperationsUser.removeFromStaff(connection, selectedUser);
                        staffComboBox.removeItem(selectedUser);

                        JOptionPane.showMessageDialog(null, selectedUser + " has been Removed from Staff.");
                    } else {
                        // User canceled the action
                        JOptionPane.showMessageDialog(null, "Canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                    }*/

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
                                    OrderLine currentOrder = new OrderLine(orderNumber, orderLineNumber, quantity,
                                            selectedProductPrice, selectedProductCode);
                                    order.add(currentOrder);
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
                // } else {
                //     JOptionPane.showMessageDialog(null, "You are not an ADMIN!", "Error", JOptionPane.ERROR_MESSAGE);
                // }
            }
        });

        confirmOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        // Add action listener to the button
        /*promoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if (isUserAuthorised(Role.ADMIN)) {
                // Get the selected user from the combo box

                String emailId = emailField.getText();


                if (! databaseOperationsUser.IsAlreadyStaff(connection, emailId)) {
                    // Ask for confirmation
                    int dialogResult = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to promote " + emailId + " to Staff?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);

                    // Check the user's choice
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        // User confirmed, promote the selected user to Moderator
                        databaseOperationsUser.promoteToStaff(connection, emailId);
                        staffComboBox.removeItem(emailId);

                        JOptionPane.showMessageDialog(null, emailId + " has been promoted to Staff.");
                    } else {
                        // User canceled the action
                        JOptionPane.showMessageDialog(null, "canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "User doesn't exists or already a Staff");
                }

            }
        });*/
    }

    /**
     * Populates the products combo box with product data from the database.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    private void populateProductComboBox(Connection connection) throws SQLException {

        ResultSet resultSet = databaseOperations.getAllProducts(connection);


        // Populate the JTable with the query results
        while (resultSet.next()) {
            String productName = resultSet.getString("productName");
            productComboBox.addItem(productName);
        }

    }
}
