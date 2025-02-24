package com.example.megacitycab.service;


import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.DriverDAO;
import com.example.megacitycab.dao.DriverDAOImpl;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DriverService {
    private final DriverDAO driverDAO;
    public DriverService() {
        this.driverDAO = new DriverDAOImpl(); // Default implementation
    }
    public int getAndAssignAvailableDriver(int driverId) {
        return driverDAO.getAndAssignAvailableDriver(driverId);
    }
    public boolean acceptBooking(int driverId, int bookingId) {
        return driverDAO.acceptBooking(driverId, bookingId);
    }
    public boolean registerDriver(Driver driver) {
        return driverDAO.addDriver(driver);
    }
    public void releaseDriver(int driverId) {
        driverDAO.releaseDriver(driverId);
    }
    public User getDriverById(int driverId) {
        return driverDAO.getDriverById(driverId);
    }
    public Integer getDriverIdByUserId(Integer userId) {
        return driverDAO.getDriverIdByUserId(userId);
    }
    public List<Driver> getUnverifiedDrivers() {
        return driverDAO.getUnverifiedDrivers();
    }
    public List<Driver> getAllDrivers() {
        return driverDAO.getAllDrivers();
    }
    public void verifyDriver(int driverId) {
        driverDAO.verifyDriver(driverId);
    }
    public void removeDriver(int driverId) {
        driverDAO.deleteDriver(driverId);
    }
}
