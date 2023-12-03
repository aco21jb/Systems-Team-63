// Import necessary libraries and classes
package com.sheffield.trainStore;

import com.sheffield.trainStore.model.DatabaseConnectionHandler;
import com.sheffield.trainStore.model.DatabaseOperations;
import com.sheffield.trainStore.model.Product;
import com.sheffield.trainStore.views.BankDetailsView;
import com.sheffield.trainStore.views.LoginView;
import com.sheffield.trainStore.views.ProductsPage;
import com.sheffield.trainStore.views.RegisterView;

import javax.swing.*;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {


        // Create an instance of DatabaseConnectionHandler for managing database connections
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        DatabaseOperations dbop = new DatabaseOperations();


        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            RegisterView RegisterView = null;
            // ProductsPage productsPage = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();
                //dbop.addProduct(databaseConnectionHandler.getConnection(),(new Product("c3fds7hgjy","Hornby","controller 1",new BigDecimal(10.50),6,"gfd","50",null)));
                RegisterView = new RegisterView(databaseConnectionHandler.getConnection());
                RegisterView.setVisible(true);
                // productsPage = new ProductsPage(databaseConnectionHandler.getConnection());
                // productsPage.setVisible(true);
                //BankDetailsView bankDetailsView = new BankDetailsView(databaseConnectionHandler.getConnection());
                //bankDetailsView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
