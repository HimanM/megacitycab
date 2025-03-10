package com.example.megacitycab.controller;

import com.example.megacitycab.dao.*;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.Payment;
import com.example.megacitycab.model.User;
import com.example.megacitycab.model.Vehicle;
import com.example.megacitycab.DTO.Ride;
import com.example.megacitycab.service.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.megacitycab.util.MessageBoxUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard/*")
public class AdminController extends HttpServlet {
    private final MessageBoxUtil messageBoxUtil = new MessageBoxUtil();
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    // Default constructor
    public AdminController(){
        this.adminService = new AdminService(new UserDAOImpl(), new RideDAOImpl(), new DriverDAOImpl(), new PaymentDAOImpl(), new VehicleDAOImpl());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer adminId = (Integer) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");
        if (adminId == null || role == null || !role.equals("Admin")) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String action = request.getPathInfo();


        if (action != null) {
            switch (action) {
                case "/viewPayments":
                    viewPayments(request, response);
                    break;
                case "/manageUsers":
                    manageUsers(request, response);
                    break;
                case "/manageVehicles":
                    manageVehicles(request, response);
                    break;
                case "/manageDrivers":
                    viewDrivers(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp");
                    dispatcher.forward(request, response);
            }
        }else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp");
            dispatcher.forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        switch (action) {
            case "/verifyDriver":
                verifyDriver(req, resp);
                break;
            case "/removeDriver":
                removeDriver(req, resp);
                break;
            case "/viewTrip":
                viewTrip(req, resp);
                break;
            case "/deleteUsers":
                deleteUsers(req, resp);
                break;
            case "/addVehicles":
                addVehicle(req, resp);
                break;
            case "/updateVehicles":
                updateVehicle(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void updateVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer vehicleId = Integer.parseInt(req.getParameter("vehicleId"));
        String newStatus = req.getParameter("status");
        if(adminService.updateVehicleStatus(vehicleId, newStatus)){
            manageVehicles(messageBoxUtil.displayMessageBox(req,"success","Vehicle Status Changed Successfully.","manageVehicles"), resp);
        }else {
            manageVehicles(messageBoxUtil.displayMessageBox(req,"error","Vehicle Status Change Failed.","manageVehicles"), resp);
        }
    }

    private void addVehicle(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Vehicle vehicle = new Vehicle();
        String licensePlate = req.getParameter("license_plate");
        String model = req.getParameter("model");
        String manufacturer = req.getParameter("manufacturer");
        String vehicleType = req.getParameter("vehicle_type");
        String makeYear = req.getParameter("year");
        String capacity = req.getParameter("capacity");

        vehicle.setLicensePlate(licensePlate);
        vehicle.setModel(model);
        vehicle.setManufacturer(manufacturer);
        vehicle.setVehicleType(vehicleType);
        vehicle.setYear(Integer.parseInt(makeYear));
        vehicle.setCapacity(Integer.parseInt(capacity));
        vehicle.setCreatedAt(java.time.LocalDateTime.now());

        adminService.addVehicle(vehicle);
        manageVehicles(messageBoxUtil.displayMessageBox(req,"success","Vehicle added successfully","manageVehicles"),resp);
    }

    private void deleteUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        adminService.deleteCustomer(userId);
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard/manageUsers");
    }

    private void viewPayments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Payment> payments = adminService.getAllPayments();
        request.setAttribute("payments", payments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/viewPayments.jsp");
        dispatcher.forward(request, response);
    }

    private void manageUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = adminService.getAllCustomers();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/manageUsers.jsp");
        dispatcher.forward(request, response);
    }
    
    private void manageVehicles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Vehicle> assignedVehicles = adminService.getAssignedVehicles();
        List<Vehicle> availableVehicles = adminService.getAvailableVehicles();
        List<Vehicle> maintenanceVehicles = adminService.getMaintenanceVehicles();
        request.setAttribute("assignedVehicles", assignedVehicles);
        request.setAttribute("availableVehicles", availableVehicles);
        request.setAttribute("maintenanceVehicles", maintenanceVehicles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/manageVehicles.jsp");
        dispatcher.forward(request, response);
    }

    private void viewDrivers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Driver> drivers = adminService.getAllDrivers();
        request.setAttribute("driversList", drivers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/manageDrivers.jsp");
        dispatcher.forward(request, response);
    }

    private void verifyDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int driverId = Integer.parseInt(request.getParameter("verifyDriverId"));
        adminService.verifyDriver(driverId);
        response.sendRedirect(request.getContextPath() + "/admin/dashboard/manageDrivers");

    }

    private void removeDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int driverId = Integer.parseInt(request.getParameter("id"));
        adminService.removeDriver(driverId);
        response.sendRedirect(request.getContextPath() + "/admin/dashboard/manageDrivers");
    }

    private void viewTrip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int driverId = Integer.parseInt(request.getParameter("driverId"));
        Ride trip = adminService.getRideByDriverId(driverId);
        if (trip != null) {
            request.setAttribute("trip", trip);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/tripDetails.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard/manageDrivers");
        }
    }
}