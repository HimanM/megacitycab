package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOimpl implements DriverDAO {


    @Override
    public boolean addDriver(Driver driver) {
        String sql = "INSERT INTO drivers (user_id, license_number, verified, created_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, driver.getUserId());
            stmt.setString(2, driver.getLicenseNumber());
            stmt.setString(3, "No");
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(driver.getCreatedAt()));

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean deleteDriver(int driverId) {
        String query = "DELETE FROM drivers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, driverId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verifyDriver(int driverId) {
        String query = "UPDATE drivers SET verified = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "Yes");
            statement.setInt(2, driverId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //to be fixed
    @Override
    public Driver getDriverById(int driverId) {
        return null;
    }

    @Override
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        return drivers;
    }

    @Override
    public boolean updateDriver(Driver driver) {
        return false;
    }
}
