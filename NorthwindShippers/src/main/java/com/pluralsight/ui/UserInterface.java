package com.pluralsight.ui;

import com.pluralsight.models.Shipper;
import com.pluralsight.services.ShipperServices;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Scanner userInput;
    private ShipperServices service;

    public UserInterface(Scanner userInput, ShipperServices service) {
        this.userInput = userInput;
        this.service = service;
    }

    // Method to run the user interface
    public void run() {

        // Flag to control app loop
        boolean appOn = true;

        while (appOn) {

            // Calls menu method
            menu();

            // Stores user input as int in variable choice
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> insertShipper();
                case 2 -> listShipper();
                case 3 -> updatePhone();
                case 4 -> deleteShipper();
                case 5 -> appOn = false;
                default -> System.out.println("Invalid choice.");
            }
        }

        // Exit message
        System.out.println("Goodbye");

    }

    // Method to print menu
    private void menu() {
        System.out.println("\n--- Northwind Shippers ---");
        System.out.println("1) Add Shipper");
        System.out.println("2) List Shippers");
        System.out.println("3) Update Phone");
        System.out.println("4) Delete Shipper");
        System.out.println("5) Exit");
    }

    // Method to add a new shipper
    private void insertShipper() {

        // Ask user for shippers name
        System.out.println("Enter shipper name: ");
        // Read and store it in name variable
        String name = userInput.nextLine();
        // Ask user for phone number
        System.out.println("Enter Phone: ");
        // REad and store it in phone variable
        String phone = userInput.nextLine();

        // Tells ShipperServices to add shipper and gets back ID number
        int id = service.addShipper(name, phone);
        // Prints out success message and ID
        System.out.println("New shipper inserted with ID: " + id);
    }

    // Method to show all shippers
    private void listShipper() {

        // Get shippers from ShipperServices and stores in list
        List<Shipper> shippers = service.listShipper();
        // Go through each shipper in list
        for (Shipper shipper : shippers) {
            // Print out shipper info
            System.out.println(shipper);
        }
    }

    // Method to update shipper phone number
    private void updatePhone() {

        // Ask user which shipper they want to update
        int id = getIntInput("Enter shipper ID to update: ");
        // Ask user for the new phone number
        System.out.println("Enter new phone: ");
        // Read the number and store in phone variable
        String phone = userInput.nextLine();

        // Tells the services to update phone
        boolean success = service.updatePhone(id, phone);
        // Check if the update worked
        if(success) {
            // if it worked print out message
            System.out.println("Phone updated.");
        } else {
            // If it doesn't print message
            System.out.println("Update failed");
        }
    }

    // Method to delete shipper
    private void deleteShipper() {

        // Ask user what shipper to delete by ID
        int id = getIntInput("Enter shipper ID to delete: ");

        // Tells the services to delete shipper
        boolean success = service.deleteShipper(id);

        // Checks if the deletion worked
        if (success) {
            // If it worked
            System.out.println("Shipper deleted.");
        } else {
            // If it doesn't
            System.out.println("Delete failed.");
        }
    }

    private int getIntInput(String question) {

        // Show user the question
        System.out.println(question);

        // Loop to keep asking until valid
        while (!userInput.hasNextInt()) {

            // If not a number tell to try again
            System.out.println("Enter valid number");

            // Clear bad input
            userInput.nextLine();
        }

        // Read user input number
        int number = userInput.nextInt();

        // Clear new line
        userInput.nextLine();

        // Return user input number
        return number;
    }

}
