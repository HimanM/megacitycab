package com.example.megacitycab.service;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.UserDAO;
import com.example.megacitycab.dao.UserDAOImpl;
import com.example.megacitycab.exceptions.AuthException;
import com.example.megacitycab.model.User;
import org.junit.jupiter.api.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceTest {
    private static Connection connection;
    private static UserDAOImpl userDAO;
    private static AuthService authService = new AuthService(new UserDAOImpl());



    @BeforeAll
    static void setup() {
        userDAO = new UserDAOImpl();
        User testUser = new User(0, "testuser", "password123", "Test User",
            "test@example.com", "123 Test Street", "123456789V", "0712345678",
            "customer", LocalDateTime.now());

    int testUserId = userDAO.addUser(testUser);
    }

    @Test
    @Order(1)
    void testAuthenticate_ValidUser() {
        User user = authService.authenticate("testuser", "password123");
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
    }

    @Test
    @Order(2)
    void testAuthenticate_InvalidPassword() {
        User user = authService.authenticate("testuser", "wrongPassword");
        assertNull(user);
    }

    @Test
    @Order(3)
    void testAuthenticate_NonExistentUser() {
        User user = authService.authenticate("unknownUser", "password123");
        assertNull(user);
    }

    @Test
    @Order(4)
    void testVerifyPassword_CorrectPassword() throws SQLException, NoSuchAlgorithmException, AuthException {
        boolean isValid = AuthService.verifyPassword("testuser", "password123");
        assertTrue(isValid);
    }

    @Test
    @Order(5)
    void testVerifyPassword_IncorrectPassword() throws SQLException, NoSuchAlgorithmException, AuthException {
        boolean isValid = AuthService.verifyPassword("testuser", "wrongPassword");
        assertFalse(isValid);
    }

    @AfterAll
    static void cleanupDatabase() throws SQLException {
        connection = DatabaseConnection.getConnection();
        String deleteTestUser = "DELETE FROM users WHERE username = 'testuser'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteTestUser)) {
            preparedStatement.executeUpdate();
        }
        connection.close();
    }
}
