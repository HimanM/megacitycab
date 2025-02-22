package com.example.megacitycab.controller;

import com.example.megacitycab.model.User;
import com.example.megacitycab.service.CustomerService;
import com.example.megacitycab.util.HashPassword;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.example.megacitycab.util.HashPassword.hashPassword;

@WebServlet("/profile/*")
public class UserController extends HttpServlet {


    private final CustomerService userService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer UserId = (Integer) request.getSession().getAttribute("userId");
        User loggedInUser = userService.getCustomerById(UserId);

        if (loggedInUser != null) {
            request.setAttribute("loggedInUser", loggedInUser);
            request.setAttribute("role", loggedInUser.getRole());
            request.getRequestDispatcher("/WEB-INF/views/other/profile.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/auth/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer UserId = (Integer) request.getSession().getAttribute("userId");
        User loggedInUser = userService.getCustomerById(UserId);

        if (loggedInUser != null) {
            String email = request.getParameter("email");
            String nic = request.getParameter("nic");
            String phoneNumber = request.getParameter("phoneNumber");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm-password");

            // Validate user input
            if (email == null || email.isEmpty() || phoneNumber == null || phoneNumber.isEmpty()) {
                request.setAttribute("error", "Email and Phone number are required.");
                request.getRequestDispatcher("/WEB-INF/views/other/profile.jsp").forward(request, response);
                return;
            }
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match.");
                request.getRequestDispatcher("/WEB-INF/views/other/profile.jsp").forward(request, response);
                return;
            }
            if (password != null && !password.isEmpty()) {
                loggedInUser.setPassword(password);
            }
            // Update the user in the database
            loggedInUser.setEmail(email);
            loggedInUser.setNic(nic);
            loggedInUser.setPhone(phoneNumber);


            boolean updateSuccess = userService.updateCustomer(loggedInUser);
            if (updateSuccess) {
                request.setAttribute("success", "Profile updated successfully.");
                request.getRequestDispatcher("/WEB-INF/views/other/profile.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to update profile. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/other/profile.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}