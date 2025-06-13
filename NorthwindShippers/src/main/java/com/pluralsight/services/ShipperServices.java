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

    // Method to update phone
    public boolean updatePhone(int shipperId, String newPhone) {

        // Return the results from data access
        return dataManager.updatePhone(shipperId, newPhone);
    }

    // Method to delete shipper
    public boolean deleteShipper(int shipperId) {

        // Return the results from data access
        return dataManager.deleteShipper(shipperId);
    }


}
