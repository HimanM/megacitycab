package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Driver;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverDAOImplTest {

    private static DriverDAOImpl driverDAO;
    private static int testDriverId;

    @BeforeAll
    static void setUp() {
        driverDAO = new DriverDAOImpl();
    }

    @Test
    @Order(1)
    void testAddDriver() {
        Driver driver = new Driver();
        driver.setUserId(188); //Test Driver from Users Table
        driver.setLicenseNumber("TEST123456");
        driver.setCreatedAt(LocalDateTime.now());

        boolean isAdded = driverDAO.addDriver(driver);
        assertTrue(isAdded, "Failed to add driver");

        // Fetch the driver ID for cleanup
        testDriverId = driverDAO.getDriverIdByUserId(driver.getUserId());
        assertNotEquals(-1, testDriverId, "Failed to retrieve test driver ID");
    }

    @Test
    @Order(2)
    void testGetDriverById() {
        Driver driver = driverDAO.getDriverById(testDriverId);
        assertNotNull(driver, "Driver not found");
        assertEquals("TEST123456", driver.getLicenseNumber(), "License number mismatch");
    }

    @Test
    @Order(3)
    void testVerifyDriver() {
        boolean isVerified = driverDAO.verifyDriver(testDriverId);
        assertTrue(isVerified, "Failed to verify driver");

        Driver driver = driverDAO.getDriverById(testDriverId);
        assertEquals("Yes", driver.isVerified(), "Driver verification failed");
    }

    @Test
    @Order(4)
    void testReleaseDriver() {
        driverDAO.releaseDriver(testDriverId);
        Driver driver = driverDAO.getDriverById(testDriverId);
        assertEquals("Available", driver.getStatus(), "Driver release failed");
    }

    @Test
    @Order(5)
    void testGetUnverifiedDrivers() {
        List<Driver> unverifiedDrivers = driverDAO.getUnverifiedDrivers();
        assertNotNull(unverifiedDrivers, "Unverified drivers list is null");
    }

    @Test
    @Order(6)
    void testGetAllDrivers() {
        List<Driver> drivers = driverDAO.getAllDrivers();
        assertNotNull(drivers, "Drivers list is null");
        assertFalse(drivers.isEmpty(), "Drivers list is empty");
    }

    @Test
    @Order(7)
    void testDeleteDriver() {
        boolean isDeleted = driverDAO.deleteDriver(testDriverId);
        assertTrue(isDeleted, "Failed to delete driver");

        Driver driver = driverDAO.getDriverById(testDriverId);
        assertNotNull(driver, "Driver not found after deletion");
        assertEquals("Inactive", driver.getStatus(), "Driver status not set to Inactive");
    }

    @AfterAll
    static void cleanUp() {
        // Delete the test driver from the database
        String deleteSQL = "DELETE FROM drivers WHERE id = ?";
        try (var conn = DatabaseConnection.getConnection();
             var stmt = conn.prepareStatement(deleteSQL)) {
            stmt.setInt(1, testDriverId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
