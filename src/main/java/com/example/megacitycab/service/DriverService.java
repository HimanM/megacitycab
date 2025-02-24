package com.example.megacitycab.service;


import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.*;
import com.example.megacitycab.dao.Interfaces.*;
import com.example.megacitycab.exceptions.DriverException;
import com.example.megacitycab.model.Assignment;
import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.DTO.BookingDetails;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DriverService {

    private final BookingDAO bookingDAO = new BookingDAOImpl();;
    private static final BookingAssignmentDAO bookingAssignmentDAO = new BookingAssignmentDAOImpl();
    private static final DriverDAO driverDAO = new DriverDAOImpl();
    private static final VehicleDAO vehicleDAO = new VehicleDAOImpl();


    public int getAndAssignAvailableDriver(int driverId) throws DriverException {
        try {
            return driverDAO.getAndAssignAvailableDriver(driverId);
        }
        catch (Exception e) {
            throw new DriverException("Error while fetching available drivers", e);
        }
    }

    public boolean acceptBooking(int driverId, int bookingId) throws DriverException  {
        try {
            return driverDAO.acceptBooking(driverId, bookingId);
        }
        catch (Exception e) {
            throw new DriverException("Error while accepting booking", e);
        }
    }

    public boolean registerDriver(Driver driver) throws DriverException {
        try {
            return driverDAO.addDriver(driver);
        }
        catch (Exception e) {
            throw new DriverException("Error while registering driver", e);
        }
    }

    public void releaseDriver(int driverId) throws DriverException {
        try {
            driverDAO.releaseDriver(driverId);
        }
        catch (Exception e) {
            throw new DriverException("Error while releasing driver", e);
        }
    }

    public User getDriverById(int driverId) throws DriverException {
        try {
            return driverDAO.getDriverById(driverId);
        }
        catch (Exception e) {
            throw new DriverException("Error while fetching driver details", e);
        }
    }

    public Integer getDriverIdByUserId(Integer userId) throws DriverException {
        try {
            return driverDAO.getDriverIdByUserId(userId);
        }
        catch (Exception e) {
            throw new DriverException("Error while fetching driver details", e);
        }
    }

    public List<Driver> getUnverifiedDrivers() throws DriverException {
        try {
            return driverDAO.getUnverifiedDrivers();
        }
        catch (Exception e) {
            throw new DriverException("Error while fetching unverified drivers", e);
        }
    }

    public List<Driver> getAllDrivers() throws DriverException {
        try {
            return driverDAO.getAllDrivers();
        }
        catch (Exception e) {
            throw new DriverException("Error while fetching drivers", e);
        }
    }

    public void verifyDriver(int driverId) throws DriverException {
        try {
            driverDAO.verifyDriver(driverId);
        }
        catch (Exception e) {
            throw new DriverException("Error while verifying driver", e);
        }
    }

    public void removeDriver(int driverId) throws DriverException {
        try {
            driverDAO.deleteDriver(driverId);
        }
        catch (Exception e) {
            throw new DriverException("Error while removing driver", e);
        }
    }

    public Assignment getAssignmentByBookingId(Integer bookingId) throws DriverException {
        try {
            return bookingAssignmentDAO.getAssignmentByBookingId(bookingId);
        }
        catch (Exception e) {
            throw new DriverException("Error while fetching assignment details", e);
        }
    }

    public void updateDriver(int id, int newDriverId) throws DriverException {
        try {
            bookingAssignmentDAO.updateDriver(id, newDriverId);
        }
        catch (Exception e) {
            throw new DriverException("Error while updating driver", e);
        }
    }

    public void releaseVehicle(int vehicleId) throws DriverException {
        try {
            vehicleDAO.releaseVehicle(vehicleId);
        }
        catch (Exception e) {
            throw new DriverException("Error while releasing vehicle", e);
        }
    }

    public void cancelBooking(Integer bookingId) throws DriverException {
        try {
            bookingDAO.updateBookingStatus(bookingId, "Cancelled");
        }
        catch (Exception e) {
            throw new DriverException("Error while canceling booking", e);
        }
    }

    public boolean finishRide(Integer bookingId)  throws DriverException{
        try {
            if (bookingId == null) return false;
            Assignment assignment = getAssignmentByBookingId(bookingId);
            driverDAO.releaseDriver(assignment.getDriverId());
            vehicleDAO.releaseVehicle(assignment.getVehicleId());
            bookingDAO.completeBooking(assignment.getBookingId());
            return true;
        }
        catch (Exception e) {
            throw new DriverException("Error while finishing ride", e);
        }

    }

    public BookingDetails getAllBookingDetails(Integer bookingId) throws DriverException {
        try {
            return bookingDAO.getAllDetails(bookingId);
        }
        catch (Exception e) {
            throw new DriverException("Error while fetching booking details", e);
        }
    }

    public List<Booking> getAssignedBookings(Integer driverId) throws DriverException {
        try {
            return bookingDAO.getBookingsByDriverId(driverId);
        }
        catch (Exception e) {
            throw new DriverException("Error while fetching assigned bookings", e);
        }
    }
}
