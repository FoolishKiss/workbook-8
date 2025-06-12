package com.pluralsight.services;

import com.pluralsight.dao.DataManager;
import com.pluralsight.models.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FilmServices {

    private DataManager dataManager;

    public FilmServices(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Method to show all films from actorId matching user input
    public void showFilmsByActorId(int actorId) {

        // Get list of matching films from dao with actorId
        List<Film> films = dataManager.getFilmsByActorId(actorId);

        // If no films print out message
        if (films.isEmpty()) {
            System.out.println("\nNo films found for actor ID: " + actorId);
        } else {
            // If found print out header
            System.out.println("\n----- Movies for Actor ID" + actorId + "------------------");
            System.out.printf("%-5s %-50s %-6s %-6s%n", "ID", "Title", "Year", "Length");
            System.out.println("-----------------------------------------------------------------------");

            // Loop through films and prints each row
            for (Film film : films) {
                System.out.printf("%-5s %-50s %-6s %-6s%n", film.getFilmId(), film.getTitle(), film.getReleaseYear(), film.getLength());
            }

            // After main list, print out info for first 3
            displayFilmDetails(films, 3);

        }

    }
    // Shows the details of the first 3 films
    private void displayFilmDetails(List<Film> films, int maxCount) {
        System.out.println("\n----- Film Details (First " + maxCount + " films)-----\n");
        int count = 0;

        for (Film film : films) {
            if (count >= maxCount)
                break;

            System.out.println("Title: " + film.getTitle());
            System.out.println("Description: " + film.getDescription());
            System.out.println("Release Year: " + film.getReleaseYear());
            System.out.println("Length: " + film.getLength());
            System.out.println("-----------------------------------------------");
            count++;

        }

    }


    // Return list of films by actorId
    public List<Film> getFilmsByActorId(int actorId) {
        return dataManager.getFilmsByActorId(actorId);
    }

    // Return list of films by actors full name
    public List<Film> getFilmsByActorName(String firstName, String lastName) {
        return dataManager.getFilmsByActorName(firstName, lastName);
    }

}
