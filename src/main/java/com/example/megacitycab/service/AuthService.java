package com.example.megacitycab.service;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.UserDAO;
import com.example.megacitycab.dao.UserDAOImpl;
import com.example.megacitycab.exceptions.UserException;
import com.example.megacitycab.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static com.example.megacitycab.util.HashPassword.hashPassword;

public class AuthService {
    private static final UserDAO userDAO = new UserDAOImpl();

    /**
     * Authenticates a user by username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The authenticated User object.
     * @throws UserException If authentication fails.
     */
    public static User authenticate(String username, String password) {
        try {
            User user = userDAO.getUserByUsername(username);
            // Verify password (assuming hashed passwords)
            if (user != null && verifyPassword(username, password)) {
                return user;
            } else {
                return null;
                //throw new UserException("Invalid username or password.");
            }
        } catch (Exception e) {
            throw new UserException("Authentication failed: " + e.getMessage(), e);
        }
    }

    /**
     * Verifies a raw password against the hashed password stored in the database.
     *
     * @param rawPassword The raw password input.
     * @param hashedPassword The hashed password stored in the database.
     * @return True if the passwords match, false otherwise.
     */
    public static boolean verifyPassword(String username, String inputPassword) throws SQLException, NoSuchAlgorithmException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password"); // Get the hashed password as a String

                // Hash the input password using the same method as when storing passwords
                String newHash = hashPassword(inputPassword);
//                System.out.println("Stored Hash: " + storedHash);
//                System.out.println("New Hash: " + newHash);
                return newHash.equals(storedHash);
            }
        }
        return false;
    }
}
