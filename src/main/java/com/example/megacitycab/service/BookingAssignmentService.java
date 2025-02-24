package com.example.megacitycab.service;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.BookingAssignmentDAO;
import com.example.megacitycab.dao.BookingAssignmentDAOImpl;
import com.example.megacitycab.model.Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingAssignmentService {
    private final DriverService driverService = new DriverService();
    private final VehicleService vehicleService = new VehicleService();
    private final BookingService bookingService = new BookingService();
    private BookingAssignmentDAO bookingAssignmentDAO = new BookingAssignmentDAOImpl();

    public boolean createAssignment(Assignment assignment) {
        return bookingAssignmentDAO.insertBookingAssignment(assignment);
    }

    public Assignment getAssignmentByBookingId(int bookingId) {
        return bookingAssignmentDAO.getAssignmentByBookingId(bookingId);
    }

    public boolean finishRide(Integer bookingId) {
        if (bookingId == null) return false;
        Assignment assignment = getAssignmentByBookingId(bookingId);
        driverService.releaseDriver(assignment.getDriverId());
        vehicleService.releaseVehicle(assignment.getVehicleId());
        bookingService.completeBooking(assignment.getBookingId());
        return true;
    }

    public boolean updateDriver(int id, int newDriverId) {
        return bookingAssignmentDAO .updateDriver(id, newDriverId);
    }
}
