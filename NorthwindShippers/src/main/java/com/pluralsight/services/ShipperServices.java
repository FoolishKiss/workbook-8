package com.pluralsight.services;

import com.pluralsight.dao.ShipperDataManager;
import com.pluralsight.models.Shipper;

import java.util.List;

public class ShipperServices {

    // Private reference to database
    private ShipperDataManager dataManager;

    // Constructor
    public ShipperServices(ShipperDataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Method to add shippers
    public int addShipper(String name, String phone) {

        // Return results from data access
        return dataManager.insertShipper(name, phone);
    }

    // Method to get all shippers
    public List<Shipper> listShipper() {

        // Return the result from data access
        return dataManager.getAllShippers();
    }

}
