package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.OrderStatus;
import com.sheffield.trainStore.model.Role;

import javax.swing.*;
import javax.swing.border.Border;
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

    private JTable orderTable;
    DefaultTableModel orderTableModel = new DefaultTableModel();

    private JPanel mainPanel;
    private JTabbedPane tabbedPanel;

    private JPanel newOrderPanel;
    private JPanel oldOrderPanel;

    private JPanel productPanel;
    private JPanel bottomPanel;

    private JScrollPane scrollPane;


    private JTextField orderNumberField ;
    private JTextField orderDateField ;
    private JTextField orderUserField ;



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
        setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Create a JPanel for the new window
        JPanel mainPanel = new JPanel();
        // mainPanel.setLayout(new FlowLayout());
        mainPanel.setLayout(new GridLayout());

        JPanel newOrderPanel = new JPanel();
        JPanel productPanel = new JPanel();
        JPanel oldOrderPanel = new JPanel();

        oldOrderPanel.setLayout(new GridLayout(5,2));
        // oldOrderPanel.setLayout(new BorderLayout());


        // JPanel bottomPanel = new JPanel();        

        tabbedPanel = new JTabbedPane();
        tabbedPanel.setSize(200,600);
        // tabbedPanel.addTab("New Orders", newOrderPanel);
        // tabbedPanel.addTab("Products",  productPanel);
        // tabbedPanel.addTab("Old Orders",  oldOrderPanel);

        // mainPanel.add(tabbedPanel);


        JLabel orderLabel = new JLabel("Order Number:");
        JLabel orderDateLabel = new JLabel("Order Date:");
        JLabel orderUserLabel = new JLabel("Order User:");
        // oldOrderPanel.add(orderLabel, BorderLayout.WEST);
        // oldOrderPanel.add(orderDateLabel, BorderLayout.WEST);
        // oldOrderPanel.add(orderUserLabel, BorderLayout.WEST);


        orderNumberField = new JTextField(20);
        orderNumberField.setEditable(false);

        orderDateField = new JTextField(20);
        orderDateField.setEditable(false);

        orderUserField = new JTextField(20);     
        orderUserField.setEditable(false);

        oldOrderPanel.add (orderLabel);
        oldOrderPanel.add (orderNumberField);
        oldOrderPanel.add (orderUserLabel);
        oldOrderPanel.add (orderUserField);
        oldOrderPanel.add (orderDateLabel);
        oldOrderPanel.add (orderDateField);
        
        oldOrderPanel.add(new JLabel()); 
        oldOrderPanel.add(new JLabel()); 


        // JButton fullfillOrderButton = new JButton("Fullfill Order");
        // JButton deleteOrderButton = new JButton("Delete Order");

        JButton newProductButton = new JButton("Add Product");
        JButton editProductButton = new JButton("Edit Product");
        // JButton deleteProductButton = new JButton("Delete Product");
        // JButton updateProductButton = new JButton("Update Stock");

        getContentPane().add(mainPanel);  
        // mainPanel.add(orderPanel);
        // mainPanel.add(productPanel);
        // mainPanel.add(bottomPanel);


        // Set a layout manager for the panel (e.g., GridLayout)
        // Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        // mainPanel.setBorder(blackline);     
        
        
        // new 28-nov
        orderTable= new JTable(orderTableModel);
        orderTableModel.addColumn("Order numer");
        orderTableModel.addColumn("DATE");
        orderTableModel.addColumn("Surname");

        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);     

            // Create a JScrollPane to display the table
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setSize(10,10);
        scrollPane.setViewportView(orderTable);        
        oldOrderPanel.add(scrollPane, BorderLayout.CENTER);

        tabbedPanel.addTab("New Orders", newOrderPanel);
        tabbedPanel.addTab("Products",  productPanel);
        tabbedPanel.addTab("Old Orders",  oldOrderPanel);
        // tabbedPanel.
        mainPanel.add(tabbedPanel,BorderLayout.CENTER);


        populateOrderTable(connection,  OrderStatus.FULFILLED);


        // mainPanel.visible
            
            // Add action listener to the button
        newProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if (isUserAuthorised(Role.ADMIN)) {
                    // Get the selected user from the combo box

                    int row = orderTable.getSelectedRow();
                    // String selectedUser = String.valueOf(staffComboBox.getSelectedItem());

                    // Check if a user is selected
                    if (row >= 0) {
                        // String emailId = (String) staffTable.getValueAt(row,0) ;
                        String emailId = String.valueOf(orderTable.getValueAt(row,0)) ;

                        // Ask for confirmation
                        int dialogResult = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to Remove   " + emailId  + " from Staff Role?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);

                        // Check the user's choice
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            // User confirmed, promote the selected user to Moderator
                            databaseOperationsUser.removeFromStaff(connection, emailId );
                            // staffTableModel.removeRow(orderTable.getSelectedRow());                           
                            JOptionPane.showMessageDialog(null, emailId + " has been Removed from Staff.");
                        } else {
                            // User canceled the action
                            JOptionPane.showMessageDialog(null, "Canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a user.");
                    }
             
            }
        });            


            // Add action listener to the button
            editProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        // Get the selected user from the combo box

                        String emailId = "";
                       
                        if (! databaseOperationsUser.IsAlreadyStaff(connection, emailId)) {
                            // Ask for confirmation
                            int dialogResult = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want to promote " + emailId + " to Staff?", "Confirmation",
                                    JOptionPane.YES_NO_OPTION);

                            // Check the user's choice
                            if (dialogResult == JOptionPane.YES_OPTION) {
                                // User confirmed, promote the selected user to Moderator
                                databaseOperationsUser.promoteToStaff(connection, emailId);
                                // populatestaffTable(connection);


                                JOptionPane.showMessageDialog(null, emailId + " has been promoted to Staff.");
                            } else {
                                // User canceled the action
                                JOptionPane.showMessageDialog(null, "canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "User doesn't exists or already a Staff");
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
        List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
        for (Role roleForCurrentUser : listOfRolesForCurrentUser) {
            if (roleForCurrentUser.equals(role)) {
                return true;
            }
        }
        return false;
    }


}