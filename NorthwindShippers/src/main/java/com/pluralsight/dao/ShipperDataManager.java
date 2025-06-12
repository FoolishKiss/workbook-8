package com.pluralsight.dao;

import com.pluralsight.models.Shipper;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipperDataManager {

    // Private reference to database
    private BasicDataSource dataSource;

    // Constructor
    public ShipperDataManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Method to insert a new shipper
    public int insertShipper(String name, String phone) {

        // Prepare sql query
        String query = """
                INSERT INTO shippers (CompanyName, Phone)
                Values (?, ?)
                """;

        try ( // Open database connection
              Connection connection = dataSource.getConnection();
              // Create the sql query, and return generated keys
              PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

              // Bind ? variables
              statement.setString(1, name);
              statement.setString(2, phone);

              // Execute the INSERT query and returns number of affected rows
              statement.executeUpdate();

              // Gets the generated keys
              try (ResultSet keys = statement.getGeneratedKeys()) {

                  // Checks if there's a key
                  if (keys.next()) {
                      // Return the first generated key as int
                      return keys.getInt(1);
                  }
              }
          // Catch sql exceptions
        } catch (SQLException e) {
            // Wrap sql exception in runtime exception
            throw new RuntimeException("Error inserting shipper: " + e.getMessage(), e);
        }
        return -1;
    }

    // Method to get all Shippers
    public List<Shipper> getAllShippers() {

        // Create empty list to store shipper
        List<Shipper> shippers = new ArrayList<>();

        // Prepare sql query
        String query = """
                SELECT ShipperID, CompanyName, Phone
                FROM shippers
                """;

        try (// Open database
             Connection connection = dataSource.getConnection();

             // Create sql query
             PreparedStatement statement = connection.prepareStatement(query);

             // Execute query
             ResultSet results = statement.executeQuery()) {

            // Loop through each row in results and add to list
            while (results.next()) {
                shippers.add(new Shipper(
                        results.getInt("ShipperID"),
                        results.getString("CompanyName"),
                        results.getString("Phone")
                        ));
            }
          // Catch sql exceptions
        } catch (SQLException e) {
            // Wrap sql exceptions as runtime exception
            throw new RuntimeException("Error getting shippers: " + e.getMessage(), e);
        }

        // Return list of shippers
        return shippers;
    }
}
