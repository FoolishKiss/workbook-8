package com.pluralsight.ui;

import com.pluralsight.services.ActorServices;
import com.pluralsight.services.FilmServices;

import java.util.Scanner;

public class UserInterface {

    // Instance variables
    private Scanner userInput;
    private ActorServices actorServices;
    private FilmServices filmServices;

    // Constructor
    public UserInterface(Scanner userInput, ActorServices actorServices, FilmServices filmServices) {
        this.userInput = userInput;
        this.actorServices = actorServices;
        this.filmServices = filmServices;
    }

    // Method to show welcome message
    public void welcome() {
        System.out.println("=== Sakila Movie Database ===\n");
    }

    // Method to display the main menu options to the user
    public void menu() {
        System.out.println("Please select an option:");
        System.out.println("1. Search for actors and their movies");
        System.out.println("2. Exit");
        System.out.println("Enter your choice (1-2): ");
    }

    // Method to get valid users choice
    public int getValidMenuChoice() {

        // Loops until valid input
        while (true) {

            // Try block to catch parsing errors
            try {
                // Read line
                String input = userInput.nextLine().trim();
                // Convert String to int
                int choice = Integer.parseInt(input);

                // checks if choice is within valid range
                if (choice >= 1 && choice <= 2) {
                    // Return valid choice
                    return choice;
                } else {
                    // Ask for valid range
                    System.out.println("Please enter 1 or 2: ");
                }
                // Catch input if not a number
            } catch (NumberFormatException e) {
                // Print error message
                System.out.println("Please enter a valid number (1 or 2): ");
            }
        }
    }

    // Method to handle the actor search workflow
    public void searchActorsByLastName() {

        // Get a valid last name from user
        String lastName = getValidLastName();

        // Call the actor services to search and show actors matching user input
        actorServices.showActorsByLastName(lastName);

        // Gets actor ID and shows their films
        handleActorTdInput();
    }

    // Private method to validate user input for lastname
    private  String getValidLastName() {

        // Loop until valid input
        while (true) {

            // Ask to enter username
            System.out.println("\nEnter actor's last name: ");

            // Read user input
            String lastName = userInput.nextLine().trim();

            // Check if the input is not empty
            if (!lastName.isEmpty()) {
                // Return valid last name
                return lastName;
            } else {
                // Print out error message
                System.out.println("Please try again.");
            }
        }
    }

    // Private method to handle actor ID input
    private void handleActorTdInput() {

        // Loop until valid input or user goes back
        while (true) {

            // Ask user for actor ID with option to go back
            System.out.println("\nEnter actor's ID to see their movies (or 'back' to go back to main menu): ");

            // Read user input
            String input = userInput.nextLine().trim();

            // Checks if user wants to go back to main menu
            if (input.equalsIgnoreCase("back")) {
                // Exit and return to menu
                return;
            }

            // Try block to parse input as int
            try {
                // Convert string to int
                int actorId = Integer.parseInt(input);

                // Validate that id is positive
                if (actorId > 0) {

                    // Call film services to show movies for actor ID
                    filmServices.showFilmsByActorId(actorId);

                    // Ask if user wants to search again
                    if (searchAgain()) {

                        // If yes call search method
                        searchActorsByLastName();
                    }
                    // Exit loop
                    break;
                } else {
                    // Print out validation error
                    System.out.println("Please enter a positive number.");
                }
                // Catch parsing errors
            } catch (NumberFormatException e) {
                // Print out error message
                System.out.println("Invalid input.");
            }
        }
    }

    // Private method to ask user if they want to search again
    private boolean searchAgain() {

        // Loop until valid
        while (true) {
            // Ask user if they want to search again
            System.out.println("\nWould you like to search for another actor? (y/n): ");

            // Read input and cleans it
            String input = userInput.nextLine().trim().toLowerCase();

            // Checks if user says yes
            if (input.equals("y") || input.equals("yes")) {
                // Search again
                return true;
                // checks if user says no
            } else if (input.equals("n") || input.equals("no")) {
                // Does not search again
                return false;
                // Invalid response. ask user again
            } else {
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
        }
    }

    // Method to show goodbye message
    public void goodbye() {
        System.out.println("\nThank you. Good Bye");
    }

    // Method for divider
    public void divider() {
        // Prints out 50 =
        System.out.println("\n" + "=".repeat(50) + "\n");
    }

    // Method to show error message
    public void errorMessage(String message) {
        System.err.println("An error occurred: " + message);
    }

    // Method to show critical error
    public void criticalErrorMessage(String message) {
        System.err.println("A critical error occurred: " + message);
    }

}
