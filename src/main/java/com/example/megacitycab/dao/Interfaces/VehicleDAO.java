package com.example.megacitycab.dao.Interfaces;

import com.example.megacitycab.model.Vehicle;
import java.util.List;

public interface VehicleDAO {
    int addVehicle(Vehicle vehicle);
    Vehicle getVehicleById(int vehicleId);
    List<Vehicle> getAllVehicles();
    boolean updateVehicle(Vehicle vehicle);
    boolean deleteVehicle(int vehicleId);
    List<Vehicle> getAvailableVehicles();
    void releaseVehicle(int vehicleId);
    void assignVehicle(int vehicleId);
    List<Vehicle> getAssignedVehicles();
    boolean updateVehicleStatus(Integer vehicleId, String newStatus);
    List<Vehicle> getMaintenanceVehicles();
}
