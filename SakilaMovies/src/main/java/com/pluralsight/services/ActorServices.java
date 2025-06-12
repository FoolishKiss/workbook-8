package com.pluralsight.services;

import com.pluralsight.dao.DataManager;
import com.pluralsight.models.Actor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ActorServices {

    private DataManager dataManager;

    public ActorServices(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Method to get and show actors that match user input for last name
    public void showActorsByLastName(String lastName) {

        // Get list from dao of matching actors
        List<Actor> actors = dataManager.getActorsByLastName(lastName);

        // If no actor found print out message
        if(actors.isEmpty()) {
            System.out.println("\nNo actors found with last name: " + lastName);
        } else {
            // Print out header
            System.out.printf("%-5s %-15s %-15s\n", "ID", "First Name", "Last Name");
            System.out.println("--------------------------------");

            // Loop through all matching actors and print out info
            for (Actor actor : actors) {
                System.out.printf("%-5s %-15s %-15s\n", actor.getActorId(), actor.getFirstName(), actor.getLastName());
            }
        }

    }

    // Return list of actors matching first and last name
    public  List<Actor> getActorsByName(String firstName, String lastName) {
        return dataManager.getActorsByName(firstName, lastName);
    }

}
