package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.Role;
import com.sheffield.trainStore.model.User;

import com.sheffield.util.UniqueUserIDGenerator;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * The LoginView class represents the GUI window for user login.
 */
public class UserDetailsView extends JFrame {
    private JTextField emailField;
    // private JPasswordField passwordField;

    private JTextField forenameField ;
    private JTextField surnameField ;
    private JTextField housenumberField ;
    private JTextField roadnameField ;
    private JTextField citynameField ;
    private JTextField postcodeField ;

    String previousEmail = "";
    Boolean bankDetailExists = false;

    String  previousHouseNumber = "";
    String  previousPostalCode = "";    


    /**
     * Constructor for the LoginView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */

    public UserDetailsView(Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Trains of Sheffield");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(12, 2));
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        panel.setBorder(blackline);
        // panel.setBackground(Color.BLACK);

        // Create JLabels
        JLabel emailLabel = new JLabel("Email:");
        // JLabel passwordLabel = new JLabel("Password:");

        JLabel forenameLabel = new JLabel("forename:");
        JLabel surnameLabel = new JLabel("surname:");
        JLabel housenumberLabel = new JLabel("housenumber:");
        JLabel roadnameLabel = new JLabel("roadname:");
        JLabel citynameLabel = new JLabel("cityname:");
        JLabel postcodeLabel = new JLabel("postcode:");


        // Create JTextFields
        emailField = new JTextField(20);
        // passwordField = new JPasswordField(20);

        forenameField = new JTextField(20);
        surnameField = new JTextField(20);
        housenumberField = new JTextField(20);
        // housenumberField.setEditable(false);
        roadnameField = new JTextField(20);
        citynameField = new JTextField(20);
        postcodeField = new JTextField(20);
        // postcodeField.setEditable(false);


           // Create a JButton for the login action
        JButton updateButton = new JButton("Update");
        // JButton loginButton = new JButton("Existing User");

        // Add components to the panel
        panel.add(emailLabel);
        panel.add(emailField);
        // panel.add(passwordLabel);
        // panel.add(passwordField);
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(new JLabel());  // Empty label for spacing

        panel.add(forenameLabel);
        panel.add(forenameField);
        panel.add(surnameLabel);
        panel.add(surnameField);

        panel.add(housenumberLabel);
        panel.add(housenumberField);
        panel.add(roadnameLabel);
        panel.add(roadnameField);
        panel.add(citynameLabel);
        panel.add(citynameField);
        panel.add(postcodeLabel);
        panel.add(postcodeField);

        panel.add(new JLabel());  // Empty label for spacing
        panel.add(new JLabel());  // Empty label for spacing

        panel.add(updateButton);
        // panel.add(loginButton);
        DatabaseOperationsUser databaseOperationsUser = new DatabaseOperationsUser();

        ResultSet resultSet = databaseOperationsUser.getUserDetail(connection, CurrentUserManager.getCurrentUser().getUserId());

        if (resultSet.next()) {
            // String emailId = emailField.getText();

            emailField.setText(resultSet.getString("email")); 

            // Storing email to check whether user has changed the email or not
            previousEmail = resultSet.getString("email");

            forenameField.setText(resultSet.getString("forename"));
            surnameField.setText(resultSet.getString("surname"));

            housenumberField.setText(resultSet.getString("housenumber"));
            previousHouseNumber = resultSet.getString("housenumber");

            postcodeField.setText(resultSet.getString("postcode"));
            previousPostalCode =resultSet.getString("postcode");

            roadnameField.setText(resultSet.getString("roadname"));
            citynameField.setText(resultSet.getString("cityname"));
        }
        else {

        }

 
        // Create an ActionListener for the Register button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailId = emailField.getText();

              
                //Regular Expression   
                String regex = "^(.+)@(.+)$";  
                //Compile regular expression to get the pattern  
                Pattern pattern = Pattern.compile(regex);           
                //Create instance of matcher   
                Matcher matcher = pattern.matcher(emailId); 
                    
                if (matcher.matches()) {
                
                    // String uniqueUserID = UniqueUserIDGenerator.generateUniqueUserID() ;
                    // char[] passwordChars = passwordField.getPassword();

                    String forename = forenameField.getText();
                    String surname = surnameField.getText();
                    // String houseNumber = housenumberField.getText();
                    Integer houseNumberInt = Integer.parseInt( housenumberField.getText());
                    String postcode = postcodeField.getText();
                    String roadName = roadnameField.getText();
                    String cityName = citynameField.getText();

                    DatabaseOperationsUser databaseOperationsUser = new DatabaseOperationsUser();
                    try {
                        String userID = CurrentUserManager.getCurrentUser().getUserId();

                        User user = new User(userID, emailId, forename, surname, houseNumberInt, postcode, roadName, cityName);

                        if (! previousEmail.equals(emailId)) {
                            if( ! databaseOperationsUser.verifyEmailID(connection, emailId)) {

                                // databaseOperationsUser.updateUser(connection, user);
                                // passing previousPostalCode and previousHousenumber foreign key constraint in the code
                                // instead of database level
                                databaseOperationsUser.updateUser(connection, user, previousHouseNumber, previousPostalCode);


                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Already registered with this Email ID.. ");
                            }
                        }
                        else {
                            // databaseOperationsUser.updateUser(connection, userID, emailId, forename, surname, houseNumberInt, postcode,roadName, cityName);
                                // databaseOperationsUser.updateUser(connection, user);
                                databaseOperationsUser.updateUser(connection, user, previousHouseNumber, previousPostalCode);


                        }
                       
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                else {
                        JOptionPane.showMessageDialog(null, "Invalid Email ID.. ");
   
                }
            }  
        });


    }
}
