package com.pluralsight;

import com.pluralsight.dao.ShipperDataManager;
import com.pluralsight.models.Shipper;
import com.pluralsight.services.ShipperServices;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

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

        // Database connection
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        ShipperDataManager dataManager = new ShipperDataManager(dataSource);
        ShipperServices service = new ShipperServices(dataManager);

        int id = service.addShipper("Miller Global Shipping", "(214)-567-1234");
        System.out.println("Insert ID: " + id);

        List<Shipper> shippers = dataManager.getAllShippers();
        for (Shipper shipper : shippers)
            System.out.println(shipper);
    }
}
