package com.example.megacitycab.controller;

import com.example.megacitycab.model.Driver;
import com.example.megacitycab.model.Payment;
import com.example.megacitycab.model.User;
import com.example.megacitycab.model.Vehicle;
import com.example.megacitycab.service.CustomerService;
import com.example.megacitycab.service.DriverService;
import com.example.megacitycab.service.PaymentService;
import com.example.megacitycab.service.VehicleService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard/*")
public class AdminController extends HttpServlet {
    private final PaymentService paymentService = new PaymentService();
    private final DriverService driverService = new DriverService();
    private final CustomerService userService = new CustomerService();
    private final VehicleService vehicleService = new VehicleService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer customerId = (Integer) request.getSession().getAttribute("userId");
        if (customerId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "/viewPayments":
                    viewPayments(request, response);
                    break;
                case "/manageDrivers":
                    manageDrivers(request, response);
                    break;
                case "/manageUsers":
                    manageUsers(request, response);
                    break;
                case "/manageVehicles":
                    manageVehicles(request, response);
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

    private void viewPayments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Payment> payments = paymentService.getAllPayments();
        request.setAttribute("payments", payments);
        request.getRequestDispatcher("view_payments.jsp").forward(request, response);
    }

    private void manageDrivers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Driver> unverifiedDrivers = driverService.getUnverifiedDrivers();
        request.setAttribute("drivers", unverifiedDrivers);
        request.getRequestDispatcher("manage_drivers.jsp").forward(request, response);
    }

    private void manageUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAllCustomers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("manage_users.jsp").forward(request, response);
    }

    private void manageVehicles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        request.setAttribute("vehicles", vehicles);
        request.getRequestDispatcher("manage_vehicles.jsp").forward(request, response);
    }
}