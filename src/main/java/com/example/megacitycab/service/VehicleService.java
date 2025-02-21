package com.example.megacitycab.service;

import com.example.megacitycab.dao.Interfaces.VehicleDAO;
import com.example.megacitycab.dao.VehicleDAOImpl;
import com.example.megacitycab.exceptions.VehicleException;
import com.example.megacitycab.model.Vehicle;

import java.util.List;

public class VehicleService {
    private final VehicleDAO vehicleDAO;

    public VehicleService() {
        this.vehicleDAO = new VehicleDAOImpl(); // Default to VehicleDAOImpl
    }

    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO; // Allow dependency injection
    }

    // Add a new vehicle
    public void addVehicle(Vehicle vehicle) throws VehicleException {
        try {
            if (vehicle == null) {
                throw new VehicleException("Vehicle data cannot be null");
            }
            vehicleDAO.addVehicle(vehicle);
        } catch (Exception e) {
            throw new VehicleException("Error while adding vehicle", e);
        }
    }

    // Update an existing vehicle
    public void updateVehicle(Vehicle vehicle) throws VehicleException {
        try {
            if (vehicle == null) {
                throw new VehicleException("Vehicle data cannot be null");
            }
            vehicleDAO.updateVehicle(vehicle);
        } catch (Exception e) {
            throw new VehicleException("Error while updating vehicle", e);
        }
    }

    // Delete a vehicle by ID
    public boolean deleteVehicle(int vehicleId) throws VehicleException {
        try {
            return vehicleDAO.deleteVehicle(vehicleId);
        } catch (Exception e) {
            throw new VehicleException("Error while deleting vehicle", e);
        }
    }

    // Get vehicle by ID
    public Vehicle getVehicleById(int vehicleId) throws VehicleException {
        try {
            return vehicleDAO.getVehicleById(vehicleId);
        } catch (Exception e) {
            throw new VehicleException("Error while fetching vehicle details", e);
        }
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() throws VehicleException {
        try {
            return vehicleDAO.getAllVehicles();
        } catch (Exception e) {
            throw new VehicleException("Error while fetching vehicle list", e);
        }
    }

    public List<Vehicle> getAvailableVehicles() throws VehicleException {
        try {
            return vehicleDAO.getAvailableVehicles();
        } catch (Exception e) {
            throw new VehicleException("Error while fetching vehicle list", e);
        }
    }

    public void releaseVehicle(int vehicleId) {
        vehicleDAO.releaseVehicle(vehicleId);
    }

    public void assignVehicle(int vehicleId){
        vehicleDAO.assignVehicle(vehicleId);
    }

    public List<Vehicle> getAssignedVehicles() {
        return vehicleDAO.getAssignedVehicles();
    }

    public boolean updateVehicleStatus(Integer vehicleId, String newStatus) {
        return vehicleDAO.updateVehicleStatus(vehicleId, newStatus);
    }

    public List<Vehicle> getMaintenanceVehicles() {
        return vehicleDAO.getMaintenanceVehicles();
    }
}
