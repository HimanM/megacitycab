package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Vehicle;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {
    @Override
    public void addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (vehicle_number, model, driver_name, driver_contact, status, last_updated) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vehicle.getVehicleNumber());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getDriverName());
            preparedStatement.setString(4, vehicle.getDriverContact());
            preparedStatement.setString(5, vehicle.getStatus());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(vehicle.getLastUpdated()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vehicle getVehicleById(int id) {
        String query = "SELECT * FROM vehicles WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapRowToVehicle(resultSet);
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
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        String query = "UPDATE vehicles SET vehicle_number = ?, model = ?, driver_name = ?, driver_contact = ?, status = ?, last_updated = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vehicle.getVehicleNumber());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getDriverName());
            preparedStatement.setString(4, vehicle.getDriverContact());
            preparedStatement.setString(5, vehicle.getStatus());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(vehicle.getLastUpdated()));
            preparedStatement.setInt(7, vehicle.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteVehicle(int id) {
        String query = "DELETE FROM vehicles WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Vehicle> getVehiclesByDriver(String driver) {

        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE driver_name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, driver);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                vehicles.add(mapRowToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private Vehicle mapRowToVehicle(ResultSet resultSet) throws SQLException {
        return new Vehicle(
                resultSet.getInt("id"),
                resultSet.getString("vehicle_number"),
                resultSet.getString("model"),
                resultSet.getString("driver_name"),
                resultSet.getString("driver_contact"),
                resultSet.getString("status"),
                resultSet.getTimestamp("last_updated").toLocalDateTime()
        );
    }
}
