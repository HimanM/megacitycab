package com.example.megacitycab.controller;

import com.example.megacitycab.model.Booking;
import com.example.megacitycab.service.BookingService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/booking/*")
public class BookingController extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        switch (action) {
            case "/create":
                createBooking(req, resp);
                break;
            case "/cancel":
                cancelBooking(req, resp);
                break;
            case "/viewBookings":
                listBookings(req, resp);
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void createBooking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Booking booking = new Booking();
        booking.setCustomerId((Integer) req.getSession().getAttribute("userId"));
        booking.setOrderNumber("ORD-" + System.currentTimeMillis()); // Example order number
        booking.setDestinationDetails(req.getParameter("destinationDetails"));
        booking.setBookingDate(LocalDateTime.now());
        booking.setTotalAmount(Double.parseDouble(req.getParameter("totalAmount")));
        booking.setStatus("Pending");

        boolean success = bookingService.createBooking(booking);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/customer/viewBookings");
        } else {
            req.setAttribute("error", "Failed to place booking. Please try again.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/placeBooking.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void cancelBooking(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        boolean success = bookingService.cancelBooking(bookingId);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/customer/viewBookings");
        } else {
            req.setAttribute("error", "Failed to cancel booking. Please try again.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/viewBookings.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void listBookings(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerId = (Integer) req.getSession().getAttribute("userId");

        List<Booking> bookings = bookingService.getBookingsByCustomer(customerId);

        if (bookings == null || bookings.isEmpty()) {
            req.setAttribute("message", "You have no bookings yet.");
        } else {
            req.setAttribute("bookings", bookings);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/viewBookings.jsp");
        dispatcher.forward(req, resp);
    }


}