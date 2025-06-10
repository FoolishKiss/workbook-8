package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>"
            );
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        // Database variables outside the scope
        Connection connection = null;

        // Scanner to get user input
        Scanner userInput = new Scanner(System.in);

        try {

            // 1. open a connection to the database
            // use the database URL to point to the correct database
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password);

            // Variable for menu
            boolean menuOn = true;

            // Loop to keep menu on until user exits
            while (menuOn) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.println("Select an option: ");
                int userChoice = userInput.nextInt();

                // If user picks 1
                if (userChoice == 1) {
                    // define your query
                    String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products ";

                    // create statement
                    // the statement is tied to the open connection
                    PreparedStatement statement = connection.prepareStatement(query);


                    // 2. Execute your query
                    ResultSet results = statement.executeQuery(query);

                    System.out.printf("%n%-4s %-30s %-8s %-6s%n", "Id", "Name", "Price", "Stock");
                    System.out.printf("---- ------------------------------ -------- -------%n");

                    // process the results
                    while (results.next()) {
                        int productId = results.getInt("ProductID");
                        String productName = results.getString("ProductName");
                        double unitPrice = results.getDouble("UnitPrice");
                        int unitsInStock = results.getInt("UnitsInStock");

                        System.out.printf("%-4d %-30s %-8.2f %-6d%n" ,
                                productId, productName, unitPrice, unitsInStock);

                    }
                  // If user picks 2
                } else if (userChoice == 2) {

                    // define your query
                    String customerQuery = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers ORDER BY Country";

                    // create statement
                    // the statement is tied to the open connection
                    PreparedStatement customerStatement = connection.prepareStatement(customerQuery);


                    // 2. Execute your query
                    ResultSet results = customerStatement.executeQuery();

                    System.out.printf("%n%-25s %-30s %-15s %-15s %-15s%n", "Contact", "Company", "City", "Country", "Phone");
                    System.out.printf("----------------------------------------------------------------------------------------------%n");

                    // process the results
                    while (results.next()) {
                        String contactName = results.getString("ContactName");
                        String companyName = results.getString("CompanyName");
                        String city = results.getString("City");
                        String country = results.getString("Country");
                        String phone = results.getString("Phone");

                        System.out.printf("%-25s %-30s %-15s %-15s %-15s%n" ,
                                contactName, companyName, city, country, phone);

                    }
                  // If user picks 0
                } else if (userChoice == 0) {
                    menuOn = false;
                    System.out.println("Goodbye.");
                } else {
                    System.out.println("Invalid option.");
                }
            }

            // 3. Close the connection
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }

            userInput.close();
        }

    }
}
