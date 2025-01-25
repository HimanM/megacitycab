package com.example.megacitycab.model;

import java.time.LocalDateTime;

public class Vehicle {
    private int id;
    private String vehicleNumber;
    private String model;
    private String driverName;
    private String driverContact;
    private String status;
    private LocalDateTime lastUpdated;

    public Vehicle() {}

    public Vehicle(int id, String vehicleNumber, String model, String driverName, String driverContact, String status, LocalDateTime lastUpdated) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.model = model;
        this.driverName = driverName;
        this.driverContact = driverContact;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getDriverContact() { return driverContact; }
    public void setDriverContact(String driverContact) { this.driverContact = driverContact; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
