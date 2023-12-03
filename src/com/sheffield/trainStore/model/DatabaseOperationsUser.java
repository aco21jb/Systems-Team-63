package com.sheffield.trainStore.model;

import com.sheffield.util.HashedPasswordGenerator;

import com.sheffield.trainStore.model.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class DatabaseOperationsUser {

     /**
     * Registers the new User.
     *
     * @param connection       The database connection.
     * @param user object        .
     */    

     public void registerUser(Connection connection, User newUser) throws SQLException {                
    try {

        // if adress already exists .. no creating new record as address can  be shared by more than one user
        //  Address should be inserted first (before the Users) because User table has foreign keys of Address table

        String sql = "SELECT houseNumber, postcode FROM ADDRESS WHERE houseNumber = ? AND postcode = ?";

        PreparedStatement preparedStatementSql = connection.prepareStatement(sql);
        ResultSet resultSet = null;

        // Set the parameter for the prepared statement
        // preparedStatementSql.setInt(1, houseNumber);
        // preparedStatementSql.setString(2, postcode);

        // Set the parameter for the prepared statement
        preparedStatementSql.setInt(1, newUser.getHouseNumber());
        preparedStatementSql.setString(2, newUser.getPostcode());        

        // Execute the query
        resultSet = preparedStatementSql.executeQuery();

        // Check if a result is found
        if (! resultSet.next()) {

            String insertAddressSQL = "INSERT INTO ADDRESS (housenumber, postcode,"+
            "roadName, cityName) VALUES (?, ?, ?, ?)";

            // Prepare and execute the INSERT statement
            PreparedStatement preparedStatement1 = connection.prepareStatement(insertAddressSQL);
            preparedStatement1.setInt(1, newUser.getHouseNumber());
            preparedStatement1.setString(2, newUser.getPostcode());
            preparedStatement1.setString(3, newUser.getRoadname());
            preparedStatement1.setString(4, newUser.getCityname());
            int rowsAffected1 = preparedStatement1.executeUpdate();
            System.out.println(rowsAffected1 + " row(s) inserted successfully.");

        } else {
            System.out.println("Address already exists");
        }        

        String insertUserSQL = "INSERT INTO USERS (userID, email, forename,"+
        "surname, passwordHash, houseNumber, postcode) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Prepare and execute the INSERT statement
        PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL);
        preparedStatement.setString(1, newUser.getUserId());
        preparedStatement.setString(2, newUser.getEmailId());
        preparedStatement.setString(3, newUser.getForename());
        preparedStatement.setString(4, newUser.getSurname());

        String passworHash = HashedPasswordGenerator.hashPassword(newUser.getPassword());        
        // String passwordString = new String(newUser.getPassword());
        String passwordString = passworHash;

        preparedStatement.setString(5, passwordString);

        preparedStatement.setInt(6, newUser.getHouseNumber());
        preparedStatement.setString(7, newUser.getPostcode());

        // String hashedEnteredPassword = HashedPasswordGenerator.hashPassword(enteredPassword);
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println(rowsAffected + " row(s) inserted successfully.");

        // by default create a role for customer with userid

        String sql1= "SELECT userId, email" +
                    " FROM USERS WHERE email = ?";
        PreparedStatement statementUser = connection.prepareStatement(sql1);
        statementUser.setString(1, newUser.getEmailId());
        ResultSet resultSetRole = statementUser.executeQuery();

        // Check if a result is found
        if (resultSetRole.next()) {

            String userId = resultSetRole.getString("userId");

            String insertRoleSQL = "INSERT INTO ROLES (userId, "+
            "role ) VALUES (?, ?)";

            // Prepare and execute the INSERT statement
            PreparedStatement preparedStatement2 = connection.prepareStatement(insertRoleSQL);
            preparedStatement2.setString(1, userId);
            preparedStatement2.setString(2, "Customer");
            int rowsAffected1 = preparedStatement2.executeUpdate();
            System.out.println(rowsAffected1 + " row(s) inserted successfully.");


            CurrentUser user = new CurrentUser(userId, newUser.getEmailId(), getRolesForUserId(connection, userId));
            CurrentUserManager.setCurrentUser(user);

        }

    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Re-throw the exception to signal an error.
    }
    }


    /**
     * Verifies the login credentials of a user.
     *
     * @param connection       The database connection.
     * @param emailId         The entered username.
     * @param enteredPassword  The entered password.
     * @return True if login is successful, false otherwise.
     */
    public boolean verifyLogin(Connection connection, String emailId, char[] enteredPassword) {
        try {
            // Query the database to fetch user information
            // String sql = "SELECT userId, password_hash, failed_login_attempts, " +
            //         "account_locked FROM Users WHERE username = ?";

            // Query the database to fetch user information
            String sql = "SELECT userId, email, passwordHash" +
                    " FROM USERS WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String email = resultSet.getString("email");

                String storedPasswordHash = resultSet.getString("passwordHash");
          
                // int failedLoginAttempts = resultSet.getInt("failed_login_attempts");
                // boolean accountLocked = resultSet.getBoolean("account_locked");

                if (verifyPassword(enteredPassword, storedPasswordHash)) {
                        CurrentUser user = new CurrentUser(userId, email, getRolesForUserId(connection, userId));
                        CurrentUserManager.setCurrentUser(user);
                        System.out.println("Current User :" + CurrentUserManager.getCurrentUser());
                        System.out.println("Current User from User object :" + user.getUserId());
                        System.out.println("Current Roles from User object :" + user.getRoles());

                        System.out.println("Login successful for user: " );
                        return true;
                } else {
                    System.out.println("Incorrect password. Failed login attempts: " );
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User not found.");
        return false;
    }



    /**
     * Verifies the password.
     *
     * @param password         The stored password.
     * @param enteredPassword  The entered password.
     * @return True if login is successful, false otherwise.
     */    
    private static boolean verifyPassword(char[] enteredPassword, String storedPasswordHash) {
        try {
     
            String enteredpasswordHashed = HashedPasswordGenerator.hashPassword(enteredPassword);        
    
            if (enteredpasswordHashed.equals(storedPasswordHash)){
                // return hashedEnteredPassword.equals(storedPasswordHash);
                return true;
            }
            else {
                return false; 
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies the emailID.
     *
     * @param emailID         The Email ID.
     * @return True if successful, false otherwise.
     */        
    public Boolean verifyEmailID(Connection connection, String emailID) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT email " +
                    " FROM USERS WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }


     /**
     * Gets the user roles based on the userId.
     *
     * @param connection  The database connection.
     * @param userId  The userId for which to retrieve roles.
     * @return The user's roles.
     */
    private List<Role> getRolesForUserId(Connection connection, String userId) {
        List<Role> listOfRoles = new ArrayList<>();
        try {
            String sql = "SELECT role FROM ROLES WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listOfRoles.add(Role.fromString(resultSet.getString("role")));
            }
            return listOfRoles;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    /**
     * Remove the selected user to the role of Staff.
     *
     * @param connection    The database connection.
     * @param selectedUser  The username of the user to be promoted.
     */
    public void removeFromStaff(Connection connection, String selectedUser) {
        PreparedStatement preparedStatement = null;

        try {
            // Get the userId based on the username
            String userId = getUserIdByEmailId(connection, selectedUser);

            // Prepare the SQL statement to delete the user's role to "Staff"
            String deleteSQL = "DELETE FROM ROLES WHERE userId = ? AND role = " + "\'" + Role.STAFF + "\' ";

            preparedStatement = connection.prepareStatement(deleteSQL);

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, userId);

            // Execute the query
            preparedStatement.executeUpdate();

            // Additional actions may be performed here if needed after the user is promoted
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        } finally {
            // Close the prepared statement in a finally block to ensure it's closed even if an exception occurs
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception according to your application's needs
            }
        }
    }

   /**
     * Promotes the selected user to the role of Staff.
     *
     * @param connection    The database connection.
     * @param selectedUser  The username of the user to be promoted.
     */
    public void promoteToStaff(Connection connection, String selectedUser) {
        PreparedStatement preparedStatement = null;

        try {
            // Get the userId based on the username
            String userId = getUserIdByEmailId(connection, selectedUser);

            // Prepare the SQL statement to insert the user's role to "Staff"
            String sql = "INSERT INTO ROLES (userId, role) VALUES (?, 'Staff')";
            preparedStatement = connection.prepareStatement(sql);

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, userId);

            // Execute the query
            preparedStatement.executeUpdate();

            // Additional actions may be performed here if needed after the user is promoted
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        } finally {
            // Close the prepared statement in a finally block to ensure it's closed even if an exception occurs
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception according to your application's needs
            }
        }
    }

    /**
     * Gets the userId based on the email from the 'Users' table.
     *
     * @param connection The database connection.
     * @param emailId   The emailId for which to retrieve the userId.
     * @return The userId corresponding to the given user.
     */
    public String getUserIdByEmailId(Connection connection, String emailId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT userId FROM USERS WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);

            // Set the parameter for the prepared statement
            preparedStatement.setString(1, emailId);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a result is found
            if (resultSet.next()) {
                return resultSet.getString("userId");
            } else {
                System.out.println("User with username " + emailId + " not found.");
                return null; // Or throw an exception or handle the case as appropriate for your application
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            return null;
        } finally {
            // Close the resources in a finally block to ensure they're closed even if an exception occurs
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception according to your application's needs
            }
        }
    }

    /**
     * Retrieves a result set containing user information from the 'Users' table.
     *
     * @param connection The database connection.
     * @param userId The database connection. 
     * @return A result set containing all usernames.
     */
    public ResultSet getUserDetail(Connection connection, String userId ) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            String query = "SELECT u.userId, u.email, u.forename, u.surname, u.housenumber, u.postcode, ad.roadname, " 
            + " ad.cityname FROM USERS u, ADDRESS ad WHERE u.housenumber = ad.housenumber " +
                    "AND u.postcode = ad.postcode AND userID = " + "\'" + userId + "\'";

            // Create a statement
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return null;
    }

    /**
     * Update User with changed personal information.
     *
     * @param connection The database connection.
     * @param user object The database connection. 
     */    

     public void updateUser(Connection connection, User user) throws SQLException {                     
            try {
                ResultSet resultSet = null;
   
                String updateSQL = "UPDATE USERS SET email=?, forename=?,"+
                "surname=? WHERE userId=?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
    
                preparedStatement.setString(1, user.getEmailId());
                preparedStatement.setString(2, user.getForename());
                preparedStatement.setString(3, user.getSurname());
                preparedStatement.setString(4, user.getUserId());
               
                int rowsAffected = preparedStatement.executeUpdate();
   
                if (rowsAffected > 0) {
                    String updateSQL1 = "UPDATE ADDRESS SET roadName=?, cityName=? "+
                    " WHERE houseNumber=? AND postcode = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(updateSQL1);

                    preparedStatement1.setString(1, user.getRoadname());
                    preparedStatement1.setString(2, user.getCityname());                    
                    preparedStatement1.setInt(3, user.getHouseNumber());
                    preparedStatement1.setString(4, user.getPostcode());
                    int rowsAffected1 = preparedStatement1.executeUpdate();
                    if (rowsAffected1 > 0) {
                       System.out.println(rowsAffected1 + " row(s) updated successfully.");
                    }
                                 
                } else {
                    System.out.println("No rows were updated for UserID: " );
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;// Re-throw the exception to signal an error.
            }
        }
    

    /**
     * Retrieves a result set all users from the 'Users' table.
     *
     * @param connection The database connection.
     * @return A result set containing all user information.
     */
    public ResultSet getAllUsers(Connection connection) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            String query = "SELECT u.userId, u.email, u.forename, u.surname, r.role FROM USERS u, ROLES r WHERE " +
                    "u.userId=r.userId";

            // Create a statement
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return null;
    }



    /**
     * Retrieves a result set containing  order table for particular order status.
     *
     * @param connection The database connection.
     * @param OrderStatus The database connection.
     * 
     * @return A result set containing all usernames.
     */
    public ResultSet getOrderDetails(Connection connection ,  OrderStatus orderStatus ){
        ResultSet resultSet = null;
        PreparedStatement statement = null;


        try {
            String query = "SELECT o.orderNumber, o.orderDate, o.userId, od.orderNumber, od.quantity, od.lineCost " 
               + "FROM ORDERS o, ORDER_LINES od WHERE o.orderNumber=od.orderNumber AND o.orderStatus = " + "\'" +
                    orderStatus + "\'";


            // Create a statement
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return null;
    }

    /**
     * Retrieves a result set containing all Staff Users from the 'Users' table.
     *
     * @param connection The database connection.
     * @return A result set containing all Staff.
     */
    public ResultSet getAllUsersStaff(Connection connection) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            String query = "SELECT u.userId, u.email, u.forename, u.surname, r.role FROM USERS u, ROLES r WHERE " +
                    "u.userId=r.userId AND r.role" + " = " + "\'" + Role.STAFF + "\'";

            // Create a statement
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return null;
    }    

    
     /**
     * to check whether user is alrady staff.
     *
     * @param connection The database connection.
     * @param emailId  Email Id.

     * @return A result set containing all usernames.
     */
    public Boolean IsAlreadyStaff(Connection connection, String emailId) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {

            String userId = getUserIdByEmailId(connection, emailId);

            String sql = "SELECT userId, role FROM ROLES WHERE userId = ? AND role" + " = " + "\'" + Role.STAFF + "\'";
            preparedStatement = connection.prepareStatement(sql);
            // Set the parameter for the prepared statement
            preparedStatement.setString(1, userId);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a result is found
            if (resultSet.next()) {
                return true;
           }
           else {
              return false;
           }

            // return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return null;
    }

    public Boolean alreadyHasBankDetails(Connection connection, String userID) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {

            //String userId = getUserIdByEmailId(connection, emailId);

            String sql = "SELECT userID FROM BANK_DETAILS WHERE userID = ?";
            preparedStatement = connection.prepareStatement(sql);
            // Set the parameter for the prepared statement
            preparedStatement.setString(1, userID);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a result is found
            if (resultSet.next()) {
                return true;
            }
            else {
                return false;
            }

            // return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return null;
    }

}

