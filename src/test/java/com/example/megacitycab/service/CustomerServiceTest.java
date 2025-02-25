package com.example.megacitycab.service;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.UserDAOImpl;
import com.example.megacitycab.exceptions.CustomerException;
import com.example.megacitycab.model.User;
import org.junit.jupiter.api.*;
import static com.example.megacitycab.util.HashPassword.hashPassword;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
    private static CustomerService customerService;
    private static Connection connection;
    private static UserDAOImpl userDAO;
    private static int testUserId;

    @BeforeAll
    static void setup() throws InterruptedException {
        userDAO = new UserDAOImpl();
        customerService = new CustomerService();
        User testUser = new User(0, "testcustomer", "password123", "Test User",
                "customer@example.com", "123 Test Street", "123456789V", "0712345678",
                "customer", LocalDateTime.now());

        testUserId = userDAO.addUser(testUser);
//        Thread.sleep(20000);
    }


    @Test
    @Order(1)
    void testRegisterCustomer_Success() throws CustomerException {
        User newUser = new User(0, "newuser", "password123", "Test User",
                "newuser@example.com", "123 Test Street", "12345671010V", "0712345678",
                "customer", LocalDateTime.now());
        int userId = customerService.registerCustomer(newUser);
        assertTrue(userId > 0);
    }


    @Test
    @Order(2)
    void testRegisterCustomer_EmailAlreadyRegistered() {
        User existingUser = new User(1, "testcustomerregistered", "password123", "Test User",
                "customer@example.com", "123 Test Street", "12345678911V", "0712345678",
                "customer", LocalDateTime.now());
        Exception exception = assertThrows(CustomerException.class, () ->
                customerService.registerCustomer(existingUser));
        assertEquals("Error while registering customer", exception.getMessage());
    }

    @Test
    @Order(3)
    void testUpdateCustomer_Success() throws CustomerException, NoSuchAlgorithmException, InterruptedException {
        String password = hashPassword("newpassword123");
        User updatedUser = new User(testUserId, "updateduser", password, "Updated User",
                "customer@example.com", "Updated Street", "123456789V", "0712345678",
                "customer", LocalDateTime.now());
        boolean isUpdated = customerService.updateCustomer(updatedUser);
        assertTrue(isUpdated);
//        Thread.sleep(10000); //sleep to get the database value from database
    }

    @Test
    @Order(4)
    void testGetCustomerById_ValidId() throws CustomerException {
        User user = customerService.getCustomerById(testUserId);
        assertNotNull(user);
        assertEquals("testcustomer", user.getUsername());
    }

    @Test
    @Order(5)
    void testGetCustomerById_InvalidId() {
        Exception exception = assertThrows(CustomerException.class, () ->
                customerService.getCustomerById(9999));
        assertEquals("Error while fetching customer details", exception.getMessage());
    }

    @Test
    @Order(6)
    void testGetAllCustomers() throws CustomerException {
        List<User> users = customerService.getAllCustomers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(7)
    void testDeleteCustomer_Success() throws CustomerException {
        User newUser = new User(1, "deleteuser", "password123", "Test User",
                "delete@example.com", "123 Test Street", "123456987V", "0712345678",
                "customer", LocalDateTime.now());
        int userId = customerService.registerCustomer(newUser);

        assertDoesNotThrow(() -> customerService.deleteCustomer(userId));
    }

    @Test
    @Order(8)
    void testIsEmailAlreadyRegistered_True() throws CustomerException {
        assertTrue(customerService.isEmailAlreadyRegistered("customer@example.com"));
    }

    @Test
    @Order(9)
    void testIsEmailAlreadyRegistered_False() throws CustomerException {
        assertFalse(customerService.isEmailAlreadyRegistered("unknown@example.com"));
    }

    @Test
    @Order(10)
    void testPasswordHashing() throws CustomerException, NoSuchAlgorithmException {
        User newUser = new User(0, "newuser3", "password123notHashed", "Test User",
                "newuser3@example.com", "123 Test Street", "12345672010V", "0712345678",
                "customer", LocalDateTime.now());
        int userId = customerService.registerCustomer(newUser);
        User checkUser = customerService.getCustomerById(userId);
        assertNotEquals("password123notHashed",checkUser.getPassword());
        assertEquals(hashPassword("password123notHashed"),checkUser.getPassword());
    }

    @AfterAll
    static void cleanupDatabase() throws SQLException {
        connection = DatabaseConnection.getConnection();
        String deleteTestUsers = "DELETE FROM users WHERE username IN ('testcustomer', 'newuser', 'deleteuser','newuser3','updateduser')";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteTestUsers)) {
            preparedStatement.executeUpdate();
        }
        connection.close();
    }
}
