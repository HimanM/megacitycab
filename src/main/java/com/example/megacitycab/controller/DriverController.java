package com.example.megacitycab.controller;

import com.example.megacitycab.model.Assignment;
import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.combined.BookingDetails;
import com.example.megacitycab.service.BookingAssignmentService;
import com.example.megacitycab.service.BookingService;
import com.example.megacitycab.service.DriverService;
import com.example.megacitycab.service.VehicleService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/driver/dashboard/*")
public class DriverController extends HttpServlet {
    private final DriverService driverService = new DriverService();
    private final BookingService bookingService = new BookingService();
    private final BookingAssignmentService bookingAssignmentService = new BookingAssignmentService();
    private final VehicleService vehicleService = new VehicleService();

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


        String action = req.getPathInfo();
        switch (action) {
            case "/finish":
                finishRide(req, resp);
                break;
            case "/cancel":
                try {
                    int bookingId = Integer.parseInt(req.getParameter("bookingId"));
                    cancelBooking(bookingId);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/driver/dashboard?error=InvalidBookingId");
                    return;
                }
                break;
            default:

        }
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

            Integer userId = (Integer) req.getSession().getAttribute("userId");
            Integer driverId = driverService.getDriverIdByUserId(userId);
            Integer bookingId;

            try {
                bookingId = Integer.parseInt(req.getParameter("bookingId"));
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/driver/dashboard?error=InvalidBookingId");
                return;
            }

            if (action.equals("/accept")) {
                bookingService.acceptBooking(bookingId, driverId);
                initDetailsPage(bookingId, req, resp);
            } else if (action.equals("/cancel")) {
                cancelBooking(bookingId);
            } else if (action.equals("/details")) {
                initDetailsPage(bookingId, req, resp);
            }
            resp.sendRedirect(req.getContextPath() + "/driver/dashboard");
    }

    private void finishRide(HttpServletRequest req, HttpServletResponse resp) {
        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        if(bookingAssignmentService.finishRide(bookingId)){
            req.setAttribute("rideComplete", true);
        }
    }

    private void initDetailsPage(Integer bookingId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        BookingDetails rideDetails = bookingService.getAllDetails(bookingId);

        if (rideDetails != null) {
            req.setAttribute("rideDetails", rideDetails);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/driver/rideDetails.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/driver/dashboard?error=BookingNotFound");
        }
    }

    private void cancelBooking (Integer bookingId) {
        Assignment assignment = bookingAssignmentService.getAssignmentByBookingId(bookingId);
        driverService.releaseDriver(assignment.getDriverId());
        vehicleService.releaseVehicle(assignment.getVehicleId());
        bookingService.cancelBooking(bookingId);
    }
}

