package com.sheffield.trainStore.model;

import com.sheffield.util.HashedPasswordGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperationsUser {

    // this.userId = userId;
    // this.email = email;
    // this.username = username;
    // this.roles = roles;

    // this.forename = forename;
    // this.surname = surname;
    // this.password_hash = password_hash;

    // public String verifyLogin(Connection connection, String username, char[] enteredPassword) {

    // public void registerUser(User newUser, Connection connection) throws SQLException {
    // public void registerUser(Connection connection, String emailId, char[] passwordChars) throws SQLException {

    public void registerUser(Connection connection, String emailId, char[] passwordChars, String forename,
             String surname, Integer houseNumber, String postcode, String roadName, String cityName) throws SQLException {

    try {

        //  create user object --- validation should happen


        User newUser = new User (emailId, passwordChars, forename, surname, houseNumber, postcode);

        // Create an SQL INSERT statement
        // String insertSQL = "INSERT INTO Users (email , "+
        // "password_hash) VALUES (?, ?)";

        String insertUserSQL = "INSERT INTO USER (email, forename,"+
        "surname, password, houseNumber, postcode) VALUES (?, ?, ?, ?, ?, ?)";

        // Prepare and execute the INSERT statement
        PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL);
        preparedStatement.setString(1, newUser.getEmailId());
        preparedStatement.setString(2, newUser.getForename());
        preparedStatement.setString(3, newUser.getSurname());
        String passwordString = new String(newUser.getPassword());
        preparedStatement.setString(4, passwordString);

        preparedStatement.setInt(5, newUser.getHouseNumber());
        preparedStatement.setString(6, newUser.getPostcode());

        // String hashedEnteredPassword = HashedPasswordGenerator.hashPassword(enteredPassword);
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println(rowsAffected + " row(s) inserted successfully.");



        // if adress already exists .. no creating new record as address can  be shared by more than one user
        String sql = "SELECT houseNumber, postcode FROM ADDRESS WHERE houseNumber = ? AND postcode = ?";

        PreparedStatement preparedStatementSql = connection.prepareStatement(sql);
        ResultSet resultSet = null;

        // Set the parameter for the prepared statement
        preparedStatementSql.setInt(1, houseNumber);
        preparedStatementSql.setString(2, postcode);

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
            preparedStatement1.setString(3, roadName);
            preparedStatement1.setString(4, cityName);
            int rowsAffected1 = preparedStatement1.executeUpdate();
            System.out.println(rowsAffected1 + " row(s) inserted successfully.");

        } else {
            System.out.println("Address already exists");
        }


        // by default create a role for customer with userid

        String sql1= "SELECT userId, email" +
                    " FROM USER WHERE email = ?";
        PreparedStatement statementUser = connection.prepareStatement(sql1);
        statementUser.setString(1, emailId);
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


            CurrentUser user = new CurrentUser(userId, emailId, getRolesForUserId(connection, userId));
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
     * @param username         The entered username.
     * @param enteredPassword  The entered password.
     * @return True if login is successful, false otherwise.
     */
    public boolean verifyLogin(Connection connection, String emailId, char[] enteredPassword) {
        try {
            // Query the database to fetch user information
            // String sql = "SELECT userId, password_hash, failed_login_attempts, " +
            //         "account_locked FROM Users WHERE username = ?";

            // Query the database to fetch user information
            String sql = "SELECT userId, email, password" +
                    " FROM USER WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String email = resultSet.getString("email");

                String storedPasswordHash = resultSet.getString("password");
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

    private static boolean verifyPassword(char[] enteredPassword, String storedPasswordHash) {
        try {
            //  NEED TO CHANGE for hashpassword
            // String hashedEnteredPassword = HashedPasswordGenerator.hashPassword(enteredPassword);
            // return hashedEnteredPassword.equals(storedPasswordHash);
            String enteredpasswordString = new String(enteredPassword);

            if (enteredpasswordString.equals(storedPasswordHash)){
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

    public Boolean verifyEmailID(Connection connection, String emailID) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT email " +
                    " FROM USER WHERE email = ?";
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
     * Promotes the selected user to the role of Moderator.
     *
     * @param connection    The database connection.
     * @param selectedUser  The username of the user to be promoted.
     */
    public void promoteToStaff(Connection connection, String selectedUser) {
        PreparedStatement preparedStatement = null;

        try {
            // Get the userId based on the username
            String userId = getUserIdByUserId(connection, selectedUser);

            // Prepare the SQL statement to update the user's role to "Moderator"
            String sql = "INSERT INTO ROLES (userId, role) VALUES (?, 'Staff')";
            preparedStatement = connection.prepareStatement(sql);

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, userId);

            // Execute the update
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
     * Gets the userId based on the username from the 'Users' table.
     *
     * @param connection The database connection.
     * @param username   The username for which to retrieve the userId.
     * @return The userId corresponding to the given username.
     */
    public String getUserIdByUserId(Connection connection, String userId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Prepare the SQL statement to select the userId based on the username
            String sql = "SELECT userId FROM USER WHERE userId = ?";
            preparedStatement = connection.prepareStatement(sql);

            // Set the parameter for the prepared statement
            preparedStatement.setString(1, userId);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a result is found
            if (resultSet.next()) {
                return resultSet.getString("userId");
            } else {
                System.out.println("User with username " + userId + " not found.");
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
     * Retrieves a result set containing all usernames from the 'Users' table.
     *
     * @param connection The database connection.
     * @return A result set containing all usernames.
     */
    public ResultSet getAllUsers(Connection connection) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            // Execute the query to select all usernames from the 'Users' table
            String query = "SELECT u.userId, u.username, r.role FROM USER u, ROLES r WHERE u.userId=r.userId";

            // Create a statement
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return null;
    }



}

