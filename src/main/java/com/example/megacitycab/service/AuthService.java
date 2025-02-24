package com.example.megacitycab.service;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.UserDAO;
import com.example.megacitycab.dao.UserDAOImpl;
import com.example.megacitycab.exceptions.AuthException;
import com.example.megacitycab.exceptions.CustomerException;
import com.example.megacitycab.model.User;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.megacitycab.util.HashPassword.hashPassword;

public class AuthService {
    private static final UserDAO userDAO = new UserDAOImpl();

    public static User authenticate(String username, String password) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && verifyPassword(username, password)) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new CustomerException("Authentication failed: " + e.getMessage(), e);
        }
    }

    public static boolean verifyPassword(String username, String inputPassword) throws SQLException, NoSuchAlgorithmException, AuthException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password"); // Get the hashed password as a String

                // Hash the input password using the same method as when storing passwords
                String newHash = hashPassword(inputPassword);
                return newHash.equals(storedHash);
            }
        }
        return false;
    }
}
