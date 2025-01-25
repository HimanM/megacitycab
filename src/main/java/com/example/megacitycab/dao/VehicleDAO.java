package com.example.megacitycab.dao;

import com.example.megacitycab.model.Vehicle;

import java.util.List;

public interface VehicleDAO {
    void addVehicle(Vehicle vehicle);
    Vehicle getVehicleById(int id);
    List<Vehicle> getAllVehicles();
    void updateVehicle(Vehicle vehicle);
    void deleteVehicle(int id);
}
