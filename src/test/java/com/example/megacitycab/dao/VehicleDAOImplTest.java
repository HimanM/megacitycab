package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Vehicle;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleDAOImplTest {
    private VehicleDAOImpl vehicleDAO;

    @BeforeAll
    void setUp() {
        vehicleDAO = new VehicleDAOImpl();
    }

    @BeforeEach
    void cleanTestVehicles() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement deleteTestVehicles = connection.prepareStatement(
                     "DELETE FROM vehicles WHERE license_plate LIKE 'TEST-%'")) {

            deleteTestVehicles.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void cleanupAfterTests() {
        cleanTestVehicles(); // Clean up test data after all tests run
    }

    @Test
    @Order(1)
    void testAddVehicle() {
        Vehicle vehicle = new Vehicle(0, "Sedan", "Camry", "TEST-123", "Toyota", 2022, 4);
        int vehicleId = vehicleDAO.addVehicle(vehicle);
        assertTrue(vehicleId > 0);
    }

    @Test
    @Order(2)
    void testGetVehicleById() {
        Vehicle vehicle = new Vehicle(0, "SUV", "CR-V", "TEST-456", "Honda", 2021, 5);
        int vehicleId = vehicleDAO.addVehicle(vehicle);

        Vehicle fetchedVehicle = vehicleDAO.getVehicleById(vehicleId);
        assertNotNull(fetchedVehicle);
        assertEquals("SUV", fetchedVehicle.getVehicleType());
        assertEquals("Honda", fetchedVehicle.getManufacturer());
    }

    @Test
    @Order(3)
    void testGetAllVehicles() {
        vehicleDAO.addVehicle(new Vehicle(0, "Sedan", "Accord", "TEST-789", "Honda", 2020, 4));
        vehicleDAO.addVehicle(new Vehicle(0, "Truck", "F-150", "TEST-111", "Ford", 2019, 3));

        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        assertTrue(vehicles.stream().anyMatch(v -> v.getLicensePlate().startsWith("TEST-")));
    }

    @Test
    @Order(4)
    void testUpdateVehicle() {
        Vehicle vehicle = new Vehicle(0, "Van", "Odyssey", "TEST-222", "Honda", 2018, 7);
        int vehicleId = vehicleDAO.addVehicle(vehicle);

        Vehicle updatedVehicle = new Vehicle(vehicleId, "Van", "Sienna", "TEST-222", "Toyota", 2018, 7);
        assertTrue(vehicleDAO.updateVehicle(updatedVehicle));

        Vehicle fetchedVehicle = vehicleDAO.getVehicleById(vehicleId);
        assertEquals("Toyota", fetchedVehicle.getManufacturer());
    }

    @Test
    @Order(5)
    void testDeleteVehicle() {
        Vehicle vehicle = new Vehicle(0, "Coupe", "Mustang", "TEST-333", "Ford", 2023, 2);
        int vehicleId =vehicleDAO.addVehicle(vehicle);

        assertTrue(vehicleDAO.deleteVehicle(vehicleId ));
        assertNull(vehicleDAO.getVehicleById(vehicleId ));
    }

    @Test
    @Order(6)
    void testVehicleStatusUpdates() {
        Vehicle vehicle = new Vehicle(0, "SUV", "X5", "TEST-444", "BMW", 2022, 5);
        int vehicleId =vehicleDAO.addVehicle(vehicle);

        assertTrue(vehicleDAO.updateVehicleStatus(vehicleId, "Assigned"));
        assertEquals("Assigned", vehicleDAO.getVehicleById(vehicleId).getStatus());

        vehicleDAO.releaseVehicle(vehicleId);
        assertEquals("Available", vehicleDAO.getVehicleById(vehicleId).getStatus());
    }

    @Test
    @Order(7)
    void testGetAvailableVehicles() {
        vehicleDAO.addVehicle(new Vehicle(0, "Sedan", "Civic", "TEST-555", "Honda", 2017, 4));
        vehicleDAO.addVehicle(new Vehicle(0, "SUV", "Rav4", "TEST-666", "Toyota", 2019, 5));

        List<Vehicle> availableVehicles = vehicleDAO.getAvailableVehicles();
        assertTrue(availableVehicles.stream().anyMatch(v -> v.getLicensePlate().startsWith("TEST-")));
    }

    @Test
    @Order(8)
    void testGetAssignedVehicles() {
        int vehicleId = vehicleDAO.addVehicle(new Vehicle(0, "Hatchback", "Golf", "TEST-777", "Volkswagen", 2018, 4));
        vehicleDAO.assignVehicle(vehicleId );
        List<Vehicle> assignedVehicles = vehicleDAO.getAssignedVehicles();
        assertTrue(assignedVehicles.stream().anyMatch(v -> v.getLicensePlate().startsWith("TEST-")));
    }

    @Test
    @Order(9)
    void testGetMaintenanceVehicles() {
        int vehicleId = vehicleDAO.addVehicle(new Vehicle(0, "Pickup Truck", "Silverado", "TEST-888", "Chevrolet", 2021, 2));

        vehicleDAO.updateVehicleStatus(vehicleId , "In Maintenance");
        List<Vehicle> maintenanceVehicles = vehicleDAO.getMaintenanceVehicles();
        assertTrue(maintenanceVehicles.stream().anyMatch(v -> v.getLicensePlate().startsWith("TEST-")));
    }
}
