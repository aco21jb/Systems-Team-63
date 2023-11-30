package com.sheffield.trainStore.model;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sheffield.trainStore.model.OrderStatus;

public class DatabaseOperations {
    public void addProduct(Connection con, Product product) throws SQLException {
        try {
            String insertStatement = "INSERT INTO PRODUCTS (productCode, brandName, productName, retailPrice, stock, " +
            "gauge, eraCode, dccCode, productType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertStatement);
            preparedStatement.setString(1, product.getProductCode());
            preparedStatement.setString(2, product.getBrandName());
            preparedStatement.setString(3, product.getProductName());
            preparedStatement.setBigDecimal(4, product.getRetailPrice());
            preparedStatement.setInt(5, product.getStock());
            preparedStatement.setString(6, product.getGauge());
            preparedStatement.setString(7, product.getEraCode());
            preparedStatement.setString(8, product.getDccCode());
            preparedStatement.setString(9, String.valueOf(product.getProductType()));

            preparedStatement.executeUpdate();

            // check if you need to insert into trackPAck or sets tables and if so insert (do later somewhere)

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void removeProduct(Connection con, Product product) throws SQLException {
        try {
            String removeStatement = "DELETE FROM PRODUCTS WHERE productCode = ?";
            PreparedStatement preparedStatement = con.prepareStatement(removeStatement);
            preparedStatement.setString(1,product.getProductCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void addOrderLine(Connection con, OrderLine orderLine) throws SQLException {
        try {
            String insertStatement = "INSERT INTO ORDER_LINES (orderNumber, orderLineNumber, quantity, " +
                    "lineCost, productCode) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertStatement);
            preparedStatement.setInt(1, orderLine.getOrderNumber());
            preparedStatement.setInt(2, orderLine.getOrderLineNumber());
            preparedStatement.setInt(3, orderLine.getQuantity());
            preparedStatement.setBigDecimal(4, orderLine.getLineCost());
            preparedStatement.setString(5, orderLine.getProductCode());

            preparedStatement.executeUpdate();

            // check if you need to insert into trackPAck or sets tables and if so insert (do later somewhere)

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void removeOrderLine(Connection con, OrderLine orderLine) throws SQLException {
        try {
            String removeStatement = "DELETE FROM ORDER_LINES WHERE (orderNumber, orderLineNumber) IN (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(removeStatement);
            preparedStatement.setInt(1, orderLine.getOrderNumber());
            preparedStatement.setInt(2, orderLine.getOrderLineNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void addOrder(Connection con, Order order) throws SQLException {
        try {
            String addStatement = "INSERT INTO ORDERS VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(addStatement);
            preparedStatement.setInt(1, order.getOrderNumber());
            preparedStatement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            preparedStatement.setString(3, order.getOrderStatus().name());
            preparedStatement.setString(4, order.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResultSet getAllProducts(Connection con) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            // Execute the query to select all products from the "PRODUCTS" table
            String query = "SELECT p.productCode, p.brandName, p.productName, p.retailPrice, p.stock, " +
                    "p.gauge, p.eraCode, p.dccCode FROM PRODUCTS p";

            // Create a statement
            statement = con.prepareStatement(query);

            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            throw e;
        }
        // return null;
    }

    public ResultSet getProducts(Connection con) throws SQLException {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM PRODUCTS";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return resultSet;
    }

    public ResultSet getFilteredProducts(Connection con, char type) throws SQLException {
        ResultSet filteredSet = null;
        System.out.println(type);
        try {
            String query = "SELECT * FROM PRODUCTS WHERE productType = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,String.valueOf(type));
            filteredSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredSet;
    }

    public ResultSet getSearchProducts(Connection con, String search) throws SQLException {
        ResultSet filteredSet = null;
        search = "%" + search + "%";
        try {
            String query = "SELECT * FROM PRODUCTS WHERE productName LIKE ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,search);
            filteredSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredSet;
    }


    public ResultSet getProduct(Connection con, String productInput) throws SQLException {
        ResultSet productResult = null;
        PreparedStatement statement = null;
        try {
            String query = "SELECT productCode, retailPrice, stock FROM PRODUCTS WHERE productName = ?";
            statement = con.prepareStatement(query);
            statement.setString(1, productInput);
            productResult = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return productResult;
    }


    public ResultSet getOrdersForStatus(Connection con, OrderStatus orderStatus) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {

             // Execute the query to select all products from the "PRODUCTS" table
            String query = "SELECT o.orderNumber, o.orderDate, o.orderStatus, ol.orderLineNumber, ol.quantity, " +
                    "ol.lineCost, ol.productCode FROM ORDERS o, ORDER_LINES ol WHERE o.orderNumber = ol.orderNumber AND o.orderStatus = " + "\'" + orderStatus + "\' " ;


            // Create a statement
            statement = con.prepareStatement(query);

            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            throw e;
        }
        // return null;
    }

}
