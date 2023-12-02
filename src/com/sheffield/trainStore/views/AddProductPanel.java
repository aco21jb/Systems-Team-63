package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class AddProductPanel extends JFrame{

    private JTextField productCodeField;
    private JTextField brandNameField;
    private JTextField productNameField;
    private JTextField priceField;
    private JTextField stockField;
    private JTextField gaugeField;
    private JTextField eraCodeField;
    private JTextField dccCodeField;

    public AddProductPanel(Connection con) throws SQLException {
        this.setTitle("Add Product");
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 500);

        // this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(12, 2));

        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        panel.setBorder(blackline);

        JLabel productCodeLabel = new JLabel("Enter Product Code:");
        JLabel productNameLabel = new JLabel("Enter Product Name:");
        JLabel brandNameLabel = new JLabel("Enter Brand Name:");
        JLabel priceLabel = new JLabel("Enter Retail Price:");
        JLabel stockLabel = new JLabel("Enter stock amount:");
        JLabel gaugeLabel = new JLabel("Enter gauge (may be empty):");
        JLabel eraCodeLabel = new JLabel("Enter Era Code (may be empty):");
        JLabel dccCodeLabel = new JLabel("Enter DCC Code (may be empty):");

        productCodeField = new JTextField(20);
        brandNameField = new JTextField(20);
        productNameField = new JTextField(20);
        priceField = new JTextField(20);
        stockField  = new JTextField(20);
        gaugeField = new JTextField(20);
        eraCodeField = new JTextField(20);
        dccCodeField = new JTextField(20);

        JButton addButton = new JButton("Add");

        panel.add(productCodeLabel);
        panel.add(productCodeField);
        panel.add(brandNameLabel);
        panel.add(brandNameField);
        panel.add(productNameLabel);
        panel.add(productNameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(stockLabel);
        panel.add(stockField);
        panel.add(gaugeLabel);
        panel.add(gaugeField);
        panel.add(eraCodeLabel);
        panel.add(eraCodeField);
        panel.add(dccCodeLabel);
        panel.add(dccCodeField);

        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseOperations dbOp = new DatabaseOperations();
                BigDecimal price = BigDecimal.valueOf(Double.valueOf(priceField.getText()));
                Integer stock = Integer.valueOf(stockField.getText());
                try {
                    dbOp.addProduct(con, new Product(productCodeField.getText(), brandNameField.getText(), productNameField.getText(),
                            price, stock, gaugeField.getText(), eraCodeField.getText(), dccCodeField.getText()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });

    }
}
