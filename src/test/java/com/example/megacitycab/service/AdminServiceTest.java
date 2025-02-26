package com.example.megacitycab.service;


import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.exceptions.AdminException;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.User;
import com.example.megacitycab.model.Vehicle;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // Ensures test order execution
class AdminServiceTest {

    private static AdminService adminService;
    private static int testUserId;
    private static int testDriverId;
    private static int testVehicleId;
    private static int testDriverUserId;

    @BeforeAll
    static void setup() {
        adminService = new AdminService();
        try (Connection connection = DatabaseConnection.getConnection()) {

            // Insert test user
            PreparedStatement stmt1 = connection.prepareStatement(
                    "INSERT INTO users (username, password, name, address, nic, phone, role, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt1.setString(1, "testUser");
            stmt1.setString(2, "password123");
            stmt1.setString(3, "test user");
            stmt1.setString(4, "123 Test Street");
            stmt1.setString(5, "123456456V");
            stmt1.setString(6, "0712345678");
            stmt1.setString(7, "Customer");
            stmt1.setString(8, "customer@example.com");
            stmt1.executeUpdate();

            ResultSet rs1 = stmt1.getGeneratedKeys();
            if (rs1.next()) {
                testUserId = rs1.getInt(1);
            } else {
                fail("Failed to insert test user");
            }

            // Insert test driver as a User
            PreparedStatement stmt2 = connection.prepareStatement(
                    "INSERT INTO users (username, password, name, address, nic, phone, role, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt2.setString(1, "testDriver");
            stmt2.setString(2, "password123");
            stmt2.setString(3, "test driver");
            stmt2.setString(4, "123 Test Street");
            stmt2.setString(5, "123456123V");
            stmt2.setString(6, "0712345678");
            stmt2.setString(7, "Driver");
            stmt2.setString(8, "driver@example.com");
            stmt2.executeUpdate();

            ResultSet rs2 = stmt2.getGeneratedKeys();
            if (rs2.next()) {
                testDriverUserId = rs2.getInt(1);
            } else {
                fail("Failed to insert test driver user");
            }

            // Insert test driver (linking user_id correctly)
            PreparedStatement stmt3 = connection.prepareStatement(
                    "INSERT INTO drivers (user_id, license_number, verified) VALUES (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt3.setInt(1, testDriverUserId);
            stmt3.setString(2, "TEST123");
            stmt3.setString(3, "No");
            stmt3.executeUpdate();

            ResultSet rs3 = stmt3.getGeneratedKeys();
            if (rs3.next()) {
                testDriverId = rs3.getInt(1);
            } else {
                fail("Failed to insert test driver");
            }

            // Insert test vehicle
            PreparedStatement stmt4 = connection.prepareStatement(
                    "INSERT INTO vehicles (vehicle_type, model, manufacturer, year, capacity, license_plate) VALUES (?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt4.setString(1, "Sedan");
            stmt4.setString(2, "Test Model");
            stmt4.setString(3, "Test Manufacturer");
            stmt4.setInt(4, 2023);
            stmt4.setInt(5, 5);
            stmt4.setString(6, "ABC123");
            stmt4.executeUpdate();

            ResultSet rs4 = stmt4.getGeneratedKeys();
            if (rs4.next()) {
                testVehicleId = rs4.getInt(1);
            } else {
                fail("Failed to insert test vehicle");
            }

        } catch (SQLException e) {
            fail("Error setting up test data: " + e.getMessage());
        }
    }


    @Test
    @Order(1)
    void testGetAllCustomers() {
        try {
            List<User> customers = adminService.getAllCustomers();
            assertNotNull(customers);
            assertFalse(customers.isEmpty());
        } catch (AdminException e) {
            fail("Fetching customers failed: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testDeleteCustomer() {
        try {
            adminService.deleteCustomer(testUserId);
            assertThrows(AdminException.class, () -> adminService.getAllCustomers().stream()
                    .filter(user -> user.getId() == testUserId)
                    .findFirst()
                    .orElseThrow(() -> new AdminException("User still exists after deletion")));
        } catch (AdminException e) {
            fail("Error deleting customer: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testGetAllDrivers() {
        try {
            List<Driver> drivers = adminService.getAllDrivers();
            assertNotNull(drivers);
            assertFalse(drivers.isEmpty());
        } catch (AdminException e) {
            fail("Fetching drivers failed: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    void testVerifyDriver() {
        try {
            adminService.verifyDriver(testDriverId);
            // Verify from database
            List<Driver> drivers = adminService.getAllDrivers();
            assertTrue(drivers.stream().anyMatch(driver -> driver.getId() == testDriverId && driver.isVerified().equals("Yes")));
        } catch (AdminException e) {
            fail("Driver verification failed: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    void testAddVehicle() {
        try {
            Vehicle vehicle = new Vehicle();
            vehicle.setManufacturer("Porsche");
            vehicle.setModel("911 Carrera");
            vehicle.setVehicleType("Convertible");
            vehicle.setLicensePlate("PRST123");
            vehicle.setCapacity(4);
            vehicle.setYear(2023);
            vehicle.setCreatedAt(LocalDateTime.now());
            adminService.addVehicle(vehicle);
            List<Vehicle> vehicles = adminService.getAvailableVehicles();
            assertTrue(vehicles.stream().anyMatch(v -> "911 Carrera".equals(v.getModel())));
        } catch (AdminException e) {
            fail("Adding vehicle failed: " + e.getMessage());
        }
    }

    @Test
    @Order(6)
    void testUpdateVehicleStatus() {
        try {
            adminService.updateVehicleStatus(testVehicleId, "In Maintenance");
            List<Vehicle> maintenanceVehicles = adminService.getMaintenanceVehicles();
            assertTrue(maintenanceVehicles.stream().anyMatch(v -> v.getId() == testVehicleId));
        } catch (AdminException e) {
            fail("Updating vehicle status failed: " + e.getMessage());
        }
    }

    @Test
    @Order(7)
    void testRemoveDriver() {
        try {
            adminService.removeDriver(testDriverId);
            List<Driver> drivers = adminService.getAllDrivers();
            for (Driver driver : drivers) {
                if (driver.getId() == testDriverId) {
                    assertEquals("Inactive", driver.getStatus());
                    assertEquals("NO", driver.isVerified());
                }
            }
        } catch (AdminException e) {
            fail("Removing driver failed: " + e.getMessage());
        }
    }

    @AfterAll
    static void cleanup() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Clean up test records
            PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM drivers WHERE id = ?");
            stmt2.setInt(1, testDriverId);
            stmt2.executeUpdate();

            PreparedStatement stmt1 = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            stmt1.setInt(1, testUserId);
            stmt1.executeUpdate();
            stmt1.setInt(1, testDriverUserId);
            stmt1.executeUpdate();

            PreparedStatement stmt3 = connection.prepareStatement("DELETE FROM vehicles WHERE id = ?");
            stmt3.setInt(1, testVehicleId);
            stmt3.executeUpdate();

            PreparedStatement stmt4 = connection.prepareStatement("DELETE FROM vehicles WHERE license_plate = ?");
            stmt4.setString(1, "PRST123");
            stmt4.executeUpdate();

        } catch (SQLException e) {
            fail("Error cleaning up test data: " + e.getMessage());
        }

    }
}
