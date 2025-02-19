package com.example.megacitycab.dao;

import com.example.megacitycab.model.Driver;
import java.util.List;

public interface DriverDAO {
    boolean addDriver(Driver driver);
    Driver getDriverById(int driverId);
    List<Driver> getAllDrivers();
    boolean updateDriver(Driver driver);
    boolean deleteDriver(int driverId);
    boolean verifyDriver(int driverId);
}
