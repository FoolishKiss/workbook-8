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

    private void menu() {
        System.out.println("\n--- Northwind Shippers ---");
        System.out.println("1) Add Shipper");
        System.out.println("2) List Shippers");
        System.out.println("3) Update Phone");
        System.out.println("4) Delete Shipper");
        System.out.println("5) Exit");
    }

}
