package com.example.megacitycab.model.DTO;

import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.Vehicle;

public class BookingDetails {
    private Booking booking;
    private Vehicle vehicle;

    // Constructor
    public BookingDetails(Booking booking, Vehicle vehicle) {
        this.booking = booking;
        this.vehicle = vehicle;
    }

    // Getters and Setters
    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
