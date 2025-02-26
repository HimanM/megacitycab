package com.example.megacitycab.dao.Interfaces;

import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.User;

import java.util.List;

public interface DriverDAO {
    boolean addDriver(Driver driver);
    Integer getDriverUserIdById(int driverId);
    Driver getDriverById(int driverId);
    List<Driver> getAllDrivers();
    boolean updateDriver(Driver driver);
    boolean deleteDriver(int driverId);
    boolean verifyDriver(int driverId);
    Integer getDriverIdByUserId(Integer userId);
    List<Driver> getUnverifiedDrivers();
    void releaseDriver(int driverId);
    int getAndAssignAvailableDriver(int driverId);
}
