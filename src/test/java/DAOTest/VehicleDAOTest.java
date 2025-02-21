package DAOTest;//package DAOTest;

import com.example.megacitycab.dao.Interfaces.VehicleDAO;
import com.example.megacitycab.dao.VehicleDAOImpl;
import com.example.megacitycab.model.Vehicle;


import java.util.List;

public class VehicleDAOTest {
    public static void main(String[] args) {
        VehicleDAO vehicleDAO = new VehicleDAOImpl();

        // 3. Get All Vehicles
        System.out.println("Fetching all vehicles...");
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        for (Vehicle v : vehicles) {
            System.out.println("Vehicle ID: " + v.getId() + ", Model: " + v.getModel());
        }

        System.out.println("Fetching all available vehicles...");
        List<Vehicle> avehicles = vehicleDAO.getAvailableVehicles();
        for (Vehicle v : avehicles) {
            System.out.println("Vehicle ID: " + v.getId() + ", Model: " + v.getModel());
        }

    }
}
