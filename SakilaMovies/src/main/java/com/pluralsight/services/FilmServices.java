package com.pluralsight.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmServices {


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
