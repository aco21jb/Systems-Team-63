package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.OrderStatus;

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


public class OrderPanelPage extends JPanel {

    public static JTable orderTable;
    public OrderPanelPage(Connection con, OrderStatus orderStatus) throws SQLException {
        // Create the JFrame in the constructor
        // this.setTitle("Trains of Sheffield");
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setSize(800, 500);
        // this.setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel(new GridLayout(2, 2));
        // JPanel panel = new JPanel();

        this.add(panel);

        panel.setLayout(new GridLayout(12, 2));

        // Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        // panel.setBorder(blackLine);

        DefaultTableModel tableModel = new DefaultTableModel();
        orderTable = new JTable(tableModel);
        tableModel.setRowCount(0);


        // DefaultTableModel tableModel = (DefaultTableModel) tableModel.getModel();
        // tableModel.setRowCount(0);        

        tableModel.addColumn("Order Number");        
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Order Status");
        tableModel.addColumn("Email");
        tableModel.addColumn("Forename");
        tableModel.addColumn("Surname");
        tableModel.addColumn("House Number");
        tableModel.addColumn("Post Code");
        tableModel.addColumn("Road Name");
        tableModel.addColumn("City Name");


        tableModel.addColumn("Order Cost");

 
        DatabaseOperations dbOperations = new DatabaseOperations();
        ResultSet resultSet = dbOperations.getOrdersForStatus(con, orderStatus);

        while (resultSet.next()) {
            tableModel.addRow(new Object[]{
                    resultSet.getString("orderNumber"),
                    resultSet.getString("orderDate"),
                    resultSet.getString("orderStatus"),
                    resultSet.getString("email"),
                    resultSet.getString("forename"),
                    resultSet.getString("surname"),
                    resultSet.getString("houseNumber"),
                    resultSet.getString("postcode"),
                    resultSet.getString("roadName"),
                    resultSet.getString("cityName")

                    // resultSet.getBigDecimal("")
                    // resultSet.getInt("orderLineNumber"),
                    // resultSet.getString("quantity"),
                    // resultSet.getString("lineCost"),
                    // resultSet.getString("productCode"),
            });
        }
        resultSet.close();
        // con.close();
        // orderTable.setSize(100,50);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

}
