package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Vehicle;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {
    @Override
    public boolean addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (vehicle_type, model, manufacturer, year, capacity, license_plate, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, vehicle.getVehicleType());
            statement.setString(2, vehicle.getModel());
            statement.setString(3, vehicle.getManufacturer());
            statement.setInt(4, vehicle.getYear());
            statement.setInt(5, vehicle.getCapacity());
            statement.setString(6, vehicle.getLicensePlate());
            statement.setTimestamp(7, Timestamp.valueOf(vehicle.getCreatedAt()));

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Vehicle getVehicleById(int vehicleId) {
        String query = "SELECT * FROM vehicles WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, vehicleId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                            rs.getInt("id"),
                            rs.getString("vehicle_type"),
                            rs.getString("model"),
                            rs.getString("license_plate"),
                            rs.getString("manufacturer"),
                            rs.getInt("year"),
                            rs.getInt("capacity")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getString("vehicle_type"),
                        rs.getString("model"),
                        rs.getString("license_plate"),
                        rs.getString("manufacturer"),
                        rs.getInt("year"),
                        rs.getInt("capacity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        String query = "UPDATE vehicles SET vehicle_type = ?, model = ?, manufacturer = ?, year = ?, capacity = ?, license_plate = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, vehicle.getVehicleType());
            statement.setString(2, vehicle.getModel());
            statement.setString(3, vehicle.getManufacturer());
            statement.setInt(4, vehicle.getYear());
            statement.setInt(5, vehicle.getCapacity());
            statement.setString(6, vehicle.getLicensePlate());
            statement.setInt(7, vehicle.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteVehicle(int vehicleId) {
        String query = "DELETE FROM vehicles WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, vehicleId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE status = 'Available'";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getString("vehicle_type"),
                        rs.getString("model"),
                        rs.getString("license_plate"),
                        rs.getString("manufacturer"),
                        rs.getInt("year"),
                        rs.getInt("capacity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public void releaseVehicle(int vehicleId) {
        String query = "UPDATE vehicles SET status = 'Available' WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, vehicleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assignVehicle(int vehicleId) {
        String query = "UPDATE vehicles SET status = 'Assigned' WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, vehicleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
