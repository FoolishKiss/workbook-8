package com.pluralsight;

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

        // Outer try with resources to set up scanner and data source
        try (Scanner userInput = new Scanner(System.in); BasicDataSource dataSource = new BasicDataSource()) {

            // Data source
            dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            // Inner try  with resources to open database connection via data source
            try (Connection connection = dataSource.getConnection()) {

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
            }
            // Any database error
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to search for actors by last name
    private static void searchActorsByLastName(Connection connection, String lastName) {

        // Prepare the sql statement
        String query = """
                SELECT actor_id, first_name, last_name
                FROM actor
                WHERE last_name = ?
                """;

        try (   // Open try with resources to prepare the sql query
                PreparedStatement statement = connection.prepareStatement(query)
        ) { // Bind lastName variable to the first ? in sql query
            statement.setString(1, lastName);

            // Execute the query
            try (ResultSet results = statement.executeQuery()) {

                // Flag - haven't found any results yet
                boolean found = false;

                // Print out header
                System.out.printf("%-5s %-15s %-15s\n", "ID", "First Name", "Last Name");
                System.out.println("--------------------------------");

                // Loops through each row and prints out results
                while (results.next()) {
                    int actorId = results.getInt("actor_id");
                    String firstName = results.getString("first_name");
                    String lastNameDb = results.getString("last_name");
                    System.out.printf("\n%-5s %-15s %-15s\n", actorId, firstName, lastNameDb);

                    // If at least one row exists enter loop
                    found = true;
                }
                // If no records found print out message
                if (!found) {
                    System.out.println("No actors found with last name: " + lastName);
                }
            }
            // If errors print out
        } catch (SQLException e) {
            System.out.println("Error getting actors: " + e.getMessage());
        }
    }
    // Method to search for all films actor is in
    private static void showFilmsByActor(Connection connection, String firstName, String lastName) {

        // Prepare the sql statement
        String query = """
                SELECT f.film_id, f.title
                FROM actor a
                JOIN film_actor fa ON a.actor_id = fa.actor_id
                JOIN film f ON fa.film_id = f.film_id
                WHERE a.first_name = ? and a.last_name = ?
                """;
        try( // Open try with resources to prepare the sql query
             PreparedStatement statement = connection.prepareStatement(query)
        ) { // Bind firstName variable to the first and lastName variable to second ? in sql query
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            // Execute query
            try (ResultSet results = statement.executeQuery()) {

                // Flag - haven't found any results yet
                boolean found = false;

                // Header
                System.out.printf("%-5s %-50s\n", "ID", "Title");
                System.out.println("--------------------------------------");

                // Loops through each row and prints out results
                while (results.next()) {
                    int filmId = results.getInt("film_id");
                    String title = results.getString("title");
                    System.out.printf("%-5s %-50s\n", filmId, title);

                    // If at least one row exists enter loop
                    found = true;
                }
                // If no records found print out message
                if (!found) {
                    System.out.println("No films found for: " +firstName + " " + lastName);
                }

            }
            // If errors print out
        } catch (SQLException e) {
            System.out.println("Error getting films: " + e.getMessage());;
        }

    }
}
