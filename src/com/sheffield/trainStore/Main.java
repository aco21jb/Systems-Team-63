// Import necessary libraries and classes
package com.sheffield.trainStore;

import com.sheffield.trainStore.model.DatabaseConnectionHandler;
import com.sheffield.trainStore.views.LoginView;
import com.sheffield.trainStore.views.RegisterView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

    
        // Create an instance of DatabaseConnectionHandler for managing database connections
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            RegisterView RegisterView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                RegisterView = new RegisterView(databaseConnectionHandler.getConnection());
                RegisterView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}