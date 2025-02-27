package com.example.megacitycab.service;

import com.example.megacitycab.dao.*;
import com.example.megacitycab.dao.Interfaces.*;
import com.example.megacitycab.exceptions.AdminException;
import com.example.megacitycab.model.DTO.Ride;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.Payment;
import com.example.megacitycab.model.User;
import com.example.megacitycab.model.Vehicle;

import java.util.List;

public class AdminService {
    private final UserDAO userDAO;
    private final RideDAO rideDAO;
    private final DriverDAO driverDAO;
    private final PaymentDAO paymentDAO;
    private final VehicleDAO vehicleDAO;

    public AdminService(UserDAO userDAO, RideDAO rideDAO, DriverDAO driverDAO, PaymentDAO paymentDAO, VehicleDAO vehicleDAO) {
        this.userDAO = userDAO;
        this.rideDAO = rideDAO;
        this.driverDAO = driverDAO;
        this.paymentDAO = paymentDAO;
        this.vehicleDAO = vehicleDAO;
    }


    public boolean updateVehicleStatus(Integer vehicleId, String newStatus) throws AdminException {
        try {
            return vehicleDAO.updateVehicleStatus(vehicleId, newStatus);
        }
        catch (Exception e){
            throw new AdminException("Error while updating vehicle status");
        }
    }

    public void addVehicle(Vehicle vehicle) throws AdminException {
        try {
            vehicleDAO.addVehicle(vehicle);
        } catch (Exception e) {
            throw new AdminException("Vehicle already exists",e);
        }

    }

    public void deleteCustomer(int userId) throws AdminException {
        try {
            Integer driverId = driverDAO.getDriverIdByUserId(userId);
            if( driverId != -1){
                driverDAO.deleteDriver(driverId);
            }
            userDAO.deleteUser(userId); // Delete the user.
        } catch (Exception e) {
            throw new AdminException("Error while deleting customer");
        }
    }

    public List<Payment> getAllPayments() throws AdminException {
        try{
            return paymentDAO.getAllPayments();
        }
        catch (Exception e){
            throw new AdminException("Error while fetching payments");
        }
    }

    public List<User> getAllCustomers() throws AdminException {
        try{
            return userDAO.getAllUsers();
        }
        catch (Exception e){
            throw new AdminException("Error while fetching customers");
        }
    }

    public List<Vehicle> getAssignedVehicles() throws AdminException{
        try {
            return vehicleDAO.getAssignedVehicles();
        }
        catch (Exception e){
            throw new AdminException("Error while fetching assigned vehicles");
        }
    }

    public List<Vehicle> getAvailableVehicles() throws AdminException{
        try{
            return vehicleDAO.getAvailableVehicles();
        }
        catch (Exception e){
            throw new AdminException("Error while fetching available vehicles");
        }
    }

    public List<Vehicle> getMaintenanceVehicles() throws AdminException{
        try{
            return vehicleDAO.getMaintenanceVehicles();
        }
        catch (Exception e){
            throw new AdminException("Error while fetching maintenance vehicles");
        }
    }

    public List<Driver> getAllDrivers() throws AdminException{
        try{
            return driverDAO.getAllDrivers();
        }
        catch (Exception e){
            throw new AdminException("Error while fetching drivers");
        }
    }

    public void verifyDriver(int driverId) throws AdminException{
        try{
            driverDAO.verifyDriver(driverId);
        }
        catch (Exception e){
            throw new AdminException("Error while verifying driver");
        }
    }

    public void removeDriver(int driverId) throws AdminException{
        try{
            driverDAO.deleteDriver(driverId);
        }
        catch (Exception e){
            throw new AdminException("Error while removing driver");
        }
    }

    public Ride getRideByDriverId(int driverId) throws AdminException{
        try{
            return rideDAO.getRideByDriverId(driverId);
        }
        catch (Exception e){
            throw new AdminException("Error while fetching ride");
        }
    }
}
