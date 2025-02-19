package com.example.megacitycab.dao;

import com.example.megacitycab.model.Vehicle;
import java.util.List;

public interface VehicleDAO {
    boolean addVehicle(Vehicle vehicle);
    Vehicle getVehicleById(int vehicleId);
    List<Vehicle> getAllVehicles();
    boolean updateVehicle(Vehicle vehicle);
    boolean deleteVehicle(int vehicleId);
    List<Vehicle> getAvailableVehicles();
    void releaseVehicle(int vehicleId);
}
