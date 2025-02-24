package com.example.megacitycab.service;

import com.example.megacitycab.dao.*;
import com.example.megacitycab.dao.Interfaces.*;
import com.example.megacitycab.model.DTO.Ride;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.Payment;
import com.example.megacitycab.model.User;
import com.example.megacitycab.model.Vehicle;

import java.util.List;

public class AdminService {
    private static final UserDAO userDAO = new UserDAOImpl();
    private static final RideDAO rideDAO = new RideDAOImpl();
    private static final DriverDAO driverDAO = new DriverDAOImpl();
    private static final PaymentDAO paymentDAO = new PaymentDAOImpl();
    private static final VehicleDAO vehicleDAO = new VehicleDAOImpl();


    public boolean updateVehicleStatus(Integer vehicleId, String newStatus) {
        return vehicleDAO.updateVehicleStatus(vehicleId, newStatus);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleDAO.addVehicle(vehicle);
    }

    public void deleteCustomer(int userId) {
        userDAO.deleteUser(userId);
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }

    public List<User> getAllCustomers() {
        return userDAO.getAllUsers();
    }

    public List<Vehicle> getAssignedVehicles() {
        return vehicleDAO.getAssignedVehicles();
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleDAO.getAvailableVehicles();
    }

    public List<Vehicle> getMaintenanceVehicles() {
        return vehicleDAO.getMaintenanceVehicles();
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

    public Ride getRideByDriverId(int driverId) {
        return rideDAO.getRideByDriverId(driverId);
    }
}
