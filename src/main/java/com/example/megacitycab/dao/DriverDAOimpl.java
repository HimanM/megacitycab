package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOimpl implements DriverDAO {

    @Override
    public List<Driver> getAvailableDrivers() {
        String query = "SELECT * FROM drivers WHERE is_available = TRUE";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Driver driver = new Driver();
                driver.setId(rs.getInt("id"));
                driver.setUserId(rs.getInt("user_id"));
                driver.setVehicleDetails(rs.getString("vehicle_details"));
                driver.setLicenseNumber(rs.getString("license_number"));
                driver.setAvailable(rs.getBoolean("is_available"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return drivers;
    }

    @Override
    public boolean assignDriverToBooking(int bookingId, int driverId) {
        String query = "UPDATE bookings SET driver_id = ?, status = 'ACCEPTED' WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, driverId);
            statement.setInt(2, bookingId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
