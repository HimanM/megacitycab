package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class UserDAOImplTest {

    private static UserDAOImpl userDAO;
    private int testUserId;

    @BeforeAll
    static void setup() {
        userDAO = new UserDAOImpl();
    }


    @BeforeEach
    void insertTestUser() {
        User testUser = new User(0, "testuser", "password123", "Test User",
                "test@example.com", "123 Test Street", "123456789V", "0712345678",
                "customer", LocalDateTime.now());

        testUserId = userDAO.addUser(testUser);
        assertTrue(testUserId > 0, "Test user insertion failed");
    }

    @AfterEach
    void cleanUp() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setInt(1, testUserId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void testGetUserById() {
        User user = userDAO.getUserById(testUserId);
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
    }

    @Test
    @Order(2)
    void testUpdateUser() {
        User user = userDAO.getUserById(testUserId);
        assertNotNull(user);

        user.setName("Updated Name");
        boolean isUpdated = userDAO.updateUser(user);

        assertTrue(isUpdated);

        User updatedUser = userDAO.getUserById(testUserId);
        assertEquals("Updated Name", updatedUser.getName());
    }

    @Test
    @Order(3)
    void testDeleteUser() {
        boolean isDeleted = userDAO.deleteUser(testUserId);
        assertTrue(isDeleted);

        User user = userDAO.getUserById(testUserId);
        assertNull(user);
    }

    @Test
    @Order(4)
    void testGetUserByUsername() {
        User testUser = userDAO.getUserByUsername("testuser");
        assertNotNull(testUser);
        assertEquals("testuser", testUser.getUsername());

        User nonExistentUser = userDAO.getUserByUsername("nonexistentuser");
        assertNull(nonExistentUser);

    }

    @Test
    @Order(5)
    void testGetAllUsers() {
        List<User> users = userDAO.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(6)
    void testAddUser() {
        User testUser = new User(0, "testuser2", "password123", "Test User2",
                "test2@example.com", "123 Test Street", "1234567810V", "0712345678",
                "customer", LocalDateTime.now());

        int userId = userDAO.addUser(testUser);
        assertNotEquals(0, userId);
        assertEquals("testuser2", userDAO.getUserById(userId).getUsername());
    }

    @Test
    @Order(7)
    void testGetUserByEmail() {
        User user = userDAO.getUserByEmail("test@example.com");
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
    }


    @Test
    @Order(8)
    void getUserByNic() {
        User user = userDAO.getUserByNic("123456789V");
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
    }
}