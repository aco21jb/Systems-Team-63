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

    public void removeOrder(Connection con, Order order) throws SQLException {
        try {
            String removeStatement = "DELETE FROM ORDERS WHERE orderNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(removeStatement);
            preparedStatement.setInt(1, order.getOrderNumber());
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



    // Update an existing book in the database
    public void updateStock(Connection connection, String productInput, Integer newSTock) throws SQLException {

        try {

            String updateSQL = "UPDATE PRODUCTS SET stock=? "+
            " WHERE productCode=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setInt(1,newSTock);
            preparedStatement.setString(2, productInput);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for product code: " );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
        }

        public void updateOrderStatus(Connection connection, String orderNumber) throws SQLException {

        try {
                //    String updateSQL = "UPDATE ORDERS SET orderStatus = ? " + "\'" + OrderStatus.FULFILLED + "\'" +
                //        " WHERE orderNumber= " + "\'" + orderNumber + "\'" ;

            String updateSQL = "UPDATE ORDERS SET orderStatus = "  + "\'" + OrderStatus.FULFILLED + "\'" +  "WHERE orderNumber= ?" ;

            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, orderNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully.");
            } else {
                System.out.println("No rows were updated for Order: " );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
        }


        public void deleteOrderStatus(Connection connection, String orderNumber) throws SQLException {

            try {

                String removeStatementOrderLine = "DELETE FROM ORDER_LINES WHERE orderNumber = ? ";
                PreparedStatement preparedStatementOl = connection.prepareStatement(removeStatementOrderLine);
                preparedStatementOl.setString(1, orderNumber);

                int rowsAffected = preparedStatementOl.executeUpdate();

                if (rowsAffected > 0) {
                        String removeStatementOrder = "DELETE FROM ORDERS WHERE orderNumber = ? ";

                        PreparedStatement preparedStatementO = connection.prepareStatement(removeStatementOrder);
                        preparedStatementO.setString(1, orderNumber);
                        rowsAffected = preparedStatementO.executeUpdate();

                        System.out.println(rowsAffected + " row(s) deleted successfully.");
                } else {
                    System.out.println("No rows were deleted for Order: " );
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;// Re-throw the exception to signal an error.
            }
        }



        public ResultSet getOrdersForStatus(Connection con, OrderStatus orderStatus) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

            try {

                String query = "SELECT o.orderNumber, o.orderDate, o.orderStatus, o.userId, u.userID, u.email, u.forename, u.surname, " +
                        " u.houseNumber, u.postcode,  ad.houseNumber, ad.postcode, ad.roadName, ad.cityName FROM ORDERS o, USERS u, ADDRESS ad WHERE  "+
                        " o.orderStatus = " + "\'" + orderStatus + "\'" + " AND o.userId = u.userID AND u.houseNumber = ad.houseNumber AND " +
                        "u.postcode = ad.postcode";

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



        public ResultSet getOrdersForStatus(Connection con, String userId) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

            try {

                String query = "SELECT o.orderNumber, o.orderDate, o.orderStatus, o.userId, u.userID, u.email, u.forename, u.surname, " +
                        " u.houseNumber, u.postcode,  ad.houseNumber, ad.postcode, ad.roadName, ad.cityName FROM ORDERS o, USERS u, ADDRESS ad WHERE  "+
                        " u.userID = " + "\'" + userId + "\'" + " AND o.userId = u.userID AND u.houseNumber = ad.houseNumber AND " +
                        "u.postcode = ad.postcode";                

         

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
        
        
        public ResultSet getOrderLineForOrderNumber(Connection con, String orderNumber) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {

            //  // Execute the query to select all products from the "PRODUCTS" table
            // String query = "SELECT o.orderNumber, o.orderDate, o.orderStatus, ol.orderLineNumber, ol.quantity, " +
            //         "ol.lineCost, ol.productCode FROM ORDERS o, ORDER_LINES ol WHERE o.orderNumber = ol.orderNumber AND o.orderStatus = " + "\'" + orderStatus + "\' " ;


            String query = "SELECT ol.orderNumber, ol.orderLineNumber, ol.quantity, ol.lineCost, ol.productCode, p.productName, p.brandName " +
                    " FROM ORDER_LINES ol , PRODUCTS p WHERE  ol.productCode = p.productCode AND ol.orderNumber = " + "\'" + orderNumber + "\' " ;

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


    public ResultSet getOrderLine(Connection con, int orderNumber, int orderLineNumber) throws SQLException {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM ORDER_LINES WHERE (orderNumber, orderLineNumber) = (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, orderNumber);
            preparedStatement.setInt(2, orderLineNumber);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return resultSet;
    }

    public ResultSet getOrderLines(Connection con, int orderNumber) throws SQLException {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM ORDER_LINES WHERE orderNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, orderNumber);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return resultSet;
    }

    public void addTrainSet(Connection con, String[] codes, String setCode) throws SQLException {
        String query = "INSERT INTO SETS (setCode,productCode) VALUES (?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        for (String c: codes) {
            try {
                preparedStatement.setString(1,setCode);
                preparedStatement.setString(2,c);
                preparedStatement.addBatch();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        preparedStatement.executeBatch();
    }

    public void addTrackPack(Connection con, String[] codes, String packCode) throws SQLException {
        String query = "INSERT INTO TRACK_PACK (trackPackCode,productCode) VALUES (?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        for (String c: codes) {
            try {
                preparedStatement.setString(1,packCode);
                preparedStatement.setString(2,c);
                preparedStatement.addBatch();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        preparedStatement.executeBatch();
    }

}
