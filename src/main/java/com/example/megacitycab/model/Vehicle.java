package com.example.megacitycab.model;

import java.time.LocalDateTime;

public class Vehicle {
    private int id;
    private String vehicleType;
    private String model;
    private String manufacturer;
    private int year;
    private int capacity;
    private String licensePlate;
    private  LocalDateTime createdAt;
    private String status;

    public Vehicle() {}

    public Vehicle(int id, String vehicleType, String model, String licensePlate, String manufacturer, int year, int capacity) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.model = model;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.year = year;
        this.capacity = capacity;
        this.createdAt = LocalDateTime.now();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
