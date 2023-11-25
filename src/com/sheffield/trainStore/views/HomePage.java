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
public class HomePage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Constructor for the LoginView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public HomePage(Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Trains of Sheffield - Home Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);

        // this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panelTop = new JPanel();
        this.add(panelTop);

        // JPanel panelBottom = new JPanel();
        // this.add(panelBottom);

        // Set a layout manager for the panel (e.g., GridLayout)
        panelTop.setLayout(new GridLayout(3, 3));
        // panelBottom.setLayout(new GridLayout(3, 2));

        
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        panelTop.setBorder(blackline);
        // panelBottom.setBorder(blackline);


        // Create a JButton for the login action
        JButton ManagerButton = new JButton("Manager");
        JButton CustomerButton = new JButton("Customer");
        JButton StaffButton = new JButton("Staff");

        panelTop.add(new JLabel());  // Empty label for spacing
        panelTop.add(ManagerButton);
        panelTop.add(CustomerButton);
        panelTop.add(StaffButton);

        // Create an ActionListener for the Manager button
        ManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Yet to be implemented");

            }
        });

          // Create an ActionListener for the Customer button
        CustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Yet to be implemented");

            }
        });

          // Create an ActionListener for the Manager button
        StaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Yet to be implemented");

            }
        });


    }
}