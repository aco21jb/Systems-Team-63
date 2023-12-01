package com.sheffield.trainStore.views;

import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.CurrentUser;
import com.sheffield.trainStore.model.CurrentUserManager;
import com.sheffield.trainStore.model.OrderLine;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class OrderView extends JPanel {

    public static JTable orderTable;
    public OrderView(Connection con, int orderNumber) throws SQLException {

        // Create a JPanel to hold the components
        JPanel panel = new JPanel(new GridLayout(2, 2));
        this.add(panel);

        panel.setLayout(new GridLayout(12, 2));
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        panel.setBorder(blackLine);
        DefaultTableModel tableModel = new DefaultTableModel();
        orderTable = new JTable(tableModel);
        tableModel.addColumn("Order Number");
        tableModel.addColumn("Order Line Number");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Product Code");

        CurrentUser currentUser = CurrentUserManager.getCurrentUser();
        String currentUserID = currentUser.getUserId();

        DatabaseOperations databaseOperations = new DatabaseOperations();
        ResultSet resultSet = databaseOperations.getOrderLines(con, orderNumber);

        while (resultSet.next()) {
            tableModel.addRow(new Object[]{
                    resultSet.getInt("orderNumber"),
                    resultSet.getInt("orderLineNumber"),
                    resultSet.getInt("quantity"),
                    resultSet.getBigDecimal("lineCost"),
                    resultSet.getString("productCode")
            });
        }
        resultSet.close();
        // con.close();
        JScrollPane scrollPane = new JScrollPane(orderTable);
        this.add(scrollPane, BorderLayout.CENTER);

        panel.add(new JLabel());

        JButton removeButton = new JButton("Remove");
        panel.add(removeButton);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String orderLineNumberString =
                        JOptionPane.showInputDialog("Which order line would you like to remove? ");
                int orderLineNumber = Integer.parseInt(orderLineNumberString);

                try {
                    ResultSet orderLineResult = databaseOperations.getOrderLine(con, orderNumber, orderLineNumber);
                    if (orderLineResult.next()) {
                        int orderNum = orderLineResult.getInt("orderNumber");
                        int orderLineNum = orderLineResult.getInt("orderLineNumber");
                        int quantity = orderLineResult.getInt("quantity");
                        BigDecimal lineCost = orderLineResult.getBigDecimal("lineCost");
                        String productCode = orderLineResult.getString("productCode");
                        OrderLine orderLine = new OrderLine(orderNum, orderLineNum, quantity, lineCost, productCode);
                        databaseOperations.removeOrderLine(con, orderLine);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
