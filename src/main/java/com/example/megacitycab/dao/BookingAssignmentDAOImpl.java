package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.BookingAssignmentDAO;
import com.example.megacitycab.model.Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingAssignmentDAOImpl implements BookingAssignmentDAO {
    @Override
    public boolean insertBookingAssignment(Assignment assignment) {
        String sql = "INSERT INTO booking_assignments (booking_id, driver_id, vehicle_id, assigned_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, assignment.getBookingId());
            stmt.setInt(2, assignment.getDriverId());
            stmt.setInt(3, assignment.getVehicleId());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(assignment.getAssignedAt()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
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

    @Override
    public boolean updateDriver(int id, int newDriverId) {
        String sql = "UPDATE booking_assignments SET driver_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newDriverId);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBookingAssignment(int id) {
        String sql = "DELETE FROM booking_assignments WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
