package com.example.megacitycab.model;

import java.time.LocalDateTime;

public class Assignment {
    private int id;
    private int driverId;
    private int vehicleId;
    private int bookingId;
    private LocalDateTime assignedAt;


    public Assignment() {
    }

    public Assignment(int id, int driverId, int vehicleId, int bookingId) {
        this.id = id;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.bookingId = bookingId;
        this.assignedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }


    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}

