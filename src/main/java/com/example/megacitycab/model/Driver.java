package com.example.megacitycab.model;

import java.time.LocalDateTime;

public class Driver extends User {
    private int id;
    private String name;
    private String phoneNumber;
    private String licenseNumber;
    private String verified;
    private LocalDateTime createdAt;
    private int userId;
    private String status;

    public Driver() {}

    public Driver(int id, String name, String phoneNumber, String licenseNumber, String verified, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.verified = verified;
        this.createdAt = createdAt;
        this.userId = 0;
        this.status = "Available"; // Default value for userId
    }

    // Getters and Setters

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String isVerified() { return verified; }
    public void setVerified(String verified) { this.verified = verified; }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
