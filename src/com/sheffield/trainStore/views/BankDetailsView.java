package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BankDetailsView extends JFrame {

    private JTextField bankCardNameField;
    private JTextField cardHolderNameField;
    private JTextField cardNumberField;
    private JTextField cardExpiryDateField;
    private JTextField securityCodeField;
    private final DatabaseOperations databaseOperations;

    /**
     * Constructor for the BankDetailsView.
     *
     * @param connection The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public BankDetailsView(Connection connection) throws SQLException {

        databaseOperations = new DatabaseOperations();

        // Create the JFrame in the constructor
        this.setTitle("Bank Details");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);

        // this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(8, 3));

        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        panel.setBorder(blackline);

        // Create JLabels for each field
        JLabel bankCardNameLabel = new JLabel("Bank Card Name:");
        JLabel cardHolderNameLabel = new JLabel("Card Holder Name:");
        JLabel cardNumberLabel = new JLabel("Card Number:");
        JLabel cardExpiryDateLabel = new JLabel("Card Expiry Date (MM/YY):");
        JLabel securityCodeLabel = new JLabel("Security Code:");

        // Create JTextFields for entering text
        bankCardNameField = new JTextField(30);
        cardHolderNameField = new JTextField(30);
        cardNumberField = new JTextField(30);
        cardExpiryDateField = new JTextField(30);
        securityCodeField = new JTextField(3);

        // Create a JButton for the confirm bank details action
        JButton confirmButton = new JButton("Confirm");

        // Add components to the panel
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(bankCardNameLabel);
        panel.add(bankCardNameField);
        panel.add(cardHolderNameLabel);
        panel.add(cardHolderNameField);
        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(cardExpiryDateLabel);
        panel.add(cardExpiryDateField);
        panel.add(securityCodeLabel);
        panel.add(securityCodeField);
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(new JLabel());  // Empty label for spacing

        panel.add(confirmButton);

        // Create an ActionListener for the login button
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bankCardName = bankCardNameField.getText();
                String cardHolderName = cardHolderNameField.getText();
                String cardNumber = cardNumberField.getText();
                String cardExpiryDate = cardExpiryDateField.getText();
                String securityCode = securityCodeField.getText();

                DatabaseOperationsUser databaseOperationsUser = new DatabaseOperationsUser();

                CurrentUser currentUser = CurrentUserManager.getCurrentUser();
                String userID = currentUser.getUserId();
                if (databaseOperationsUser.alreadyHasBankDetails(connection, userID) == false) {
                    try {
                        BankDetails bankDetails = new BankDetails(userID, bankCardName, cardHolderName,
                                cardNumber, cardExpiryDate, securityCode);
                        databaseOperations.addBankDetails(connection, bankDetails);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User has already submitted their bank details.");
                }
            }
        });
    }
}
