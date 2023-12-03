package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.OrderStatus;
import com.sheffield.trainStore.model.Role;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
    private final DatabaseOperations databaseOperations;

    List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();

    DefaultTableModel orderTableModel = new DefaultTableModel();

    private JTabbedPane tabbedPane;

    // private JScrollPane scrollPane;

    private JTextField productStockField ;

    // new
    public static JTable orderLineTable;
    public static JTable orderTable;
    private JTextField searchOrderDateField;
    private JTextField searchOrderNumberField;

    public String currentTabInTabbedPane;
    Boolean bankDetailExists = false;

    /**
     * Constructor for the StaffUserView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public StaffUserView(Connection connection) throws SQLException {

        databaseOperationsUser = new DatabaseOperationsUser();
        databaseOperations = new DatabaseOperations();

        // Set properties for the new window
        setTitle("Staff");
        setSize(1400, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JPanel for the new window
        JPanel mainPanel = new JPanel();
        // mainPanel.setLayout(new FlowLayout());
        // mainPanel.setLayout(new GridLayout());
        // mainPanel.setSize(1000, 900);

        JPanel managerPanel = new JPanel();

        JPanel confirmedOrderPanel = new JPanel();
        JPanel productPanel = new JPanel();
        JPanel fullfilledOrderPanel = new JPanel();
        JPanel updateStockPanel = new JPanel();

        // new

        // JPanel searchPanel = new JPanel();
        JPanel searchPanel = new JPanel(new GridLayout(5, 1));

        searchOrderDateField = new JTextField(20);
        searchOrderNumberField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Order Date:"));
        searchPanel.add(searchOrderDateField);
        searchPanel.add(new JLabel("Order Number:"));
        searchPanel.add(searchOrderNumberField);
        searchPanel.add(searchButton);
        // fullfilledOrderPanel.add(searchPanel, BorderLayout.NORTH) ;
        // confirmedOrderPanel.add(searchPanel, BorderLayout.NORTH) ;


        JPanel btnConfirmedOrderPanel = new JPanel(new GridLayout(4, 1));


        JButton checkForBankDetailsButton = new JButton("Check Bank Details");
        JButton viewOrderLineButton = new JButton("View Order Line");
        JButton fulfillOrderLineButton = new JButton("Fulfill Order");
        JButton deleteOrderLineButton = new JButton("Delete Order");
        JButton viewConfirmOrderLineButton = new JButton("View Order Line");

        btnConfirmedOrderPanel.add (checkForBankDetailsButton);
        btnConfirmedOrderPanel.add (viewConfirmOrderLineButton);
        btnConfirmedOrderPanel.add (fulfillOrderLineButton);
        btnConfirmedOrderPanel.add (deleteOrderLineButton);



        tabbedPane = new JTabbedPane();
        tabbedPane.setSize(800,800);

        JButton updateStockProductButton = new JButton("Update Product Stock");
        JLabel productStockLabel = new JLabel("Stock:");
        productStockField = new JTextField(20);

        getContentPane().add(mainPanel);

        tabbedPane.addTab("Confirmed Orders", confirmedOrderPanel);
        tabbedPane.addTab("Fullfilled Orders",  fullfilledOrderPanel);
        tabbedPane.addTab("Products",  productPanel);
        tabbedPane.addTab("Update Stock",  updateStockPanel);


        if (listOfRolesForCurrentUser.contains(Role.MANAGER) ) {
            // NOTE : If you change the Title, need to chage the name in Change Listener method
             tabbedPane.addTab("Manager",  managerPanel);
            //  managerPanel.add (managerButton);
        }

        // new

        DefaultTableModel tableOrderModel = new DefaultTableModel();
        orderTable = new JTable(tableOrderModel);
        tableOrderModel.setRowCount(0);

        tableOrderModel.addColumn("Order Number");
        tableOrderModel.addColumn("Order Date");
        tableOrderModel.addColumn("Order Status");
        tableOrderModel.addColumn("Email");
        tableOrderModel.addColumn("Forename");
        tableOrderModel.addColumn("Surname");
        tableOrderModel.addColumn("House Number");
        tableOrderModel.addColumn("Post Code");
        tableOrderModel.addColumn("Road Name");
        tableOrderModel.addColumn("City Name");


        DefaultTableModel tableOrderLineModel = new DefaultTableModel();
        orderLineTable = new JTable(tableOrderLineModel);
        tableOrderLineModel.setRowCount(0);


        tableOrderLineModel.addColumn("Order Number");
        tableOrderLineModel.addColumn("Line Number");
        tableOrderLineModel.addColumn("Product Code");

        tableOrderLineModel.addColumn("Product Name");
        tableOrderLineModel.addColumn("Brand Name");

        tableOrderLineModel.addColumn("Qty");
        tableOrderLineModel.addColumn("Line Cost");

        // ??

        // JScrollPane scrollPaneOrder = new JScrollPane(orderTable);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPaneOrder = new JScrollPane(orderTable);
        scrollPaneOrder.setViewportView(orderTable);
        // confirmedOrderPanel.add(scrollPaneOrder, BorderLayout.CENTER);

        orderLineTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPaneOrderLine = new JScrollPane(orderLineTable);
        scrollPaneOrderLine.setViewportView(orderLineTable);

        tabbedPane.setSelectedIndex(0);
        if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {
                try {
                    // newWindow = new ProductsPage(connection);
                    //  setting variable to keep track of current/active tab
                    currentTabInTabbedPane = "Confirmed";
                    confirmedOrderPanel.removeAll();
                    tabbedPane.setSelectedIndex(0);
                    tableOrderModel.setRowCount(0);
                    tableOrderLineModel.setRowCount(0);

                    confirmedOrderPanel.add(searchPanel, BorderLayout.NORTH) ;
                    confirmedOrderPanel.add(scrollPaneOrder, BorderLayout.CENTER);
                    confirmedOrderPanel.add(btnConfirmedOrderPanel);
                    confirmedOrderPanel.add(scrollPaneOrderLine, BorderLayout.CENTER);

                    DatabaseOperations dbOperations = new DatabaseOperations();
                    ResultSet resultSet = dbOperations.getOrdersForStatus(connection, OrderStatus.CONFIRMED);

                    while (resultSet.next()) {
                        tableOrderModel.addRow(new Object[]{
                                resultSet.getString("orderNumber"),
                                resultSet.getString("orderDate"),
                                resultSet.getString("orderStatus"),
                                resultSet.getString("email"),
                                resultSet.getString("forename"),
                                resultSet.getString("surname"),
                                resultSet.getString("houseNumber"),
                                resultSet.getString("postcode"),
                                resultSet.getString("roadName"),
                                resultSet.getString("cityName")

                        });
                    }
                    resultSet.close();
                    confirmedOrderPanel.revalidate();
                    confirmedOrderPanel.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        }

        mainPanel.add(tabbedPane,BorderLayout.CENTER);

        ChangeListener tabbedPaneChangeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent){

                JTabbedPane localTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = localTabbedPane.getSelectedIndex();

                // System.out.println(changeEvent.getSource());
                System.out.println("Tab changed to: " + localTabbedPane.getTitleAt(index));
                // System.out.println("Tab changed to: " + localTabbedPane.getTitleAt(index)).ge);

                if (localTabbedPane.getTitleAt(index) == "Manager") {
                    currentTabInTabbedPane = "Manager";

                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.MANAGER) ) {
                        // Open a new window (replace NewWindowClass with the actual class you want to open)
                        ManagerUserView newWindow = null;

                        try {
                            newWindow = new ManagerUserView(connection);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        newWindow.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "You are not authorized to view this!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }


                if (localTabbedPane.getTitleAt(index) == "Products") {
                    currentTabInTabbedPane = "Products";

                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {

                        ProductsPage newWindow = null;

                        try {
                            newWindow = new ProductsPage(connection);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        newWindow.setVisible(true);
                    }
                }

                if (localTabbedPane.getTitleAt(index) == "Fullfilled Orders") {
                    currentTabInTabbedPane = "Fullfilled";

                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();

                    if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {

                        try {
                            // newWindow = new ProductsPage(connection);

                            searchOrderNumberField.setText("");
                            fullfilledOrderPanel.removeAll();
                            tabbedPane.setSelectedIndex(1);
                            tableOrderModel.setRowCount(0);
                            tableOrderLineModel.setRowCount(0);

                            fullfilledOrderPanel.add(searchPanel, BorderLayout.NORTH) ;

                            fullfilledOrderPanel.add(scrollPaneOrder, BorderLayout.CENTER);
                            fullfilledOrderPanel.add(viewOrderLineButton);

                            fullfilledOrderPanel.add(scrollPaneOrderLine, BorderLayout.CENTER);

                            DatabaseOperations dbOperations = new DatabaseOperations();
                            ResultSet resultSet = dbOperations.getOrdersForStatus(connection, OrderStatus.FULFILLED);

                            while (resultSet.next()) {
                                tableOrderModel.addRow(new Object[]{
                                        resultSet.getString("orderNumber"),
                                        resultSet.getString("orderDate"),
                                        resultSet.getString("orderStatus"),
                                        resultSet.getString("email"),
                                        resultSet.getString("forename"),
                                        resultSet.getString("surname"),
                                        resultSet.getString("houseNumber"),
                                        resultSet.getString("postcode"),
                                        resultSet.getString("roadName"),
                                        resultSet.getString("cityName")

                                });

                            }
                            resultSet.close();
                            fullfilledOrderPanel.revalidate();
                            fullfilledOrderPanel.repaint();

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        // newWindow.setVisible(true);
                    }
                }

                if (localTabbedPane.getTitleAt(index) == "Confirmed Orders") {
                    currentTabInTabbedPane = "Confirmed";

                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {

                        try {

                            searchOrderNumberField.setText("");

                            confirmedOrderPanel.removeAll();
                            tabbedPane.setSelectedIndex(0);
                            tableOrderModel.setRowCount(0);
                            tableOrderLineModel.setRowCount(0);
                            confirmedOrderPanel.add(searchPanel, BorderLayout.NORTH) ;
                            confirmedOrderPanel.add(scrollPaneOrder, BorderLayout.CENTER);

                            confirmedOrderPanel.add(btnConfirmedOrderPanel);
                            confirmedOrderPanel.add(scrollPaneOrderLine, BorderLayout.CENTER);

                            DatabaseOperations dbOperations = new DatabaseOperations();
                            ResultSet resultSet = dbOperations.getOrdersForStatus(connection, OrderStatus.CONFIRMED);

                            while (resultSet.next()) {
                                tableOrderModel.addRow(new Object[]{
                                        resultSet.getString("orderNumber"),
                                        resultSet.getString("orderDate"),
                                        resultSet.getString("orderStatus"),
                                        resultSet.getString("email"),
                                        resultSet.getString("forename"),
                                        resultSet.getString("surname"),
                                        resultSet.getString("houseNumber"),
                                        resultSet.getString("postcode"),
                                        resultSet.getString("roadName"),
                                        resultSet.getString("cityName")

                                });
                            }
                            resultSet.close();
                            confirmedOrderPanel.revalidate();
                            confirmedOrderPanel.repaint();

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        // newWindow.setVisible(true);
                    }
                }


                if (localTabbedPane.getTitleAt(index) == "Update Stock") {
                    currentTabInTabbedPane = "Stock";

                    // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
                    if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {
                        // ProductsPage newWindow = null;
                        try {
                            // newWindow = new ProductsPage(connection);
                            updateStockPanel.removeAll();
                            updateStockPanel.add(new ProductsPanelPage (connection));
                            updateStockPanel.add(productStockLabel);
                            productStockField.setText("0");
                            updateStockPanel.add(productStockField);
                            updateStockPanel.add(updateStockProductButton);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        // newWindow.setVisible(true);
                    }
                }
            }
        };

        //  Adding change listener tp tabbed Pane control
        tabbedPane.addChangeListener(tabbedPaneChangeListener);


        // Add action listener to the button
        updateStockProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = ProductsPanelPage.productsTable.getSelectedRow();
                  // Check if a product is selected
                if (row >= 0) {
                    try {
                        int productNewStock = Integer.parseInt( productStockField.getText());

                        String productOldStock1 = String.valueOf(ProductsPanelPage.productsTable.getValueAt(row,3)) ;
                        Integer productOldStock = Integer.parseInt( productOldStock1)     ;

                        if (productNewStock > 0) {
                            if (productNewStock  > productOldStock)  {
                                    String productCode = String.valueOf(ProductsPanelPage.productsTable.getValueAt(row,0)) ;

                                    // Ask for confirmation
                                    int dialogResult = JOptionPane.showConfirmDialog(null,
                                            "Are you sure you want to Update the Stock for    " + productCode  + " from Existing Stock ?"  + productOldStock , "Confirmation",
                                            JOptionPane.YES_NO_OPTION);

                                    // Check the user's choice
                                    if (dialogResult == JOptionPane.YES_OPTION) {
                                        // User confirmed, promote the selected user to Moderator
                                        try {
                                            databaseOperations.updateStock(connection, productCode, productNewStock );
                                            updateStockPanel.removeAll();
                                            updateStockPanel.add(new ProductsPanelPage (connection));
                                            updateStockPanel.add(productStockLabel);
                                            productStockField.setText("0");
                                            updateStockPanel.add(productStockField);
                                            updateStockPanel.add(updateStockProductButton);
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                        JOptionPane.showMessageDialog(null, productCode + " Stock Updated Successfully.");
                                    } else {
                                        // User canceled the action
                                        JOptionPane.showMessageDialog(null, "Canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                                    }
                                } else {
                                   JOptionPane.showMessageDialog(null, "New Stock should be more than the Current Stock ");
                                }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Please Enter a New Stock to update.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity.");
                    }

                }


                  else {
                    JOptionPane.showMessageDialog(null, "Please select a Product.");
                  }

            }
        });


       // Add action listener to the button
        viewOrderLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    // Get the selected user from the combo box
                    // int row = OrderPanelPage.orderTable.getSelectedRow();
                    int row = orderTable.getSelectedRow();

                    // Check if a user is selected
                    if (row >= 0) {

                        System.out.println(orderTable.getValueAt(row,0));
                        System.out.println(orderTable.getValueAt(row,1));
                        System.out.println(orderTable.getValueAt(row,2));

                        // String orderNumber = String.valueOf(OrderPanelPage.orderTable.getValueAt(row,0)) ;
                        String orderNumber = String.valueOf(orderTable.getValueAt(row,0)) ;


                        try {
                            int index = tabbedPane.getSelectedIndex();

                            if (tabbedPane.getTitleAt(index) == "Fullfilled Orders") {

                                tableOrderLineModel.setRowCount(0);

                                DatabaseOperations dbOperations = new DatabaseOperations();
                                ResultSet resultSet = dbOperations.getOrderLineForOrderNumber(connection, orderNumber);

                                while (resultSet.next()) {
                                    tableOrderLineModel.addRow(new Object[]{
                                            resultSet.getString("orderNumber"),
                                            // resultSet.getString("orderDate"),
                                            // resultSet.getBigDecimal("orderStatus"),
                                            resultSet.getInt("orderLineNumber"),
                                            resultSet.getString("productCode"),

                                            resultSet.getString("productName"),
                                            resultSet.getString("brandName"),

                                            resultSet.getString("quantity"),
                                            resultSet.getString("lineCost")
                                    });
                                }

                                resultSet.close();

                            }

                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a Order.");
                    }
            }
        });


        // Add action listener to the button
        viewConfirmOrderLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    // Get the selected user from the combo box
                    int row = orderTable.getSelectedRow();

                    // Check if a user is selected
                    if (row >= 0) {

                        System.out.println(orderTable.getValueAt(row,0));
                        System.out.println(orderTable.getValueAt(row,1));
                        System.out.println(orderTable.getValueAt(row,2));

                        String orderNumber = String.valueOf(orderTable.getValueAt(row,0)) ;
                        try {
                            int index = tabbedPane.getSelectedIndex();

                            if (tabbedPane.getTitleAt(index) == "Confirmed Orders") {

                                tableOrderLineModel.setRowCount(0);

                                DatabaseOperations dbOperations = new DatabaseOperations();
                                ResultSet resultSet = dbOperations.getOrderLineForOrderNumber(connection, orderNumber);

                                while (resultSet.next()) {
                                    tableOrderLineModel.addRow(new Object[]{
                                            resultSet.getString("orderNumber"),
                                            // resultSet.getString("orderDate"),
                                            // resultSet.getBigDecimal("orderStatus"),
                                            resultSet.getInt("orderLineNumber"),
                                            resultSet.getString("productCode"),

                                            resultSet.getString("productName"),

                                            resultSet.getString("brandName"),
                                            resultSet.getString("quantity"),
                                            resultSet.getString("lineCost")
                                    });
                                }
                            }

                            // confirmedOrderPanel.setVisible(true);
                            // // confirmedOrderPanel.setVisible(true);
                            //      confirmedOrderPanel.revalidate();
                            //      confirmedOrderPanel.repaint();

                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a Order.");
                    }
            }
        });

        // Add action listener to the button
        fulfillOrderLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    // Get the selected user from the combo box

                    int row = orderTable.getSelectedRow();
                    // Check if a user is selected
                    if (row >= 0) {

                        String orderNumber = String.valueOf(orderTable.getValueAt(row,0)) ;

                        // bankDetailExists or not
                        if(databaseOperations.IsUserBankDetailsExists(connection, orderNumber)){
                            // Ask for confirmation
                            int dialogResult = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want Fulfill the order -   " + orderNumber  + "  ?"  , "Confirmation",
                                    JOptionPane.YES_NO_OPTION);

                            // Check the user's choice
                            if (dialogResult == JOptionPane.YES_OPTION) {
                                // User confirmed, promote the selected user to Moderator
                                try {
                                    //  Update the Order to Fulfilled
                                    databaseOperations.updateOrderStatus(connection, orderNumber);
                                    // reloading the data as there may be new orders

                                    searchOrderNumberField.setText("");

                                    confirmedOrderPanel.removeAll();
                                    tabbedPane.setSelectedIndex(0);
                                    tableOrderModel.setRowCount(0);
                                    tableOrderLineModel.setRowCount(0);
                                    confirmedOrderPanel.add(searchPanel, BorderLayout.NORTH) ;
                                    confirmedOrderPanel.add(scrollPaneOrder, BorderLayout.CENTER);

                                    confirmedOrderPanel.add(btnConfirmedOrderPanel);
                                    confirmedOrderPanel.add(scrollPaneOrderLine, BorderLayout.CENTER);

                                    DatabaseOperations dbOperations = new DatabaseOperations();
                                    ResultSet resultSet = dbOperations.getOrdersForStatus(connection, OrderStatus.CONFIRMED);

                                    while (resultSet.next()) {
                                        tableOrderModel.addRow(new Object[]{
                                                resultSet.getString("orderNumber"),
                                                resultSet.getString("orderDate"),
                                                resultSet.getString("orderStatus"),
                                                resultSet.getString("email"),
                                                resultSet.getString("forename"),
                                                resultSet.getString("surname"),
                                                resultSet.getString("houseNumber"),
                                                resultSet.getString("postcode"),
                                                resultSet.getString("roadName"),
                                                resultSet.getString("cityName")

                                        });
                                    }
                                    resultSet.close();
                                    confirmedOrderPanel.revalidate();
                                    confirmedOrderPanel.repaint();

                                        // fullfilledOrderPanel.revalidate();
                                    // fullfilledOrderPanel.repaint();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                                JOptionPane.showMessageDialog(null, orderNumber + " Order marked Fulfilled Successfully.");
                            } else {
                                // User canceled the action
                                JOptionPane.showMessageDialog(null, "Canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                            }
                            }
                        else {
                            JOptionPane.showMessageDialog(null, "Cannot Fulfill the Order Bank Details doesnt Exist.");
                        }
                        } else {
                        JOptionPane.showMessageDialog(null, "Please select an Order.");
                    }
            }
        });


       // Add action listener to the button
        checkForBankDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    // Get the selected user from the combo box

                    int row = orderTable.getSelectedRow();
                    // Check if a user is selected
                    if (row >= 0) {

                                    String orderNumber = String.valueOf(orderTable.getValueAt(row,0)) ;

                                    // bankDetailExists or not
                                    if(databaseOperations.IsUserBankDetailsExists(connection, orderNumber)){
                                        JOptionPane.showMessageDialog(null, "Bank Details Exist ");
                                     }
                                    else {
                                        JOptionPane.showMessageDialog(null, "Bank Details doesnt EXIST. Cannot proceed to fullfill the order");
                                    }
                        } else {
                        JOptionPane.showMessageDialog(null, "Please select an Order.");
                    }
            }
        });


        // Add action listener to the button
        deleteOrderLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int row = orderTable.getSelectedRow();
                    // Check if a user is selected
                    if (row >= 0) {

                        String orderNumber = String.valueOf(orderTable.getValueAt(row,0)) ;

                        // Ask for confirmation
                        int dialogResult = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete the order -   " + orderNumber  + "  ?"  , "Confirmation",
                                JOptionPane.YES_NO_OPTION);

                        // Check the user's choice
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            // User confirmed, promote the selected user to Moderator
                            try {
                                //  Update the Order to Fulfilled
                                databaseOperations.deleteOrderStatus(connection, orderNumber);

                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(null, orderNumber + " Order Deleted Successfully.");
                        } else {
                            // User canceled the action
                            JOptionPane.showMessageDialog(null, "Canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a Order.");
                    }

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String orderDate = searchOrderDateField.getText();
                String orderNumber = searchOrderNumberField.getText();
                try {

                    DatabaseOperations dbOperations = new DatabaseOperations();
                    OrderStatus localOrderStatus = OrderStatus.CONFIRMED;
                    if (currentTabInTabbedPane.equals("Confirmed")){
                        localOrderStatus =  OrderStatus.CONFIRMED;
                    }
                    else  if(currentTabInTabbedPane.equals("Fullfilled")){
                        localOrderStatus =  OrderStatus.FULFILLED;
                    }

                    //    ResultSet resultSet = dbOperations.getOrdersForStatus(connection, OrderStatus.CONFIRMED);
                       ResultSet resultSet = dbOperations.getOrdersForStatus(connection, localOrderStatus);

                       tableOrderModel.setRowCount(0);
                       tableOrderLineModel.setRowCount(0);

                       if ((! orderNumber.isEmpty()) || (! orderDate.isEmpty())) {
                            if (! orderDate.isEmpty() && ! orderNumber.isEmpty()) {
                                while (resultSet.next()) {
                                        if ((orderNumber.equals(resultSet.getString("orderNumber"))) &&
                                                    (orderDate.equals(resultSet.getString("orderDate")))){
                                                tableOrderModel.addRow(new Object[]{
                                                    resultSet.getString("orderNumber"), resultSet.getString("orderDate"),
                                                    resultSet.getString("orderStatus"), resultSet.getString("email"),
                                                    resultSet.getString("forename"), resultSet.getString("surname"),
                                                    resultSet.getString("houseNumber"), resultSet.getString("postcode"),
                                                    resultSet.getString("roadName"),resultSet.getString("cityName")
                                                });
                                            }
                                }
                            }
                            else if (! orderDate.isEmpty()){
                                while (resultSet.next()) {
                                        if ((orderDate.equals(resultSet.getString("orderDate")))){
                                                tableOrderModel.addRow(new Object[]{
                                                    resultSet.getString("orderNumber"), resultSet.getString("orderDate"),
                                                    resultSet.getString("orderStatus"), resultSet.getString("email"),
                                                    resultSet.getString("forename"), resultSet.getString("surname"),
                                                    resultSet.getString("houseNumber"), resultSet.getString("postcode"),
                                                    resultSet.getString("roadName"),resultSet.getString("cityName")
                                                });
                                            }
                                }
                            }
                            else if (! orderNumber.isEmpty()) {
                               while (resultSet.next()) {
                                        if ((orderNumber.equals(resultSet.getString("orderNumber")))){
                                                tableOrderModel.addRow(new Object[]{
                                                    resultSet.getString("orderNumber"), resultSet.getString("orderDate"),
                                                    resultSet.getString("orderStatus"), resultSet.getString("email"),
                                                    resultSet.getString("forename"), resultSet.getString("surname"),
                                                    resultSet.getString("houseNumber"), resultSet.getString("postcode"),
                                                    resultSet.getString("roadName"),resultSet.getString("cityName")
                                                });
                                            }
                                }
                            }
                        }
                        else {
                                while (resultSet.next()) {
                                    tableOrderModel.addRow(new Object[]{
                                                resultSet.getString("orderNumber"), resultSet.getString("orderDate"),
                                                resultSet.getString("orderStatus"), resultSet.getString("email"),
                                                resultSet.getString("forename"), resultSet.getString("surname"),
                                                resultSet.getString("houseNumber"), resultSet.getString("postcode"),
                                                resultSet.getString("roadName"),resultSet.getString("cityName")
                                            });
                                    }
                        }
                        resultSet.close();
                        // confirmedOrderPanel.revalidate();
                        // confirmedOrderPanel.repaint();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        });






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
