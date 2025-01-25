package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    private Connection connection;

    public AdminDAOImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Admin getAdminByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ? AND role = 'ADMIN'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapRowToAdmin(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = 'ADMIN'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                admins.add(mapRowToAdmin(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public boolean createAdmin(Admin admin) {
        String query = "INSERT INTO users (username, password, email, name, address, nic, phone, role) VALUES (?, HASHBYTES('SHA2_256', ?), ?, ?, ?, ?, 'ADMIN')";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setString(3, admin.getName());
            preparedStatement.setString(4, admin.getAddress());
            preparedStatement.setString(5, admin.getNIC());
            preparedStatement.setString(6, admin.getContactNumber());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE users SET name = ?, phone = ?, password = HASHBYTES('SHA2_256', ?) WHERE username = ? AND role = 'ADMIN'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getContactNumber());
            preparedStatement.setString(3, admin.getPassword());
            preparedStatement.setString(4, admin.getUsername());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAdmin(int id) {
        String query = "DELETE FROM users WHERE id = ? AND role = 'ADMIN'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private Admin mapRowToAdmin(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();
        admin.setId(resultSet.getInt("id"));
        admin.setUsername(resultSet.getString("username"));
        admin.setName(resultSet.getString("name"));
        admin.setContactNumber(resultSet.getString("phone"));
        return admin;
    }
}
