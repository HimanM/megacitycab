package com.example.megacitycab.service;


import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.DriverDAO;
import com.example.megacitycab.dao.DriverDAOimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DriverService {
    private final DriverDAO driverDAO;

    public DriverService() {
        this.driverDAO = new DriverDAOimpl(); // Default implementation
    }

    public DriverService(DriverDAO driverDAO) {
        this.driverDAO = driverDAO; // Allow dependency injection for testing or customization
    }

    public boolean acceptBooking(int driverId, int bookingId) {
        String ACCEPT_BOOKING_QUERY = "UPDATE bookings SET driver_id = ?, status = 'ACCEPTED' WHERE id = ? AND status = 'PENDING'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ACCEPT_BOOKING_QUERY)) {

            statement.setInt(1, driverId);
            statement.setInt(2, bookingId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
