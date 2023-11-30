package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.DatabaseConnectionHandler;
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

public class ProductsPage extends JFrame {

    private JTable products;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;

    ResultSet resultSet = null;

    public ProductsPage(Connection con) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Trains of Sheffield");
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel(new GridLayout(12, 2));
        this.add(panel);

        panel.setLayout(new GridLayout(12, 2));
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        panel.setBorder(blackLine);
        JLabel titleLabel = new JLabel("Products");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);

        // Create a JPanel for search and filter components
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        filterComboBox = new JComboBox<>(new String[]{"All", "Track", "Controllers", "Locomotives", "Rolling stock", "Train sets", "Track packs"});
        JButton filterButton = new JButton("Filter");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(new JLabel("Filter by type:"));
        searchPanel.add(filterComboBox);
        searchPanel.add(filterButton);

        // Add the search panel to the top of the frame
        this.add(searchPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel();
        products = new JTable(tableModel);
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Gauge");
        tableModel.addColumn("Era code");
        tableModel.addColumn("DCC code");
        tableModel.addColumn("Product Type");

        DatabaseOperations dbOperations = new DatabaseOperations();
        resultSet = dbOperations.getProducts(con);

        while (resultSet.next()) {
            tableModel.addRow(new Object[]{
                    resultSet.getString("productName"),
                    resultSet.getBigDecimal("retailPrice"),
                    resultSet.getInt("stock"),
                    resultSet.getString("gauge"),
                    resultSet.getString("eraCode"),
                    resultSet.getString("dccCode"),
                    resultSet.getString("productType")
            });
        }
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    filterProducts(con);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchProducts(con);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        resultSet.close();
        JScrollPane scrollPane = new JScrollPane(products);
        this.add(scrollPane, BorderLayout.CENTER);

    }

    private void filterProducts(Connection con) throws SQLException {
        DatabaseOperations dbOp = new DatabaseOperations();
        String type = filterComboBox.getSelectedItem().toString();
        System.out.println(type);
        switch (type){
            case "All":
                resultSet = dbOp.getProducts(con);
                break;
            case "Track":
                resultSet = dbOp.getFilteredProducts(con,'r');
                break;
            case "Controllers":
                resultSet = dbOp.getFilteredProducts(con,'c');
                break;
            case "Locomotives":
                resultSet = dbOp.getFilteredProducts(con,'l');
                break;
            case "Rolling stock":
                resultSet = dbOp.getFilteredProducts(con,'s');
                break;
            case "Train sets":
                resultSet = dbOp.getFilteredProducts(con,'m');
                break;
            case "Track packs":
                resultSet = dbOp.getFilteredProducts(con,'p');
                break;
        }
        DefaultTableModel tableModel = (DefaultTableModel) products.getModel();
        tableModel.setRowCount(0);

        if (resultSet != null) {
            while (resultSet.next()) {
                // Access data and add to the tableModel
                tableModel.addRow(new Object[]{
                        resultSet.getString("productName"),
                        resultSet.getBigDecimal("retailPrice"),
                        resultSet.getInt("stock"),
                        resultSet.getString("gauge"),
                        resultSet.getString("eraCode"),
                        resultSet.getString("dccCode"),
                        resultSet.getString("productType")
                });
            }
        }
        resultSet.close();
    }

    private void searchProducts(Connection con) throws SQLException {
        DatabaseOperations dbOp = new DatabaseOperations();
        String search = searchField.getText();
        resultSet = dbOp.getSearchProducts(con,search);
        DefaultTableModel tableModel = (DefaultTableModel) products.getModel();
        tableModel.setRowCount(0);

        if (resultSet != null) {
            while (resultSet.next()) {
                // Access data and add to the tableModel
                tableModel.addRow(new Object[]{
                        resultSet.getString("productName"),
                        resultSet.getBigDecimal("retailPrice"),
                        resultSet.getInt("stock"),
                        resultSet.getString("gauge"),
                        resultSet.getString("eraCode"),
                        resultSet.getString("dccCode"),
                        resultSet.getString("productType")
                });
            }
        }
        resultSet.close();
    }

}
