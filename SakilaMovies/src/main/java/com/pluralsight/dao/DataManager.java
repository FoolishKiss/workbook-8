package com.pluralsight.dao;

import com.pluralsight.models.Actor;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    // Connection data object
    private BasicDataSource dataSource;


    public DataManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Get list of actors matching user input for last name
    public List<Actor> getActorsByLastName(String lastName) {

        // List to hold actors
        List<Actor> actors = new ArrayList<>();

        // Prepare the sql statement
        String query = """
                SELECT actor_id, first_name, last_name
                FROM actor
                WHERE last_name = ?
                """;

        try (   // Open database connection
                Connection connection = dataSource.getConnection();
                // Create the sql query
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            // Bind ? in query with lastName variable
            statement.setString(1, lastName);

            // Execute query
            try (ResultSet results = statement.executeQuery()) {

                // Loops through each row and prints out results
                while (results.next()) {
                    int actorId = results.getInt("actor_id");
                    String firstName = results.getString("first_name");
                    String lastNameDb = results.getString("last_name");

                    // Create actor and add to list
                    Actor actor = new Actor(actorId, firstName, lastNameDb);
                    actors.add(actor);
                }

            }
            // Return list of matching actors
            return actors;

            // Error handling
        } catch (SQLException e) {
            throw new RuntimeException("Error getting actors by last name: " + e.getMessage(), e);
        }

    }

    // Gets actors matching both first and last name
    public List<Actor> getActorsByName(String firstName, String lastName) {
        List<Actor> actors = new ArrayList<>();

        // Prepare the sql statement
        String query = """
                SELECT actor_id, first_name, last_name
                FROM actor
                WHERE firstName = ? AND last_name = ?
                """;

        try (   // Open database connection
                Connection connection = dataSource.getConnection();
                // Create the sql query
                PreparedStatement statement = connection.prepareStatement(query);
        ) { // Bind ? to variables firstName, and lastName
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    int actorId = results.getInt("actor_id");
                    String firstNameDb = results.getString("first_name");
                    String lastNameDb = results.getString("last_name");

                    Actor actor = new Actor(actorId, firstNameDb, lastNameDb);
                    actors.add(actor);
                }
            }
            return actors;

        } catch (SQLException e) {
            throw new RuntimeException("Error getting actors by name: " + e.getMessage(), e);
        }
    }

}
