package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.Role;

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
public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    /**
     * Constructor for the LoginView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public LoginView(Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Trains of Sheffield - Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 150);

        // this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(5, 2));

        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        panel.setBorder(blackline);

        // Create JLabels for username and password
        JLabel emailLabel = new JLabel("Emailid:");
        JLabel passwordLabel = new JLabel("Password:");

        // Create JTextFields for entering username and password
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Create a JButton for the login action
        JButton loginButton = new JButton("Login");

        // Add components to the panel
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(new JLabel());  // Empty label for spacing

        panel.add(loginButton);

        // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailId = emailField.getText();
                char[] passwordChars = passwordField.getPassword();
                System.out.println(emailId);
                System.out.println(new String(passwordChars));
                DatabaseOperationsUser databaseOperationsUser = new DatabaseOperationsUser();
                // Check if login is successful
                if (databaseOperationsUser.verifyLogin(connection, emailId, passwordChars)) {
                    // Secure disposal of the password
                    Arrays.fill(passwordChars, '\u0000');


                    try {
                        // Close the current window

                        dispose();
                        HomePage HomePage = null;

                        HomePage = new HomePage(connection);
                        HomePage.setVisible(true);

                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                } else {
                    // Handle unsuccessful login (show a message, etc.)
                    JOptionPane.showMessageDialog(LoginView.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                // Secure disposal of the password
                Arrays.fill(passwordChars, '\u0000');
            }
        });
    }
}
