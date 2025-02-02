package com.example.megacitycab.controller;

import com.example.megacitycab.model.User;
import com.example.megacitycab.service.AuthService;
import com.example.megacitycab.service.CustomerService;
import com.example.megacitycab.util.HashPassword;
import com.example.megacitycab.util.ValidationUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/auth/*")
public class AuthController extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        switch (action) {
            case "/login":
                RequestDispatcher loginDispatcher = req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp");
                loginDispatcher.forward(req, resp);
                break;
            case "/register":
                RequestDispatcher registerDispatcher = req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp");
                registerDispatcher.forward(req, resp);
                break;
            case "/logout":
                HttpSession session = req.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                resp.sendRedirect(req.getContextPath() + "/auth/login");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        switch (action) {
            case "/login":
                handleLogin(req, resp);
                break;
            case "/register":
                try {
                    handleRegister(req, resp);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User customer = AuthService.authenticate(username, password);

        //admin login integration here


        if (customer != null) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", customer.getId());
            session.setAttribute("username", customer.getUsername());
            resp.sendRedirect(req.getContextPath() + "/customer/dashboard");
        } else {
            req.setAttribute("error", "Invalid username or password");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, NoSuchAlgorithmException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String nic = req.getParameter("nic");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");
        String email = req.getParameter("email");

        // Initialize a list to collect error messages
        List<String> errors = new ArrayList<>();

        // Input validation with specific error messages
        if (!ValidationUtil.isValidUsername(username)) {
            errors.add("Username must be alphanumeric and meet the required standards.");
        }
        if (!ValidationUtil.isValidPassword(password)) {
            errors.add("Password must meet the required standards (e.g., minimum 8 characters, include a special character, etc.).");
        }
        if (!ValidationUtil.isValidEmail(email)) {
            errors.add("Please provide a valid email address.");
        } else if (customerService.isEmailAlreadyRegistered(email)) {
            errors.add("The provided email address is already registered.");
        }

        // Additional field validations
        if (name == null || name.trim().isEmpty()) {
            errors.add("Name cannot be empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            errors.add("Address cannot be empty.");
        }
        if (nic == null || nic.trim().isEmpty()) {
            errors.add("NIC cannot be empty.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            errors.add("Phone number cannot be empty.");
        }
        if (role == null || (!role.equals("Customer") && !role.equals("Driver"))) {
            errors.add("Please select a valid role (Customer or Driver).");
        }

        // Check if there are any errors
        if (errors.isEmpty()) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setName(name);
            newUser.setAddress(address);
            newUser.setNic(nic);
            newUser.setPhone(phone);
            newUser.setRole(role);
            newUser.setEmail(email);
            newUser.setCreatedAt(java.time.LocalDateTime.now());

            boolean isRegistered = customerService.registerCustomer(newUser);
            if (isRegistered) {
                resp.sendRedirect(req.getContextPath() + "/auth/login");
            } else {
                req.setAttribute("error", "Registration failed. Please try again.");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp");
                dispatcher.forward(req, resp);
            }
        } else {
            // If there are errors, display them on the registration page
            req.setAttribute("errors", errors);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
