package trainStore.model;

import trainStore.Product;
import trainStore.util.HashedPasswordGenerator;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {
    public void addProduct(Connection con, trainStore.Product product) throws SQLException {
        try {
            String insertStatement = "INSERT INTO PRODUCTS (productCode, brandName, productName, retailPrice, stock," +
            "gauge, eraCode, dccCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertStatement);
            preparedStatement.setString(1, product.getProductCode());
            preparedStatement.setString(2, product.getBrandName());
            preparedStatement.setString(3, product.getProductName());
            preparedStatement.setBigDecimal(4, product.getRetailPrice());
            preparedStatement.setInt(5, product.getStock());
            preparedStatement.setString(6, product.getGauge());
            preparedStatement.setString(7, product.getEraCode());
            preparedStatement.setString(8, product.getDccCode());

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

    public String verifyLogin(Connection connection, String username, char[] enteredPassword) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT userID, passwordHash, failedLoginAttempts, " +
                    "accountLocked FROM USERS WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("userID");
                String storedPasswordHash = resultSet.getString("passwordHash");
                int failedLoginAttempts = resultSet.getInt("failedLoginAttempts");
                boolean accountLocked = resultSet.getBoolean("accountLocked");

                // Check if the account is locked
                if (accountLocked) {
                    return "Account is locked. Please contact support.";
                } else {
                    // Verify the entered password against the stored hash
                    if (verifyPassword(enteredPassword, storedPasswordHash)) {
                        // Update the last login time
                        sql = "UPDATE USERS SET lastLogin = CURRENT_TIMESTAMP, " +
                                "failedLoginAttempts = 0 WHERE userID = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, userId);
                        statement.executeUpdate();
                        return "Login successful for user: " + username;
                    } else {
                        // Incorrect password, update failed login attempts
                        failedLoginAttempts++;
                        sql = "UPDATE USERS SET failedLoginAttempts = ? WHERE userID = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setInt(1, failedLoginAttempts);
                        statement.setString(2, userId);
                        statement.executeUpdate();

                        return "Incorrect password. Failed login attempts: " + failedLoginAttempts;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User not found.";
    }

    private static boolean verifyPassword(char[] enteredPassword, String storedPasswordHash) {
        try {
            String hashedEnteredPassword = HashedPasswordGenerator.hashPassword(enteredPassword);
            return hashedEnteredPassword.equals(storedPasswordHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}