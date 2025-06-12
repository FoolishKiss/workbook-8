package com.pluralsight;

import com.pluralsight.dao.DataManager;
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

        // Setup data access layer
        DataManager dataManager = new DataManager(dataSource);


        // Outer try with resources to set up scanner and data source
        try (Scanner userInput = new Scanner(System.in); BasicDataSource dataSource = new BasicDataSource()) {

                // Ask user for actor last name and stores it in variable lastName
                System.out.println("Enter actor's last name: ");
                String lastName = userInput.nextLine();

                // Call method to search database for actors matching user input
                searchActorsByLastName(connection, lastName);

                // Ask user for actor first name and stores it in variable firstName
                System.out.println("\nEnter actor's first name: ");
                String firstName = userInput.nextLine();

                // Ask user for actor last name again and stores it in variable lastName
                System.out.println("Enter actor's last name again: ");
                lastName = userInput.nextLine();

                // Call method to show all movies for actors matching user input
                showFilmsByActor(connection, firstName, lastName);

            // Any database error
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
