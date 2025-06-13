package com.pluralsight;

import com.pluralsight.dao.ShipperDataManager;
import com.pluralsight.models.Shipper;
import com.pluralsight.services.ShipperServices;
import com.pluralsight.ui.UserInterface;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        // Make sure user gave us a username and password as args
        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>"
            );
            System.exit(1);
        }

        // Get the username and password from command line arg
        String username = args[0];
        String password = args[1];

        // Database connection
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Create instance of ShipperDataManager
        ShipperDataManager dataManager = new ShipperDataManager(dataSource);
        // Create instance of ShipperServices
        ShipperServices service = new ShipperServices(dataManager);

        // Try with resources creates Scanner
        try (Scanner userInput = new Scanner(System.in)) {

            // Create instance of UserInterface with passed Scanner and ShipperServices
            UserInterface ui = new UserInterface(userInput, service);

            // Starts user interface
            ui.run();

            // Catch exceptions
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Nested try catch to close database
            try {
                dataSource.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
