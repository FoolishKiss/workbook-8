package com.pluralsight;

import com.pluralsight.dao.DataManager;
import com.pluralsight.services.ActorServices;
import com.pluralsight.services.FilmServices;
import com.pluralsight.ui.UserInterface;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
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
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Setup data access layer and services
        DataManager dataManager = new DataManager(dataSource);
        ActorServices actorServices = new ActorServices(dataManager);
        FilmServices filmServices = new FilmServices(dataManager);


        // Outer try with resources to set up scanner and data source
        try (Scanner userInput = new Scanner(System.in)) {

            // Create user interface
            UserInterface ui = new UserInterface(userInput, actorServices, filmServices);

            // Show welcome message
            ui.welcome();

            // Flag to control app loop
            boolean appOn = true;

            // Loop until user exits
            while (appOn) {

                // try block to catch errors
                try {
                    // Show the menu
                    ui.menu();
                    // Get valid choice
                    int choice = ui.getValidMenuChoice();

                    // Switch statement to pick method for user choice
                    switch (choice) {
                        case 1:
                            ui.searchActorsByLastName();
                            break;
                        case 2:
                            ui.goodbye();
                            appOn = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }

                    // Divider
                    if (appOn) {
                        ui.divider();
                    }
                } catch (Exception e) {
                    ui.errorMessage(e.getMessage());
                }
            }

            // Catch and print any exception
        } catch (Exception e) {
            System.err.println("A critical error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {

            // Close the datasource
            try {
                dataSource.close();
            } catch (SQLException e) {
                System.err.println("Error closing datasource: " + e.getMessage());
            }
        }
    }



}
