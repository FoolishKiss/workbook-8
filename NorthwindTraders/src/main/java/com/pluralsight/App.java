package com.pluralsight;

import java.sql.*;

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

        try {

            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection;
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password);

            // define your query
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products ";

            // create statement
            // the statement is tied to the open connection
            PreparedStatement statement = connection.prepareStatement(query);


            // 2. Execute your query
            ResultSet results = statement.executeQuery(query);

            System.out.printf("%-4s %-30s %-8s %-6s%n", "Id", "Name", "Price", "Stock");
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
            // 3. Close the connection
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
