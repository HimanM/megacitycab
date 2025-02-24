package com.example.megacitycab.controller;

import com.example.megacitycab.model.*;
import com.example.megacitycab.model.combined.RidePayment;
import com.example.megacitycab.service.*;
import com.example.megacitycab.util.MessageBoxUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/customer/*")
public class BookingController extends HttpServlet {
    private final BookingService bookingService = new BookingService();
    private final VehicleService vehicleService = new VehicleService();
    private final DriverService driverService = new DriverService();
    private final PaymentService paymentService = new PaymentService();
    private final CustomerService customerService = new CustomerService();
    private final BookingAssignmentService bookingAssignmentService = new BookingAssignmentService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        Integer customerId = (Integer) req.getSession().getAttribute("userId");

        if (customerId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        switch (action) {
            case "/booking/placeBooking":
                initPlaceBooking(req, resp);
                break;
            case "/booking/viewBookings":
                viewBookings(req, resp);
                break;
            case "/booking/details":
                viewBookingDetails(req, resp);
                break;
            case "/booking/cancel":
                cancelBooking(req, resp);
                break;
            case "/dashboard":
                viewDashboard(req, resp);
                break;
            default:
                viewDashboard(req, resp);
        }
    }

    private void viewDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/dashboard.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        switch (action) {
            case "/booking/placeBooking":
                createBooking(req, resp);
                break;
            case "/booking//cancel":
                deleteBooking(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private void createBooking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String destinationDetails = req.getParameter("destination");
            String pickupLocationDetails = req.getParameter("pickupLocation");
            String bookingDateStr = req.getParameter("bookingDate");
            String selectedVehicleIdStr = req.getParameter("vehicleId");


            System.out.println(selectedVehicleIdStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            if (destinationDetails == null || destinationDetails.isEmpty()) {
                req.setAttribute("error", "Destination details cannot be empty.");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/placeBooking.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            if (pickupLocationDetails == null || pickupLocationDetails.isEmpty()) {
                req.setAttribute("error", "Pickup Location details cannot be empty.");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/placeBooking.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            Integer customerId = (Integer) req.getSession().getAttribute("userId");
            if (customerId == null) {
                resp.sendRedirect(req.getContextPath() + "/auth/login");
                return;
            }

            if (req.getParameter("calculateFare") != null) {
                RidePayment ridePayment = bookingService.calculateFare(destinationDetails, pickupLocationDetails);
                Vehicle selectedVehicle = vehicleService.getVehicleById(Integer.parseInt(selectedVehicleIdStr));

                req.setAttribute("fare", ridePayment.getTotalAfterTax());
                req.getSession().setAttribute("calculatedFare", ridePayment.getTotalAfterTax());
                req.setAttribute("tax", ridePayment.getTax());
                req.setAttribute("beforeTax", ridePayment.getTotal());
                req.setAttribute("vehicleId", selectedVehicleIdStr);
                req.setAttribute("vehicleType", selectedVehicle.getManufacturer() + " - " + selectedVehicle.getModel() + " (Capacity: " + selectedVehicle.getCapacity() + ")");
                req.setAttribute("destination", destinationDetails);
                req.setAttribute("pickupLocation", pickupLocationDetails);
                req.setAttribute("bookingDate", bookingDateStr);

                List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
                req.setAttribute("availableVehicles", availableVehicles);
                req.setAttribute("selectedVehicleId", selectedVehicleIdStr);
            }
            else if (req.getParameter("placeBooking") != null) {
                if (selectedVehicleIdStr == null || selectedVehicleIdStr.isEmpty()) {

                    System.out.println(selectedVehicleIdStr);
                    System.out.println("Error here");
                    req.setAttribute("error", "Please select a vehicle.");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/placeBooking.jsp");
                    dispatcher.forward(req, resp);
                    return;
                }

                int selectedVehicleId = Integer.parseInt(selectedVehicleIdStr);
                LocalDateTime bookingDate = LocalDateTime.parse(bookingDateStr, formatter);
                double totalAmount = (double) req.getSession().getAttribute("calculatedFare");

                // Retrieve Credit Card Details
                String cardNumber = req.getParameter("cardNumber");
                String expiryDate = req.getParameter("expiryDate");
                String cvv = req.getParameter("cvv");

                // Validate credit card fields
                if (cardNumber == null || expiryDate == null || cvv == null ||
                        cardNumber.length() != 16 || cvv.length() != 3) {
                    req.setAttribute("error", "Invalid credit card details.");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/placeBooking.jsp");
                    dispatcher.forward(req, resp);
                    return;
                }

                // Assign an available driver
                Integer driverId = driverService.getAndAssignAvailableDriver(-1);
                if (driverId == -1) {
                    req.setAttribute("noDrivers", true);
                    initPlaceBooking(req, resp);
                    return;
                }

                // Create Booking
                Booking booking = new Booking();
                booking.setCustomerId(customerId);
                booking.setOrderNumber("ORD-" + System.currentTimeMillis());
                booking.setDestinationDetails(destinationDetails);
                booking.setPickupLocation(pickupLocationDetails);
                booking.setBookingDate(bookingDate);
                booking.setTotalAmount(totalAmount);
                booking.setStatus("PENDING");

                boolean success = bookingService.createBooking(booking);
                if (success) {
                    // Insert into booking_assignments
                    Assignment bookingAssignment = new Assignment();
                    bookingAssignment.setBookingId(booking.getId());
                    bookingAssignment.setDriverId(driverId);
                    bookingAssignment.setVehicleId(selectedVehicleId);
                    bookingAssignment.setAssignedAt(LocalDateTime.now());

                    boolean assignmentSuccess = bookingAssignmentService.createAssignment(bookingAssignment);
                    if (!assignmentSuccess) {
                        req.setAttribute("error", "Booking placed, but driver assignment failed.");
                    }
                    vehicleService.assignVehicle(selectedVehicleId);

                    // Insert into Payment Table
                    Payment payment = new Payment();
                    payment.setBookingId(booking.getId());
                    payment.setCustomerId(customerId);
                    payment.setAmount(totalAmount);
                    payment.setCardHolderName(customerService.getCustomerById(customerId).getName());
                    payment.setCardNumber(cardNumber);
                    payment.setExpiryDate(expiryDate);
                    payment.setCvv(cvv);
                    payment.setStatus("Completed");

                    boolean paymentSuccess = paymentService.processPayment(payment);
                    if (!paymentSuccess) {
                        req.setAttribute("error", "Payment failed. Please try again.");
                    }

                    req.setAttribute("booking", booking);
                    req.setAttribute("status", booking.getStatus());
                } else {
                    req.setAttribute("error", "Failed to place booking. Please try again.");
                }
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/placeBooking.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred while processing the request.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/placeBooking.jsp");
            dispatcher.forward(req, resp);
        }
    }



    private void deleteBooking(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        boolean success = bookingService.deleteBooking(bookingId);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/customer/viewBookings");
        } else {
            req.setAttribute("error", "Failed to cancel booking. Please try again.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/viewBookings.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void viewBookings(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer customerId = (Integer) req.getSession().getAttribute("userId");

        if(customerId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        List<Booking> activeBookings = bookingService.getActiveBookings(customerId);
        List<Booking> previousBookings = bookingService.getPreviousBookings(customerId);
        List<Booking> cancelledBookings = bookingService.getCancelledBookings(customerId);

        req.setAttribute("activeBookings", activeBookings);
        req.setAttribute("previousBookings", previousBookings);
        req.setAttribute("cancelledBookings", cancelledBookings);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/viewBookings.jsp");
        dispatcher.forward(req, resp);
    }
    private void viewBookingDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        Booking booking = bookingService.getBookingById(bookingId);
        Assignment assignment = bookingAssignmentService.getAssignmentByBookingId(bookingId);

        int userId = (int) req.getSession().getAttribute("userId");
        if (booking == null || !(booking.getCustomerId() == userId)) {
            resp.sendRedirect(req.getContextPath() + "/customer/booking/viewBookings");
            return;
        }

        if (assignment != null) {
            int driverId = assignment.getDriverId();
            int vehicleId = assignment.getVehicleId();

            User driver = driverService.getDriverById(driverId);
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            req.setAttribute("driverName", driver.getName());
            req.setAttribute("driverPhone", driver.getPhone());
            req.setAttribute("vehicle", vehicle.getManufacturer() + " " + vehicle.getModel() + " Plate No:" +vehicle.getLicensePlate());
            req.setAttribute("booking", booking);
        }

        req.getRequestDispatcher("/WEB-INF/views/customer/bookingDetails.jsp").forward(req, resp);
    }
    private void cancelBooking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        Assignment assignment = bookingAssignmentService.getAssignmentByBookingId(bookingId);

        if (assignment != null) {
            int driverId = assignment.getDriverId();
            int vehicleId = assignment.getVehicleId();
            vehicleService.releaseVehicle(vehicleId);
            driverService.releaseDriver(driverId);
        }
        bookingService.cancelBooking(bookingId);
        resp.sendRedirect(req.getContextPath() + "/customer/booking/viewBookings");
    }

    private void initPlaceBooking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer customerId = (Integer) req.getSession().getAttribute("userId");

        if (customerId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        // Check if the user has an active booking
        if (bookingService.hasActiveBooking(customerId)) {
            req.setAttribute("activeBooking", true);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/customer/placeBooking.jsp");

        // Fetch available vehicles for selection
        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
        req.setAttribute("availableVehicles", availableVehicles);
        dispatcher.forward(req, resp);
    }
}