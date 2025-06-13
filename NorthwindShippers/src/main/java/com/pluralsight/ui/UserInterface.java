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

        // Print welcome message
        System.out.println("Northwind Shippers");

        // Ask user for shipper name
        System.out.println("Enter new shipper name: ");
        // Read and store line in name variable
        String name = userInput.nextLine();

        // Ask user to enter phone number
        System.out.println("Enter phone: ");
        // Read and store line in phone variable
        String phone = userInput.nextLine();

        // Call addShipper method from ShipperServices
        int id = service.addShipper(name, phone);

        // Show confirmation message with shipper ID
        System.out.println("New shipper inserted with ID: " + id);

        // Header
        System.out.println("\nAll shippers:");

        // Get list of shippers from ShipperServices
        List<Shipper> shippers = service.listShipper();

        // Loop through the list of shippers
        for (Shipper s : shippers) {
            // Print shipper
            System.out.println(s);
        }


    }

}
