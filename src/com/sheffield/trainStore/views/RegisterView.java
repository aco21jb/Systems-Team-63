package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.DatabaseOperationsUser;
import com.sheffield.trainStore.model.OrderLine;
import com.sheffield.trainStore.model.Role;
import com.sheffield.trainStore.model.User;

import com.sheffield.util.UniqueUserIDGenerator;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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

        // housenumberField.addKeyListener(new KeyAdapter() {
        //     @Override
        //     public void keyPressed(KeyEvent e) {
        //         // int key = e.getKeyCode();
        //         int key = e.getKeyChar();

        //         /* Restrict input to only integers */
        //         if (key < 96 && key > 105) e.setKeyChar(' ');
        //     };
        // });        

        // Create an ActionListener for the Register button
        registerButton.addActionListener(new ActionListener() {
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
                                String uniqueUserID = UniqueUserIDGenerator.generateUniqueUserID() ;
                                char[] passwordChars = passwordField.getPassword();
                                String forename = forenameField.getText();
                                String surname = surnameField.getText();

                                // try {
                                //     Integer houseNumberInt = Integer.parseInt( housenumberField.getText());
  
                                // } catch (NumberFormatException n) {
                                //     System.out.println("HouseNumber should contain Number");
                                // }

                                Integer houseNumberInt = Integer.parseInt( housenumberField.getText());
                                String postcode = postcodeField.getText();
                                String roadName = roadnameField.getText();
                                String cityName = citynameField.getText();

                                DatabaseOperationsUser databaseOperationsUser = new DatabaseOperationsUser();
                                try {
                                    if( ! databaseOperationsUser.verifyEmailID(connection, emailId)) {
                                        User user = new User(uniqueUserID, emailId, passwordChars, forename, surname, houseNumberInt, postcode, roadName, cityName);
                                        // databaseOperationsUser.registerUser(connection, uniqueUserID, emailId, passwordChars, forename, surname, houseNumberInt, 
                                        // postcode, roadName, cityName);
                                        databaseOperationsUser.registerUser(connection, user);

                                        // Secure disposal of the password
                                        Arrays.fill(passwordChars, '\u0000');
                                    
                                        dispose();
                                        HomePage HomePage = null;
                                        HomePage = new HomePage(connection);
                                        HomePage.setVisible(true);
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(null, "Already registered with this Email ID.. ");
                                    }
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                }
                else {
                        JOptionPane.showMessageDialog(null, "Invalid Email ID.. ");
                }
            } 
        });



                // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                        // Close the current window

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

    // private boolean isDataValid (Role role) {
    //     // List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();
    // //     for (Role roleForCurrentUser : listOfRolesForCurrentUser) {
    // //         if (roleForCurrentUser.equals(role)) {
    // //             return true;
    // //         }
    // //     }
    //     return false;
    // }    
      
    
}
