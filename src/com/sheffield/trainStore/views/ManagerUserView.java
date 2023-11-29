package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.DatabaseOperationsUser;

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
 * The ManagerUserView class represents the GUI window for promoting users to Moderator.
 */
public class ManagerUserView extends JFrame {

    private final DatabaseOperationsUser databaseOperationsUser;

    private JTable staffTable;
    DefaultTableModel staffTableModel = new DefaultTableModel();

    private JPanel mainPanel;

    private JPanel topPanel;
    private JPanel btnPanel;
    private JPanel bottomPanel;

    private JScrollPane scrollPane;




    /**
     * Constructor for the ManagerUserView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public ManagerUserView(Connection connection) throws SQLException {
      
        databaseOperationsUser = new DatabaseOperationsUser();

        // Set properties for the new window
        setTitle("Manager");
        setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Create a JPanel for the new window
        JPanel mainPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel btnPanel = new JPanel();
        JPanel bottomPanel = new JPanel();


        mainPanel.setLayout(new FlowLayout());

        JButton removeStaffButton = new JButton("Remove Staff Role");
        JButton promoteButton = new JButton("Promote to Staff");



        getContentPane().add(mainPanel);  
        mainPanel.add(topPanel);
        mainPanel.add(btnPanel);
        mainPanel.add(bottomPanel);


        // Set a layout manager for the panel (e.g., GridLayout)
        // Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        // mainPanel.setBorder(blackline);     
        
        
        // new 28-nov
            // DefaultTableModel tableModel = new DefaultTableModel();
        staffTable= new JTable(staffTableModel);
        staffTableModel.addColumn("Email");
        staffTableModel.addColumn("Forename");
        staffTableModel.addColumn("Surname");

        populatestaffTable(connection);
        staffTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);     

            // Create a JScrollPane to display the table
        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setSize(10,10);
        scrollPane.setViewportView(staffTable);        
        topPanel.add(scrollPane, BorderLayout.CENTER);

        topPanel.add(removeStaffButton);

        JLabel emailLabel = new JLabel("Enter Email ID:");
        JTextField emailField= new JTextField(20);


        bottomPanel.add(emailLabel);
        bottomPanel.add(emailField);

        bottomPanel.add(new JLabel());
        bottomPanel.add(promoteButton);

            
            // Add action listener to the button
        removeStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if (isUserAuthorised(Role.ADMIN)) {
                    // Get the selected user from the combo box

                    int row = staffTable.getSelectedRow();
                    // String selectedUser = String.valueOf(staffComboBox.getSelectedItem());

                    // Check if a user is selected
                    if (row >= 0) {
                        // String emailId = (String) staffTable.getValueAt(row,0) ;
                        String emailId = String.valueOf(staffTable.getValueAt(row,0)) ;

                        // Ask for confirmation
                        int dialogResult = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to Remove   " + emailId  + " from Staff Role?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);

                        // Check the user's choice
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            // User confirmed, promote the selected user to Moderator
                            databaseOperationsUser.removeFromStaff(connection, emailId );
                            staffTableModel.removeRow(staffTable.getSelectedRow());                           
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
            promoteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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

    private void populatestaffTable(Connection connection) throws SQLException {

    
        ResultSet resultSet = databaseOperationsUser.getAllUsersStaff(connection);

        // Populate the JTable with the query results
        while (resultSet.next()) {

            // String emailID = resultSet.getString("email");
            // staffComboBox.addItem(emailID);

            staffTableModel.addRow(new Object[]{
                resultSet.getString("email"),
                resultSet.getString("forename"),
                resultSet.getString("surname")
                
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