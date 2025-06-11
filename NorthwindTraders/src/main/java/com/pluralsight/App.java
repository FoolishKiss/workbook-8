package com.pluralsight;

import com.mysql.cj.protocol.Resultset;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        // Make sure user gave us a username and password as args
        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>"
            );
            System.exit(1);
        }

        // Get the username and password from command line arg
        String username = args[0];
        String password = args[1];


        try (Scanner userInput = new Scanner(System.in); BasicDataSource dataSource = new BasicDataSource()) {

            dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
            dataSource.setUsername("root");
            dataSource.setPassword("yearup");

            Connection connection = dataSource.getConnection();

            // Variable for menu loop
            boolean menuOn = true;

            // Loop to keep menu on until user exits
            while (menuOn) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.println("Select an option: ");

                // Read user choice
                int userChoice = userInput.nextInt();

                // Switch to pick method for users choice
                switch (userChoice) {
                    case 1:
                        // If user picks 1, show all products
                        showProducts(connection);
                        break;
                    case 2:
                        // If user picks 2, show all customers
                        showCustomers(connection);
                        break;
                    case 3:
                        // If user picks 3, show categories and ask user to pick one
                        showCategories(connection, userInput);
                        break;
                    case 0:
                        // If user picks 0, exit loop
                        menuOn = false;
                        System.out.println("Goodbye");
                        break;
                    default:
                        // If they type something else, tell them its invalid
                        System.out.println("Invalid option");
                }
            }

        } catch (SQLException e) {
            // Catch any database errors and show message
            System.out.println("Database error: " + e.getMessage());
        }
    }
    
    // Method to show list of all products from database
    private static void showProducts(Connection connection) {

        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        try (
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()
        ) {
            // Header for table
            System.out.printf("%n%-4s %-30s %-8s %-6s%n", "Id", "Name", "Price", "Stock");
            System.out.printf("--------------------------------------------------------%n");

            // Loop through the results and print each product
            while (results.next()) {
                int productId = results.getInt("ProductId");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("Unitprice");
                int unitInStock = results.getInt("UnitsInStock");

                System.out.printf("%-4d %-30s %-8.2f %-6d%n", productId, productName, unitPrice, unitInStock);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
    }

    // Method to show list of all customers , order by country from database
    private static void showCustomers(Connection connection) {

        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers ORDER BY Country";

        try (// Prepare and run the SQL query
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()
        ) {
            // Header for table
            System.out.printf("%n%-25s %-30s %-15s %-15s %-15s%n", "Contact", "Company", "City", "Country", "Phone");
            System.out.printf("-------------------------------------------------------------------------------------------------%n");

            // Loop through the results and print each customer
            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.printf("%n%-25s %-30s %-15s %-15s %-15s%n", contactName, companyName, city, country, phone);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
        }
    }

    // Method to show all categories, then ask user to pick one to display
    private static void showCategories(Connection connection, Scanner userInput) {

        // First query to get list of categories
        String query = "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID";

        try (
             // First show all categories
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()
        ) {
            // Header for table
            System.out.printf("%n%-5s %-30s%n", "ID", "Category");
            System.out.printf("----- -------------------------%n");

            // Loop through the results and print each category
            while (results.next()) {
                int id = results.getInt("CategoryID");
                String name = results.getString("CategoryName");

                System.out.printf("%n%-5s %-30s%n", id, name);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
            return;
        }

        // Ask user which category to view
        System.out.println("Enter a category ID to view its products: ");
        int userCategory = userInput.nextInt();

        // Second show products from chosen category
        String productQuery = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products WHERE CategoryID = ?";

        try (
             // Prepare query and insert users category choice
             PreparedStatement statement = connection.prepareStatement(productQuery)
        ) {
            // Set category ID in the SQL
            statement.setInt(1, userCategory);

            try (ResultSet results = statement.executeQuery()) {

                // Header for table
                System.out.printf("%n%-4s %-30s %-8s %-6s%n", "Id", "Name", "Price", "Stock");
                System.out.printf("----------------------------------------------------------%n");

                // Loop through the results and print each product from chosen category
                while (results.next()) {
                    int productId = results.getInt("ProductId");
                    String productName = results.getString("ProductName");
                    double unitPrice = results.getDouble("Unitprice");
                    int unitInStock = results.getInt("UnitsInStock");

                    System.out.printf("%-4d %-30s %-8.2f %-6d%n", productId, productName, unitPrice, unitInStock);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products for category: " + e.getMessage());
        }
    }
}
