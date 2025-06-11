package com.pluralsight;

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

            dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            try (Connection connection = dataSource.getConnection()) {

                System.out.println("Enter actor's last name: ");
                String lastName = userInput.nextLine();

                searchActorsByLastName(connection, lastName);

                System.out.println("\nEnter actor's first name: ");
                String firstName = userInput.nextLine();

                System.out.println("Enter actor's last name again: ");
                lastName = userInput.nextLine();

                showFilmsByActor(connection, firstName, lastName);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void searchActorsByLastName(Connection connection, String lastName) {

    }

    private static void showFilmsByActor(Connection connection, String firstName, String lastName) {

    }
}
