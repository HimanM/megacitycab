package com.example.megacitycab.controller;

import com.example.megacitycab.model.Driver;
import com.example.megacitycab.service.DriverService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/driver/*")
public class DriverController extends HttpServlet {
    private final DriverService driverService = new DriverService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        switch (action) {
            case "/dashboard":
                forward(req, resp, "/WEB-INF/views/customer/dashboard.jsp");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/driver/dashboard");
        }
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }

        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if ("/save".equals(action)) {
//            handleDriverRegistration(req, resp);
        }



            //        int driverId = (Integer) req.getSession().getAttribute("userId");
//        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
//
//        boolean accepted = driverService.acceptBooking(driverId, bookingId);
//        if (accepted) {
//            resp.sendRedirect(req.getContextPath() + "/driver/dashboard");
//        } else {
//            req.setAttribute("error", "Failed to accept booking.");
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/driver/dashboard.jsp");
//            dispatcher.forward(req, resp);
//        }
    }

//    private void handleDriverRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        int userId = Integer.parseInt(req.getParameter("user_id"));
//        String licenseNumber = req.getParameter("license_number");
//
//        Driver driver = new Driver();
//        driver.setUserId(userId);
//        driver.setLicenseNumber(licenseNumber);
//        driver.setVerified("No");
//        driver.setCreatedAt(LocalDateTime.now());
//
//        boolean isRegistered = driverService.registerDriver(driver);
//
//        if (isRegistered) {
//            resp.sendRedirect(req.getContextPath() + "/auth/login?success=driver_registered");
//        } else {
//            resp.sendRedirect(req.getContextPath() + "/driver/register?user_id=" + userId + "&error=registration_failed");
//        }
//    }

}
