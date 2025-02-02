package com.example.megacitycab.dao;

import com.example.megacitycab.model.Driver;

import java.util.List;

public interface DriverDAO {
    List<Driver> getAvailableDrivers();
    boolean assignDriverToBooking(int bookingId, int driverId);

}
