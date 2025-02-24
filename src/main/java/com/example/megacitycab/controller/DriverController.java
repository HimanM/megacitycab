package com.example.megacitycab.controller;

import com.example.megacitycab.model.Assignment;
import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.DTO.BookingDetails;
import com.example.megacitycab.service.BookingService;
import com.example.megacitycab.service.DriverService;
import com.example.megacitycab.util.MessageBoxUtil;
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
    private final MessageBoxUtil messageBoxUtil = new MessageBoxUtil();
    private final BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initDashboard(req, resp);

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
                cancelAndAssignBooking(bookingId);
            } else if (action.equals("/details")) {
                initDetailsPage(bookingId, req, resp);
            }
            resp.sendRedirect(req.getContextPath() + "/driver/dashboard");
    }

    private void cancelAndAssignBooking(Integer bookingId) {
        Assignment assignment = driverService.getAssignmentByBookingId(bookingId);
        int currentDriverId = assignment.getDriverId();
        driverService.releaseDriver(currentDriverId);
        int newDriverId = driverService.getAndAssignAvailableDriver(currentDriverId);
        if (newDriverId == -1) {
            driverService.releaseVehicle(assignment.getVehicleId());
            driverService.cancelBooking(bookingId);
            System.out.println("No driver available so booking cancelled");
        }else{
            driverService.updateDriver(assignment.getId(), newDriverId);
        }
    }

    private void finishRide(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        if(driverService.finishRide(bookingId)){
            req.setAttribute("rideComplete", true);
            initDashboard(messageBoxUtil.displayMessageBox(req,"success","Ride Complete Successfully","dashboard"), resp);
        }else{
            req.setAttribute("rideComplete", false);
            initDashboard(messageBoxUtil.displayMessageBox(req,"error","Ride Complete Failed","dashboard"), resp);
        }
    }

    private void initDetailsPage(Integer bookingId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        BookingDetails rideDetails = driverService.getAllBookingDetails(bookingId);

        if (rideDetails != null) {
            req.setAttribute("rideDetails", rideDetails);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/driver/rideDetails.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/driver/dashboard?error=BookingNotFound");
        }
    }

    private void cancelBooking (Integer bookingId) {
        Assignment assignment = driverService.getAssignmentByBookingId(bookingId);
        driverService.releaseDriver(assignment.getDriverId());
        driverService.releaseVehicle(assignment.getVehicleId());
        driverService.cancelBooking(bookingId);
    }

    private void initDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        Integer driverId = driverService.getDriverIdByUserId(userId);

        if (driverId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        // Fetch assigned bookings
        List<Booking> assignedBookings = driverService.getAssignedBookings(driverId);
        req.setAttribute("assignedBookings", assignedBookings);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/driver/dashboard.jsp");
        dispatcher.forward(req, resp);
    }
}


