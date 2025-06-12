package com.pluralsight.models;

public class Shipper {

    // Private instance variables
    private int shipperId;
    private String companyName;
    private String phone;

    // Constructor
    public Shipper(int shipperId, String companyName, String phone) {
        this.shipperId = shipperId;
        this.companyName = companyName;
        this.phone = phone;
    }

    // Getters
    public int getShipperId() {
        return shipperId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Shipper{" +
                "id=" + shipperId +
                ", name='" + companyName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
