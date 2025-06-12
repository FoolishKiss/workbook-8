package com.pluralsight.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorServices {

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

}
