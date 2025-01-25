import com.example.megacitycab.dao.VehicleDAO;
import com.example.megacitycab.dao.VehicleDAOImpl;
import com.example.megacitycab.model.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public class VehicleDAOTest {
    public static void main(String[] args) {
        VehicleDAO vehicleDAO = new VehicleDAOImpl();

        // 1. Add a Vehicle
        System.out.println("Adding a new vehicle...");
        Vehicle vehicle = new Vehicle(100, "CAB125", "Honda Fit", "Kamal Silva", "0776543210", "AVAILABLE", LocalDateTime.now());
        vehicleDAO.addVehicle(vehicle);
        System.out.println("Vehicle added successfully!");

        // 2. Get Vehicle by ID
        System.out.println("Fetching vehicle with ID 1...");
        Vehicle fetchedVehicle = vehicleDAO.getVehicleById(1);
        if (fetchedVehicle != null) {
            System.out.println("Vehicle Details: " + fetchedVehicle.getVehicleNumber() + ", " + fetchedVehicle.getModel());
        } else {
            System.out.println("Vehicle not found!");
        }

        // 3. Get All Vehicles
        System.out.println("Fetching all vehicles...");
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        for (Vehicle v : vehicles) {
            System.out.println("Vehicle ID: " + v.getId() + ", Model: " + v.getModel());
        }

        // 4. Update Vehicle
        System.out.println("Updating vehicle with ID 1...");
        if (fetchedVehicle != null) {
            fetchedVehicle.setStatus("MAINTENANCE");
            fetchedVehicle.setLastUpdated(LocalDateTime.now());
            vehicleDAO.updateVehicle(fetchedVehicle);
            System.out.println("Vehicle updated successfully!");
        }

        // 5. Delete Vehicle
        System.out.println("Deleting vehicle with ID 1...");
        vehicleDAO.deleteVehicle(1);
        System.out.println("Vehicle deleted successfully!");
    }
}
