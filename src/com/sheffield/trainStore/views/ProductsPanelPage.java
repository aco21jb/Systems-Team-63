package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.DatabaseOperations;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class ProductsPanelPage extends JPanel {

    public static JTable productsTable;
    public ProductsPanelPage(Connection con) throws SQLException {
        // Create the JFrame in the constructor
        // this.setTitle("Trains of Sheffield");
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this.setSize(800, 500);
        // this.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel(new GridLayout(2, 2));
        this.add(panel);

        panel.setLayout(new GridLayout(12, 2));
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        panel.setBorder(blackLine);
        DefaultTableModel tableModel = new DefaultTableModel();
        productsTable = new JTable(tableModel);
        tableModel.addColumn("Product Code");        
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Gauge");
        tableModel.addColumn("Era code");
        tableModel.addColumn("DCC code");
        tableModel.addColumn("Product Type");

        DatabaseOperations dbOperations = new DatabaseOperations();
        ResultSet resultSet = dbOperations.getProducts(con);

        while (resultSet.next()) {
            tableModel.addRow(new Object[]{
                    resultSet.getString("productCode"),
                    resultSet.getString("productName"),
                    resultSet.getBigDecimal("retailPrice"),
                    resultSet.getInt("stock"),
                    resultSet.getString("gauge"),
                    resultSet.getString("eraCode"),
                    resultSet.getString("dccCode"),
                    resultSet.getString("productType")
            });
        }
        resultSet.close();
        // con.close();
        JScrollPane scrollPane = new JScrollPane(productsTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

}
