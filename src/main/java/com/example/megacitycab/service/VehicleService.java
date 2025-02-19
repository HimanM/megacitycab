package com.example.megacitycab.service;

import com.example.megacitycab.dao.VehicleDAO;
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

//    // Get vehicles by type
//    public List<Vehicle> getVehiclesByDriver(String driver) throws VehicleException {
//        try {
//            return vehicleDAO.getVehiclesByDriver(driver);
//        } catch (Exception e) {
//            throw new VehicleException("Error while fetching vehicles by type", e);
//        }
//    }
//
//    // Get vehicles by Status
//    public List<Vehicle> getVehiclesByStatus(String status) throws VehicleException {
//        try {
//            return vehicleDAO.getVehiclesByStatus(status);
//        } catch (Exception e) {
//            throw new VehicleException("Error while fetching vehicles by status", e);
//        }
//    }
}
