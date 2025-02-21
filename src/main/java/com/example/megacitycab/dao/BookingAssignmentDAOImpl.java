package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.BookingAssignmentDAO;
import com.example.megacitycab.model.Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
