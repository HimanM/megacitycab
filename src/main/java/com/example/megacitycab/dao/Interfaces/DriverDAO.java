package com.example.megacitycab.dao.Interfaces;

import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.User;

import java.util.List;

public interface DriverDAO {
    boolean addDriver(Driver driver);
    User getDriverById(int driverId);
    List<Driver> getAllDrivers();
    boolean updateDriver(Driver driver);
    boolean deleteDriver(int driverId);
    boolean verifyDriver(int driverId);
    Integer getDriverIdByUserId(Integer userId);
    List<Driver> getUnverifiedDrivers();
    void releaseDriver(int driverId);
    boolean acceptBooking(int driverId, int bookingId);
    int getAndAssignAvailableDriver(int driverId);
}
