package com.example.megacitycab.controller;

import com.example.megacitycab.service.DriverService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/driver/acceptBooking")
public class DriverController extends HttpServlet {
    private DriverService driverService = new DriverService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int driverId = (Integer) req.getSession().getAttribute("userId");
        int bookingId = Integer.parseInt(req.getParameter("bookingId"));

        boolean accepted = driverService.acceptBooking(driverId, bookingId);
        if (accepted) {
            resp.sendRedirect(req.getContextPath() + "/driver/dashboard");
        } else {
            req.setAttribute("error", "Failed to accept booking.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/driver/dashboard.jsp");
            dispatcher.forward(req, resp);
        }
    }

}
