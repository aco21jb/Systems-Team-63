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

    // static JMenuBar menuBar;
    // static JMenu userMenu;
    // static JMenuItem managerMenuItem, customerMenuItem, staffMenuItem;
    static JFrame f;


    /**
     * Constructor for the LoginView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public HomePage(Connection connection) throws SQLException {

        List<Role> listOfRolesForCurrentUser = CurrentUserManager.getCurrentUser().getRoles();


        // Create the JFrame in the constructor
        this.setTitle("Trains of Sheffield - Home Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);

        // this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        // f = new JFrame ("Menu Frame");
        // menuBar = new JMenuBar();
        // userMenu = new JMenu ("Menu");
        // managerMenuItem = new JMenuItem("Manager");
        // customerMenuItem = new JMenuItem("Customer");
        // staffMenuItem= new JMenuItem("Staff");
        // userMenu.add(managerMenuItem);
        // userMenu.add(customerMenuItem);
        // userMenu.add(staffMenuItem);
        // menuBar.add(userMenu);
   
        // this.setJMenuBar(menuBar);


        Border blackline = BorderFactory.createLineBorder(Color.BLACK);

        // Create a JPanel to hold the components
        JPanel panelTop = new JPanel();
        panelTop.setSize(800,50);
        panelTop.setBorder(blackline);

        JPanel panelCenter= new JPanel();
        panelCenter.setSize(800,700);
        panelCenter.setBorder(blackline);

        JPanel panelBottom = new JPanel();
        panelBottom.setSize(800,50);
        panelBottom.setBorder(blackline);

        // JPanel panelBottom = new JPanel();
        // this.add(panelBottom);

        // Set a layout manager for the panel (e.g., GridLayout)
        // panelTop.setLayout(new GridLayout(1, 3));
        // panelCenter.setLayout(new GridLayout(1, 2));
        // panelBottom.setLayout(new GridLayout(1, 2));


        // Create a JButton for the login action
        // JButton ManagerButton = new JButton("Manager");
        JButton CustomerButton = new JButton("Customer");
        JButton StaffButton = new JButton("Staff");


        JButton EditPersonalButton = new JButton("Edit Personal Details");


        panelTop.add(new JLabel());  // Empty label for spacing

        // if (listOfRolesForCurrentUser.contains(Role.MANAGER) ) {
        //   panelTop.add(ManagerButton);
        // }

        panelTop.add(CustomerButton);
        panelTop.add(EditPersonalButton);


        if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {
            panelTop.add(StaffButton);
        }

        this.add(panelTop);
        this.add(panelCenter);
        this.add(panelBottom);



        // // Create an ActionListener for the Manager button
        // ManagerButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {

        //         if (listOfRolesForCurrentUser.contains(Role.MANAGER) ) {
        //                // Open a new window (replace NewWindowClass with the actual class you want to open)
        //                ManagerUserView newWindow = null;

        //                try {
        //                    newWindow = new ManagerUserView(connection);
        //                } catch (SQLException ex) {
        //                    throw new RuntimeException(ex);
        //                }
        //                newWindow.setVisible(true);
        //            } else {
        //                JOptionPane.showMessageDialog(null, "You are not authorized to view this!", "Error", JOptionPane.ERROR_MESSAGE);
        //            }
                  
        //       }
        // });

        // managerMenuItem.addActionListener(new ActionListener() {
        //     @Override
        //             public void actionPerformed(ActionEvent e) {

        //                 if (listOfRolesForCurrentUser.contains(Role.MANAGER) ) {
        //                     // Open a new window (replace NewWindowClass with the actual class you want to open)
        //                     ManagerUserView newWindow = null;

        //                     try {
        //                         newWindow = new ManagerUserView(connection);
        //                     } catch (SQLException ex) {
        //                         throw new RuntimeException(ex);
        //                     }
        //                     newWindow.setVisible(true);
        //                 } else {
        //                     JOptionPane.showMessageDialog(null, "You are not authorized to view this!", "Error", JOptionPane.ERROR_MESSAGE);
        //                 }
                        
        //             }
        // });

          // Create an ActionListener for the Customer button
        CustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //System.out.println("Yet to be implemented");

                ProductsView newWindow = null;
                ProductsPage productsWindow = null;
                try {
                    newWindow = new ProductsView(connection);
                    productsWindow = new ProductsPage(connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                newWindow.setVisible(true);
                productsWindow.setVisible(true);

            }
        });

          // Create an ActionListener for the UserDetailsView button
        EditPersonalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //System.out.println("Yet to be implemented");

                UserDetailsView newWindow = null;
                try {
                    newWindow = new UserDetailsView(connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                newWindow.setVisible(true);

            }
        });


          // Create an ActionListener for the Manager button
        StaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {
                       // Open a new window (replace NewWindowClass with the actual class you want to open)
                    //    PromoteUserView newWindow = null;
                       StaffUserView newWindow = null;

                       try {
                           newWindow = new StaffUserView(connection);
                       } catch (SQLException ex) {
                           throw new RuntimeException(ex);
                       }
                       newWindow.setVisible(true);
                   } else {
                       JOptionPane.showMessageDialog(null, "You are not authorized to view this!", "Error", JOptionPane.ERROR_MESSAGE);
                   }
                  
              }
        });

        // staffMenuItem.addActionListener(new ActionListener() {
        //     @Override
        //         public void actionPerformed(ActionEvent e) {

        //             if (listOfRolesForCurrentUser.contains(Role.STAFF) ) {
        //                 // Open a new window (replace NewWindowClass with the actual class you want to open)
        //                 StaffUserView newWindow = null;

        //                 try {
        //                     newWindow = new StaffUserView(connection);
        //                 } catch (SQLException ex) {
        //                     throw new RuntimeException(ex);
        //                 }
        //                 newWindow.setVisible(true);
        //             } else {
        //                 JOptionPane.showMessageDialog(null, "You are not authorized to view this!", "Error", JOptionPane.ERROR_MESSAGE);
        //             }
                    
        //         }
        // });


    }
}
