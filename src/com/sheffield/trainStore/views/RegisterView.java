package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.Role;
import com.sheffield.trainStore.model.User;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * The LoginView class represents the GUI window for user login.
 */
public class RegisterView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    private JTextField forenameField ;
    private JTextField surnameField ;
    private JTextField housenumberField ;
    private JTextField roadnameField ;
    private JTextField citynameField ;
    private JTextField postcodeField ;


    /**
     * Constructor for the LoginView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */

    public RegisterView(Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Trains of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JLabel passwordLabel = new JLabel("Password:");

        JLabel forenameLabel = new JLabel("forename:");
        JLabel surnameLabel = new JLabel("surname:");
        JLabel housenumberLabel = new JLabel("housenumber:");
        JLabel roadnameLabel = new JLabel("roadname:");
        JLabel citynameLabel = new JLabel("cityname:");
        JLabel postcodeLabel = new JLabel("postcode:");


        // Create JTextFields
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        forenameField = new JTextField(20);
        surnameField = new JTextField(20);
        housenumberField = new JTextField(20);

        roadnameField = new JTextField(20);
        citynameField = new JTextField(20);
        postcodeField = new JTextField(20);

        // Create a JButton for the login action
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Existing User");

        // Add components to the panel
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
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

        panel.add(registerButton);
        panel.add(loginButton);


        // Create an ActionListener for the Register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // if (e.getActionCommand() == "Register") {

                    String emailId = emailField.getText();
                    char[] passwordChars = passwordField.getPassword();

                    String forename = forenameField.getText();
                    String surname = surnameField.getText();
                    // String houseNumber = housenumberField.getText();
                    Integer houseNumberInt = Integer.parseInt( housenumberField.getText());
                    String postcode = postcodeField.getText();
                    String roadName = roadnameField.getText();
                    String cityName = postcodeField.getText();


                    // System.out.println(username);
                    // System.out.println(new String(passwordChars));
                    DatabaseOperationsUser databaseOperationsUser = new DatabaseOperationsUser();

                    // System.out.println(databaseOperations.verifyLogin(connection, username, passwordChars));
                    // Secure disposal of the password
                    // Arrays.fill(passwordChars, '\u0000');

                    // Close the current window
                    // dispose();

                    //  create user object --- validation should happen
                    // User newUser = new User (emailId, passwordChars);
                    try {
                        // databaseOperationsUser.registerUser(newUser, connection) ;

                        databaseOperationsUser.registerUser(connection, emailId, passwordChars, forename, surname, houseNumberInt, postcode, roadName, cityName);

                        dispose();
                        HomePage HomePage = null;

                        HomePage = new HomePage(connection);
                        HomePage.setVisible(true);

                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
        });



                // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                        dispose();
                        LoginView LoginView = null;

                        LoginView = new LoginView(connection);
                        LoginView.setVisible(true);

                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

        });

    }
}