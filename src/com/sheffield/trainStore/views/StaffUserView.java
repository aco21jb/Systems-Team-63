package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.OrderStatus;
import com.sheffield.trainStore.model.Role;

import com.sheffield.trainStore.views.ProductsPage;
import com.sheffield.trainStore.views.OrderPanelPage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The StaffUserView class represents the GUI window for promoting users to Moderator.
 */
public class StaffUserView extends JFrame {


    private final DatabaseOperationsUser databaseOperationsUser;
    List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();


    private JTable orderTable;
    DefaultTableModel orderTableModel = new DefaultTableModel();

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    private JPanel nmanagerPanel;


    private JPanel newOrderPanel;
    private JPanel oldOrderPanel;

    private JPanel productPanel;
    private JPanel bottomPanel;

    private JPanel productsTablePanel;
    private JPanel productsButtonPanel;

    private JScrollPane scrollPane;


    private JTextField orderNumberField ;
    private JTextField orderDateField ;
    private JTextField orderUserField ;

    private JTextField productCodeField ;
    private JTextField productNameField ;
    private JTextField productRetailPriceField ;
    private JTextField productStockField ;

    private JTextField gaugeField ;
    private JTextField eraCodeField ;
    private JTextField dccCodeField ;
    private JTextField productTypeField ;



    /**
     * Constructor for the StaffUserView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public StaffUserView(Connection connection) throws SQLException {

        databaseOperationsUser = new DatabaseOperationsUser();

        // Set properties for the new window
        setTitle("Staff");
        setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Create a JPanel for the new window
        JPanel mainPanel = new JPanel();
        // mainPanel.setLayout(new FlowLayout());
        mainPanel.setLayout(new GridLayout());


        JPanel managerPanel = new JPanel();
        JPanel newOrderPanel = new JPanel();
        JPanel productPanel = new JPanel();
        JPanel oldOrderPanel = new JPanel();
        JPanel updateStockPanel = new JPanel();


        JPanel productsTablePanel = new JPanel();
        JPanel productViewPanel = new JPanel();
        JPanel productsButtonPanel = new JPanel();
        // JPanel productViewPanel = new JPanel();


        tabbedPane = new JTabbedPane();
        tabbedPane.setSize(200,800);

        JButton viewProductButton = new JButton("View Product");
        JButton newProductButton = new JButton("Add Product");
        JButton editProductButton = new JButton("Edit Product");
        JButton deleteProductButton = new JButton("Delete Product");


        productsButtonPanel.add(viewProductButton);
        productsButtonPanel.add(newProductButton);
        productsButtonPanel.add(editProductButton);
        productsButtonPanel.add(deleteProductButton);


        JButton updateStockProductButton = new JButton("Update Product Stock");

        // productsButtonPanel.add(updateStockProductButton);

        JLabel productCodeLabel = new JLabel("Product Code:");
        JLabel productNameLabel = new JLabel("Product Name:");
        JLabel productRetailPriceLabel = new JLabel(" RetailPrice:");
        JLabel productStockLabel = new JLabel("Stock:");

        // JLabel productPriceLabel = new JLabel("Price:");
        // JLabel productStockabel = new JLabel("Stock:");
        // JLabel productGuageabel = new JLabel("Gauge:");
        // JLabel productEraCodeLabel = new JLabel("Era code:");
        // JLabel productProductTypeLabel = new JLabel("Product Type:");

        productCodeField = new JTextField(20);
        productCodeField.setEditable(false);
        // productCodeField.setBackground(BLACK);

        productNameField = new JTextField(20);
        productRetailPriceField = new JTextField(20);

        productStockField = new JTextField(20);
        // productStockField.setEnabled(false);

        productViewPanel.add(productCodeLabel);
        productViewPanel.add(productCodeField);

        productViewPanel.add(productNameLabel);
        productViewPanel.add(productNameField);

        productViewPanel.add(productRetailPriceLabel);
        productViewPanel.add(productRetailPriceField);

        productViewPanel.add(productStockLabel);
        productViewPanel.add(productStockField);

        productViewPanel.setLayout(new GridLayout(4,4));


        getContentPane().add(mainPanel);

        tabbedPane.addTab("Confirmed Orders", newOrderPanel);
        tabbedPane.addTab("Fullfilled Orders",  oldOrderPanel);
        tabbedPane.addTab("Products",  productPanel);
        tabbedPane.addTab("Update Stock",  updateStockPanel);


        if (listOfRolesForCurrentUser.contains(Role.MANAGER) ) {
            // NOTE : If you change the Title, need to chage the name in Change Listener method
             tabbedPane.addTab("Manager",  managerPanel);
            //  managerPanel.add (managerButton);
        }


        mainPanel.add(tabbedPane,BorderLayout.CENTER);

        // populateOrderTable(connection,  OrderStatus.FULFILLED);

        ChangeListener tabbedPaneChangeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent){

                JTabbedPane localTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = localTabbedPane.getSelectedIndex();

                // System.out.println(changeEvent.getSource());
                System.out.println("Tab changed to: " + localTabbedPane.getTitleAt(index));
                // System.out.println("Tab changed to: " + localTabbedPane.getTitleAt(index)).ge);

                if (localTabbedPane.getTitleAt(index) == "Manager") {
                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.MANAGER) ) {
                        // Open a new window (replace NewWindowClass with the actual class you want to open)
                        //    PromoteUserView newWindow = null;
                        ManagerUserView newWindow = null;

                        try {
                            newWindow = new ManagerUserView(connection);

                            // managerPanel.removeAll();
                            // managerPanel.add(new ManagerUserView (connection));

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        newWindow.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "You are not authorized to view this!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }


                if (localTabbedPane.getTitleAt(index) == "Products") {
                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {

                        // ProductsPage newWindow = null;

                        try {
                            // newWindow = new ProductsPage(connection);
                            productsTablePanel.removeAll();

                            productsTablePanel.add(new ProductsPanelPage (connection));
                            productPanel.add(productsTablePanel);
                            productPanel.add(productViewPanel);

                            productPanel.add(productsButtonPanel);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        // newWindow.setVisible(true);
                    }
                }

                if (localTabbedPane.getTitleAt(index) == "Fullfilled Orders") {
                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {

                        // oldOrderPanel.remove(OrderPanelPage);
                        try {
                            // newWindow = new ProductsPage(connection);
                            // oldOrderPanel.remove(OrderPanelPage);
                            oldOrderPanel.removeAll();

                            oldOrderPanel.add(new OrderPanelPage (connection, OrderStatus.FULFILLED));

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        // newWindow.setVisible(true);
                    }
                }

                if (localTabbedPane.getTitleAt(index) == "Confirmed Orders") {
                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {

                        // oldOrderPanel.remove(OrderPanelPage);
                        try {
                            // newWindow = new ProductsPage(connection);
                            newOrderPanel.removeAll();
                            newOrderPanel.add(new OrderPanelPage (connection, OrderStatus.PENDING));

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        // newWindow.setVisible(true);
                    }
                }



                if (localTabbedPane.getTitleAt(index) == "Update Stock") {
                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {

                        // ProductsPage newWindow = null;

                        try {
                            // newWindow = new ProductsPage(connection);
                            updateStockPanel.removeAll();

                            updateStockPanel.add(new ProductsPanelPage (connection));

                            updateStockPanel.add(productStockLabel);
                            updateStockPanel.add(productStockField);
                            updateStockPanel.add(updateStockProductButton);

                            // productPanel.add(productsTablePanel);
                            // productPanel.add(productViewPanel);

                            // productPanel.add(productsButtonPanel);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        // newWindow.setVisible(true);
                    }
                }


            }
        };


        tabbedPane.addChangeListener(tabbedPaneChangeListener);


        // Add action listener to the button
        updateStockProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if (isUserAuthorised(Role.ADMIN)) {
                    // Get the selected user from the combo box
                    // productStockField.setEnabled(false);

                    int row = ProductsPanelPage.productsTable.getSelectedRow();

                    // Check if a user is selected
                    if (row >= 0) {


                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a user.");
                    }

            }
        });



            // Add action listener to the button
            viewProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // if (isUserAuthorised(Role.ADMIN)) {
                        // Get the selected user from the combo box

                        int row = ProductsPanelPage.productsTable.getSelectedRow();
                        // String selectedUser = String.valueOf(staffComboBox.getSelectedItem());
                        // String emailId = String.valueOf(ProductsPage.products.getValueAt(row,0)) ;



                        // Check if a user is selected
                        if (row >= 0) {

                            System.out.println(ProductsPanelPage.productsTable.getValueAt(row,0));
                            System.out.println(ProductsPanelPage.productsTable.getValueAt(row,1));
                            System.out.println(ProductsPanelPage.productsTable.getValueAt(row,2));

                            productCodeField.setText((String) ProductsPanelPage.productsTable.getValueAt(row,0));
                            productNameField.setText((String) ProductsPanelPage.productsTable.getValueAt(row,1));
                            // productStockField.setText((String) ProductsPanelPage.productsTable.getValueAt(row,2));
                            // productRetailPriceField.setText((String) ProductsPanelPage.productsTable.getValueAt(row,3));

                            // productRetailPriceField = ProductsPanelPage.productsTable.getValueAt(row,0);


                        } else {
                            JOptionPane.showMessageDialog(null, "Please select a user.");
                        }


                }
            });
    }


    /**
     * Populates the user combo box with user data from the database.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */

    private void populateOrderTable(Connection connection,  OrderStatus orderStatus) throws SQLException {


        ResultSet resultSet = databaseOperationsUser.getOrderDetails(connection, orderStatus);

        // Populate the JTable with the query results
        while (resultSet.next()) {


            // String emailID = resultSet.getString("email");
            // staffComboBox.addItem(emailID);

            orderTableModel.addRow(new Object[]{
                resultSet.getString("orderNumber"),
                resultSet.getString("orderDate"),
                resultSet.getString("userID")

            });
        }
        resultSet.close();

    }


    private boolean isUserAuthorised(Role role) {
        // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
        for (Role roleForCurrentUser : listOfRolesForCurrentUser) {
            if (roleForCurrentUser.equals(role)) {
                return true;
            }
        }
        return false;
    }


}
