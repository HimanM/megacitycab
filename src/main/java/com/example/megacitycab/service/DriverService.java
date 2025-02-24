package com.example.megacitycab.service;


import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.*;
import com.example.megacitycab.dao.Interfaces.*;
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
    private static final UserDAO userDAO = new UserDAOImpl();
    private static final RideDAO rideDAO = new RideDAOImpl();
    private static final BookingAssignmentDAO bookingAssignmentDAO = new BookingAssignmentDAOImpl();
    private static final DriverDAO driverDAO = new DriverDAOImpl();
    private static final PaymentDAO paymentDAO = new PaymentDAOImpl();
    private static final VehicleDAO vehicleDAO = new VehicleDAOImpl();

    public int getAndAssignAvailableDriver(int driverId) {
        return driverDAO.getAndAssignAvailableDriver(driverId);
    }
    public boolean acceptBooking(int driverId, int bookingId) {
        return driverDAO.acceptBooking(driverId, bookingId);
    }
    public boolean registerDriver(Driver driver) {
        return driverDAO.addDriver(driver);
    }
    public void releaseDriver(int driverId) {
        driverDAO.releaseDriver(driverId);
    }
    public User getDriverById(int driverId) {
        return driverDAO.getDriverById(driverId);
    }
    public Integer getDriverIdByUserId(Integer userId) {
        return driverDAO.getDriverIdByUserId(userId);
    }
    public List<Driver> getUnverifiedDrivers() {
        return driverDAO.getUnverifiedDrivers();
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

    public Assignment getAssignmentByBookingId(Integer bookingId) {
        return bookingAssignmentDAO.getAssignmentByBookingId(bookingId);
    }

    public void updateDriver(int id, int newDriverId) {
        bookingAssignmentDAO.updateDriver(id, newDriverId);
    }

    public void releaseVehicle(int vehicleId) {
        vehicleDAO.releaseVehicle(vehicleId);
    }

    public void cancelBooking(Integer bookingId) {
        bookingDAO.updateBookingStatus(bookingId, "Cancelled");
    }

    public boolean finishRide(Integer bookingId) {
        if (bookingId == null) return false;
        Assignment assignment = getAssignmentByBookingId(bookingId);
        driverDAO.releaseDriver(assignment.getDriverId());
        vehicleDAO.releaseVehicle(assignment.getVehicleId());
        bookingDAO.completeBooking(assignment.getBookingId());
        return true;
    }

    public BookingDetails getAllBookingDetails(Integer bookingId) {
        return bookingDAO.getAllDetails(bookingId);
    }

    public List<Booking> getAssignedBookings(Integer driverId) {
        return bookingDAO.getBookingsByDriverId(driverId);
    }
}
