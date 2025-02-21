package com.example.megacitycab.service;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.BookingAssignmentDAO;
import com.example.megacitycab.dao.BookingAssignmentDAOImpl;
import com.example.megacitycab.model.Assignment;
import com.example.megacitycab.service.DriverService;

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
        String sql = "SELECT * FROM booking_assignments WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("id"));
                assignment.setDriverId(rs.getInt("driver_id"));
                assignment.setVehicleId(rs.getInt("vehicle_id"));
                assignment.setBookingId(rs.getInt("booking_id"));
                assignment.setAssignedAt(rs.getTimestamp("assigned_at").toLocalDateTime());
                return assignment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean finishRide(Integer bookingId) {
//        System.out.println("Finish Ride Triggered");
        Assignment assignment = getAssignmentByBookingId(bookingId);
        driverService.releaseDriver(assignment.getDriverId());
        vehicleService.releaseVehicle(assignment.getVehicleId());
        bookingService.completeBooking(assignment.getBookingId());
        return true;
    }
}
