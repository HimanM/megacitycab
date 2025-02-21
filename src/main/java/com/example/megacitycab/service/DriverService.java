package com.example.megacitycab.service;


import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.DriverDAO;
import com.example.megacitycab.dao.DriverDAOimpl;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverService {
    private final DriverDAO driverDAO;

    public DriverService() {
        this.driverDAO = new DriverDAOimpl(); // Default implementation
    }

//    public DriverService(DriverDAO driverDAO) {
//        this.driverDAO = driverDAO; // Allow dependency injection for testing or customization
public int getAndAssignAvailableDriver() {
    String SELECT_AVAILABLE_DRIVER = "SELECT TOP 1 id FROM drivers WHERE status = 'Available' AND verified = 'Yes' ORDER BY id ASC";
    String UPDATE_DRIVER_STATUS = "UPDATE drivers SET status = 'On Trip' WHERE id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement selectStmt = connection.prepareStatement(SELECT_AVAILABLE_DRIVER);
         PreparedStatement updateStmt = connection.prepareStatement(UPDATE_DRIVER_STATUS)) {

        ResultSet resultSet = selectStmt.executeQuery();

        if (resultSet.next()) {
            int driverId = resultSet.getInt("id");

            // Mark the driver as busy to prevent duplicate assignments
            updateStmt.setInt(1, driverId);
            updateStmt.executeUpdate();

            return driverId;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return -1; // No available driver found
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

    public boolean registerDriver(Driver driver) {
        return driverDAO.addDriver(driver);
    }

    public void releaseDriver(int driverId) {
        String UPDATE_DRIVER_STATUS = "UPDATE drivers SET status = 'Available' WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DRIVER_STATUS)) {

            statement.setInt(1, driverId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getDriverById(int driverId) {
        return driverDAO.getDriverById(driverId);
    }

    public Integer getDriverIdByUserId(Integer userId) {
        return driverDAO.getDriverIdByUserId(userId);
    }
}
