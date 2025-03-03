package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.RideDAO;
import com.example.megacitycab.DTO.Ride;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RideDAOImpl implements RideDAO {
    @Override
    public Ride getRideByDriverId(int driverId) {
        Ride ride = null;
        String sql = """
        SELECT 
            ba.id AS assignment_id, ba.driver_id, ba.vehicle_id, ba.assigned_at, b.id AS booking_id, b.order_number,
            b.customer_id, b.destination_details, b.booking_date, b.status, b.total_amount, b.pickup_location
        FROM booking_assignments ba
        JOIN bookings b ON ba.booking_id = b.id
        WHERE ba.driver_id = ?
        AND b.status = 'Approved'
        ORDER BY ba.assigned_at DESC
        OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY;
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ride = new Ride();
                ride.setAssignmentId(rs.getInt("assignment_id"));
                ride.setDriverId(rs.getInt("driver_id"));
                ride.setVehicleId(rs.getInt("vehicle_id"));
                ride.setAssignedAt(rs.getTimestamp("assigned_at"));
                ride.setBookingId(rs.getInt("booking_id"));
                ride.setOrderNumber(rs.getString("order_number"));
                ride.setCustomerId(rs.getInt("customer_id"));
                ride.setDestinationDetails(rs.getString("destination_details"));
                ride.setBookingDate(rs.getDate("booking_date"));
                ride.setStatus(rs.getString("status"));
                ride.setTotalAmount(rs.getDouble("total_amount"));
                ride.setPickupLocation(rs.getString("pickup_location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ride;
    }
}
