package com.pluralsight;

import com.pluralsight.dao.DataManager;
import com.pluralsight.services.ActorServices;
import com.pluralsight.services.FilmServices;
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

            System.out.println("=== Sakila Movie Database ===\n");

            // Ask user for actor last name and stores it in variable lastName
            System.out.println("Enter actor's last name: ");
            String lastName = userInput.nextLine().trim();

            // Call method to search database for actors matching user input
            actorServices.showActorsByLastName(lastName);

            // Ask user to enter actor id to show their movies
            System.out.println("\nEnter actor's ID to see their movies: ");

            // Inner try to handle input errors
            try {// Convert String to int
                 int actorId = Integer.parseInt(userInput.nextLine().trim());

                 // Runs filmServices showFilmByActorId method
                 filmServices.showFilmsByActorId(actorId);

              // Handles invalid number
            } catch (NumberFormatException e) {
                System.out.println("Invalid actor ID");
            }

            // Catch and print any exception
        } catch (Exception e) {
            System.err.println("An Error occurred: " + e.getMessage());
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
