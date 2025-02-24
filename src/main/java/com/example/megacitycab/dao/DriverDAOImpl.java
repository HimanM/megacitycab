package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.DriverDAO;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.User;
import com.example.megacitycab.service.CustomerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl implements DriverDAO {

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
        String query = "UPDATE drivers SET verified = 'NO', status = 'Inactive' WHERE id = ?";
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

    @Override
    public Integer getDriverIdByUserId(Integer userId) {
        String query = "SELECT id FROM drivers WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Driver> getUnverifiedDrivers() {
        String query = "SELECT * FROM drivers WHERE verified = No";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Driver driver = mapDriver(resultSet);
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    @Override
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

    @Override
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


//    public int getAndAssignAvailableDriver() {
//        String SELECT_AVAILABLE_DRIVER = "SELECT TOP 1 id FROM drivers WHERE status = 'Available' AND verified = 'Yes' AND ORDER BY id ASC";
//        String UPDATE_DRIVER_STATUS = "UPDATE drivers SET status = 'On Trip' WHERE id = ?";
//
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement selectStmt = connection.prepareStatement(SELECT_AVAILABLE_DRIVER);
//             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_DRIVER_STATUS)) {
//
//            ResultSet resultSet = selectStmt.executeQuery();
//
//            if (resultSet.next()) {
//                int driverId = resultSet.getInt("id");
//
//                // Mark the driver as busy to prevent duplicate assignments
//                updateStmt.setInt(1, driverId);
//                updateStmt.executeUpdate();
//
//                return driverId;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return -1; // No available driver found
//    }
    @Override
    public int getAndAssignAvailableDriver(int excludedDriverId) {
        String SELECT_AVAILABLE_DRIVER = "SELECT TOP 1 id FROM drivers WHERE status = 'Available' AND verified = 'Yes' AND id <> ? ORDER BY id ASC";
        String UPDATE_DRIVER_STATUS = "UPDATE drivers SET status = 'On Trip' WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_AVAILABLE_DRIVER);
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_DRIVER_STATUS)) {

            // Set the excluded driver ID in the SELECT query
            selectStmt.setInt(1, excludedDriverId);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int driverId = resultSet.getInt("id");

                // Mark the driver as busy
                updateStmt.setInt(1, driverId);
                updateStmt.executeUpdate();

                return driverId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // No available driver found
    }


    @Override
    public User getDriverById(int driverId) {
        final CustomerService userService = new CustomerService();
        String query = "SELECT user_id FROM drivers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                return userService.getCustomerById(userId);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Driver> getAllDrivers() {
        String query = "SELECT * FROM drivers";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Driver driver = mapDriver(resultSet);

                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    @Override
    public boolean updateDriver(Driver driver) {
        return false;
    }


    private Driver mapDriver(ResultSet resultSet) throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl();

        Driver driver = new Driver();
        User user = new User();
        user = userDAO.getUserById(resultSet.getInt("user_id"));
        driver.setName(user.getName());
        driver.setPhoneNumber(user.getPhone());
        driver.setId(resultSet.getInt("id"));
        driver.setUserId(resultSet.getInt("user_id"));
        driver.setLicenseNumber(resultSet.getString("license_number"));
        driver.setVerified(resultSet.getString("verified"));
        driver.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        driver.setStatus(resultSet.getString("status"));
        return driver;
    }
}

