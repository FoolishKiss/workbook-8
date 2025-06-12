package com.pluralsight.dao;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
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

        try ( // Open database connection
              Connection connection = dataSource.getConnection();
              // Create the sql query
              PreparedStatement statement = connection.prepareStatement(query);
        ) { // Bind ? to variables firstName, and lastName
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            // Execute query
            try (ResultSet results = statement.executeQuery()) {
                // Loops through each row and prints out results
                while (results.next()) {
                    int actorId = results.getInt("actor_id");
                    String firstNameDb = results.getString("first_name");
                    String lastNameDb = results.getString("last_name");

                    // Create actor and add to list
                    Actor actor = new Actor(actorId, firstNameDb, lastNameDb);
                    actors.add(actor);
                }
            }
            // Return list of matching actors
            return actors;
            // Error handling
        } catch (SQLException e) {
            throw new RuntimeException("Error getting actors by name: " + e.getMessage(), e);
        }
    }

    // Gets list of films by actorId
    public List<Film> getFilmsByActorId(int actorId) {

        // List to hold films
        List<Film> films = new ArrayList<>();

        // Prepare the sql statement
        String query = """
                SELECT f.film_id, f.title, f.description, f.release_year, f.length
                FROM actor a
                JOIN film_actor fa ON a.actor_id = fa.actor_id
                JOIN film f ON fa.film_id = f.film_id
                WHERE a.actor_id = ?
                """;

        try ( // Open database connection
              Connection connection = dataSource.getConnection();
              // Create the sql query
              PreparedStatement statement = connection.prepareStatement(query);
        ) { // Bind ? to variables actorId
            statement.setInt(1, actorId);

            // Execute query
            try (ResultSet results = statement.executeQuery()) {

                // Loops through each row and prints out results
                while (results.next()) {
                    int filmId = results.getInt("film_id");
                    String title = results.getString("title");
                    String description = results.getString("description");
                    int releaseYear = results.getInt("release_year");
                    int length = results.getInt("length");

                    // Create film and add to list
                    Film film = new Film(filmId, title, description, releaseYear, length);
                    films.add(film);
                }
            }
            // Return list of matching actors
            return films;

            // Error handling
        } catch (SQLException e) {
            throw new RuntimeException("Error getting films by actor ID: " + e.getMessage(), e);
        }
    }
    // Gets films for an actor based off of full name
    public List<Film> getFilmsByActorName(String firstName, String lastName) {
        List<Film> films = new ArrayList<>();

        String query = """
                SELECT f.film_id, f.title, f.description, f.release_year, f.length
                FROM actor a
                JOIN film_actor fa ON a.actor_id = fa.actor_id
                JOIN film f ON fa.film_id = f.film_id
                WHERE a.first_name = ? AND a.last-name = ?
                """;

        try ( // Open database connection
              Connection connection = dataSource.getConnection();
              // Create the sql query
              PreparedStatement statement = connection.prepareStatement(query);
        ) { // Bind ? to variables firstName, and lastName
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            // Execute query
            try (ResultSet results = statement.executeQuery()) {

                // Loops through each row and prints out results
                while (results.next()) {
                    int filmId = results.getInt("film_id");
                    String title = results.getString("title");
                    String description = results.getString("description");
                    int releaseYear = results.getInt("release_year");
                    int length = results.getInt("length");

                    // Create film and add to list
                    Film film = new Film(filmId, title, description, releaseYear, length);
                    films.add(film);
                }
            }
            // Return list of matching actors
            return films;

            // Error handling
        } catch (SQLException e) {
            throw new RuntimeException("Error getting films by actor name: " + e.getMessage(), e);
        }

    }

}
