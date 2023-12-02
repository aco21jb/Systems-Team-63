package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.Role;

import javax.swing.*;
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
public class CustomerOrdersView extends JFrame {


    private final DatabaseOperationsUser databaseOperationsUser;
    private final DatabaseOperations databaseOperations;

    List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();

    DefaultTableModel orderTableModel = new DefaultTableModel();

    public static JTable orderLineTable;
    public static JTable orderTable;

    /**
     * Constructor for the StaffUserView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public CustomerOrdersView(Connection connection) throws SQLException {

        databaseOperationsUser = new DatabaseOperationsUser();
        databaseOperations = new DatabaseOperations();

        // Set properties for the new window
        setTitle("Customer Order");
        setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JPanel for the new window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        // mainPanel.setSize(500, 600);

        JPanel customerOrderPanel = new JPanel();

        // new
        JPanel customerOrderLinePanel = new JPanel();


        JButton viewOrderLineButton = new JButton("View Order Line");
      

        getContentPane().add(mainPanel);

   
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

       
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);     
        JScrollPane scrollPaneOrder = new JScrollPane(orderTable);
        scrollPaneOrder.setViewportView(orderTable);    

        orderLineTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);     
        JScrollPane scrollPaneOrderLine = new JScrollPane(orderLineTable);
        scrollPaneOrderLine.setViewportView(orderLineTable);           

        tableOrderModel.setRowCount(0);
        tableOrderLineModel.setRowCount(0);

        customerOrderPanel.add(scrollPaneOrder, BorderLayout.CENTER);
        customerOrderPanel.add(viewOrderLineButton);
                  
        // customerOrderPanel.add(scrollPaneOrderLine, BorderLayout.CENTER);   
        
        customerOrderLinePanel.add(scrollPaneOrderLine, BorderLayout.CENTER);

        mainPanel.add (customerOrderPanel);
        mainPanel.add (customerOrderLinePanel);

        try {

                DatabaseOperations dbOperations = new DatabaseOperations();
                ResultSet resultSet = dbOperations.getOrdersForUser(connection, CurrentUserManager.getCurrentUser().getUserId());


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
                customerOrderPanel.revalidate();
                customerOrderPanel.repaint();   

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }            

       
       // Add action listener to the button
        viewOrderLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    // Get the selected user from the combo box
                    int row = orderTable.getSelectedRow();

                    // Check if a user is selected
                    if (row >= 0) {
                 
                        String orderNumber = String.valueOf(orderTable.getValueAt(row,0)) ;
                        try {
                                tableOrderLineModel.setRowCount(0);

                                // confirmedOrderPanel.add(scrollPaneOrderLine, BorderLayout.CENTER);
                                                   
                                DatabaseOperations dbOperations = new DatabaseOperations();
                                ResultSet resultSet = dbOperations.getOrderLineForOrderNumber(connection, orderNumber);

                        

                                while (resultSet.next()) {
                                    tableOrderLineModel.addRow(new Object[]{
                                            resultSet.getString("orderNumber"),
                                            resultSet.getInt("orderLineNumber"),
                                            resultSet.getString("productCode"),                                            
                                            resultSet.getString("productName"),
                                            resultSet.getString("brandName"),
                                          
                                            resultSet.getString("quantity"),
                                            resultSet.getString("lineCost")
                                            
                                    });
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


    }

}
