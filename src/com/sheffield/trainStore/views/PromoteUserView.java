package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.DatabaseOperationsUser;

import com.sheffield.trainStore.model.Role;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The PromoteUserView class represents the GUI window for promoting users to Moderator.
 */
public class PromoteUserView extends JFrame {
    private JComboBox<String> staffComboBox;
    private final DatabaseOperationsUser databaseOperationsUser;

    /**
     * Constructor for the PromoteUserView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public PromoteUserView(Connection connection) throws SQLException {
        // Initialize DatabaseOperations
        // DatabaseOperationsUser databaseOperationsUser = new DatabaseOperationsUser();
        databaseOperationsUser = new DatabaseOperationsUser();

        // Set properties for the new window
        setTitle("Manager");
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
            
            staffComboBox = new JComboBox<>();
            staffComboBox.setSize (300, 20);

            // Populate the combo box with user data 
            populatestaffComboBox(connection);
            panel.add(staffComboBox);


            JButton removeStaffButton = new JButton("Remove Staff Role");
            panel.add(removeStaffButton);

            panel.add(new JLabel());
            panel.add(new JLabel());
            
            JLabel emailLabel = new JLabel("Enter Email ID:");
            JTextField emailField= new JTextField(20);

            JButton promoteButton = new JButton("Promote to Staff");

            panel.add(emailLabel);
            panel.add(emailField);

            panel.add(new JLabel());
            panel.add(promoteButton);

            
            // Add action listener to the button
            removeStaffButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // if (isUserAuthorised(Role.ADMIN)) {
                        // Get the selected user from the combo box

                                            
                        String selectedUser = String.valueOf(staffComboBox.getSelectedItem());

                        // Check if a user is selected
                        if (selectedUser != null) {

                            // Ask for confirmation
                            int dialogResult = JOptionPane.showConfirmDialog(null,
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
                            }


                        } else {
                            JOptionPane.showMessageDialog(null, "Please select a user.");
                        }
                    // } else {
                    //     JOptionPane.showMessageDialog(null, "You are not an ADMIN!", "Error", JOptionPane.ERROR_MESSAGE);
                    // }
                }
            });            


            // Add action listener to the button
            promoteButton.addActionListener(new ActionListener() {
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
            });
    }

    /**
     * Populates the user combo box with user data from the database.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    private void populatestaffComboBox(Connection connection) throws SQLException {
        // ResultSet resultSet = databaseOperationsUser.getAllUsers(connection);

        // staffComboBox.removeAllItems();

        // ResultSet resultSet = databaseOperationsUser.getAllUsers(connection);
        ResultSet resultSet = databaseOperationsUser.getAllUsersStaff(connection);

        
        // Populate the JTable with the query results
        while (resultSet.next()) {


            // String userDetail = resultSet.getString("email") + "  -  "+ resultSet.getString("forename") + 
            //         "  -  " + resultSet.getString("surname");
 
            // staffComboBox.addItem(userDetail);
            String emailID = resultSet.getString("email");
            staffComboBox.addItem(emailID);
        }
        //     String userId = resultSet.getString("userId");

        //     Role role = Role.fromString(resultSet.getString("role"));

        //     // Check if the username is not the same as the current user's username
        //     if (!userId.equals(CurrentUserManager.getCurrentUser().getUserId()) && role.equals(Role.CUSTOMER)) {
        //         String userDetail = resultSet.getString("email") + "  -  "+ resultSet.getString("forename") + 
        //             "  -  " + resultSet.getString("surname");
 
        //         staffComboBox.addItem(userDetail);
        //     }
        // }
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