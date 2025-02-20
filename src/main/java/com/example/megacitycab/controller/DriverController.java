package com.example.megacitycab.controller;

import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.Driver;
import com.example.megacitycab.service.BookingService;
import com.example.megacitycab.service.DriverService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/driver/dashboard/*")
public class DriverController extends HttpServlet {
    private final DriverService driverService = new DriverService();
    private final BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        Integer driverId = driverService.getDriverIdByUserId(userId);

        if (driverId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        // Fetch assigned bookings
        List<Booking> assignedBookings = bookingService.getAssignedBookings(driverId);
        req.setAttribute("assignedBookings", assignedBookings);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/driver/dashboard.jsp");
        dispatcher.forward(req, resp);
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }

        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String action = req.getPathInfo();

            if (action == null) {
                resp.sendRedirect(req.getContextPath() + "/driver/dashboard");
                return;
            }

            Integer driverId = (Integer) req.getSession().getAttribute("userId");
            Integer bookingId = Integer.parseInt(req.getParameter("bookingId"));

            if (action.equals("/accept")) {
                bookingService.acceptBooking(bookingId, driverId);
            } else if (action.equals("/cancel")) {
                bookingService.cancelBooking(bookingId);
            }

            resp.sendRedirect(req.getContextPath() + "/driver/booking");
    }
}

