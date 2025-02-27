package bin;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.BookingAssignmentDAOImpl;
import com.example.megacitycab.dao.BookingDAOImpl;
import com.example.megacitycab.dao.DriverDAOImpl;
import com.example.megacitycab.dao.VehicleDAOImpl;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.User;
import com.example.megacitycab.service.DriverService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverServiceTest {

    private DriverService driverService;
    private int testDriverUserId;
    private int testDriverId;
    private int testBookingId;
    private int testVehicleId;

    @BeforeAll
    void setup() {
        driverService = new DriverService(new BookingDAOImpl(), new BookingAssignmentDAOImpl(),new DriverDAOImpl(), new VehicleDAOImpl());

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Insert test driver as a user
            PreparedStatement stmt1 = connection.prepareStatement(
                    "INSERT INTO users (username, password, name, address, nic, phone, role, email) " +
                            "VALUES ('testDriver', 'password123', 'Test Driver', '123 Test Street', '123456123V', '0712345678', 'Driver', 'driver@example.com')",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt1.executeUpdate();
            var rs1 = stmt1.getGeneratedKeys();
            if (rs1.next()) testDriverUserId = rs1.getInt(1);

            // Insert test driver
            PreparedStatement stmt2 = connection.prepareStatement(
                    "INSERT INTO drivers (user_id, license_number, verified) VALUES (?, 'TEST123', 'No')",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt2.setInt(1, testDriverUserId);
            stmt2.executeUpdate();
            var rs2 = stmt2.getGeneratedKeys();
            if (rs2.next()) testDriverId = rs2.getInt(1);

            // Insert test vehicle
            PreparedStatement stmt3 = connection.prepareStatement(
                    "INSERT INTO vehicles (vehicle_type, model, manufacturer, year, capacity, license_plate) " +
                            "VALUES ('SUV', 'Test Model', 'Test Manufacturer', 2023, 5, 'ABCD321')",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt3.executeUpdate();
            var rs3 = stmt3.getGeneratedKeys();
            if (rs3.next()) testVehicleId = rs3.getInt(1);

            // Insert test booking
            PreparedStatement stmt4 = connection.prepareStatement(
                    "INSERT INTO booking_assignments (booking_id, driver_id, vehicle_id) VALUES (1030, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt4.setInt(1, testDriverId);
            stmt4.setInt(2, testVehicleId);
            stmt4.executeUpdate();
            var rs4 = stmt4.getGeneratedKeys();
            if (rs4.next()) testBookingId = rs4.getInt(1);

        } catch (SQLException e) {
            fail("Error setting up test data: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void testRegisterDriver() {
        Driver driver = new Driver();
        driver.setUserId(testDriverUserId);
        driver.setLicenseNumber("NEWLICENSE123");
        driver.setCreatedAt(LocalDateTime.now());

        assertDoesNotThrow(() -> {
            boolean result = driverService.registerDriver(driver);
            assertTrue(result);
        });
    }

    @Test
    @Order(2)
    void testGetDriverById() {
        assertDoesNotThrow(() -> {
            User driver = driverService.getDriverById(testDriverId);
            assertNotNull(driver);
            assertEquals("testDriver", driver.getUsername());
        });
    }


    @Test
    @Order(3)
    void testVerifyDriver() {
        assertDoesNotThrow(() -> {
            driverService.verifyDriver(testDriverId);
            Driver driver = driverService.getDriverById(testDriverId);
            assertEquals("Yes", driver.isVerified());
        });
    }

    @Test
    @Order(4)
    void testGetUnverifiedDrivers() {
        assertDoesNotThrow(() -> {
            List<Driver> unverifiedDrivers = driverService.getUnverifiedDrivers();
            assertNotNull(unverifiedDrivers);
            assertFalse(unverifiedDrivers.isEmpty());
        });
    }

    @Test
    @Order(5)
    void testFinishRide() {
        assertDoesNotThrow(() -> {
            boolean finished = driverService.finishRide(testBookingId);
            assertTrue(finished);
        });
    }

    @AfterAll
    void cleanup() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.prepareStatement("DELETE FROM booking_assignments WHERE id = " + testBookingId).executeUpdate();
            connection.prepareStatement("DELETE FROM vehicles WHERE id = " + testVehicleId).executeUpdate();
            connection.prepareStatement("DELETE FROM drivers WHERE id = " + testDriverId).executeUpdate();
            connection.prepareStatement("DELETE FROM users WHERE id = " + testDriverUserId).executeUpdate();
        } catch (SQLException e) {
            fail("Error cleaning up test data: " + e.getMessage());
        }
    }
}
