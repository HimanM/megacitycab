package com.example.megacitycab.service;

import com.example.megacitycab.dao.*;
import com.example.megacitycab.dao.Interfaces.*;
import com.example.megacitycab.exceptions.BookingException;
import com.example.megacitycab.model.*;
import com.example.megacitycab.model.DTO.BookingDetails;
import com.example.megacitycab.model.DTO.RidePayment;

import java.util.List;
import java.util.Random;

public class BookingService {

    private final BookingDAO bookingDAO;;
    private final UserDAO userDAO;
    private final BookingAssignmentDAO bookingAssignmentDAO;
    private final DriverDAO driverDAO;
    private final PaymentDAO paymentDAO;
    private  final VehicleDAO vehicleDAO;

    public BookingService(BookingDAO bookingDAO, UserDAO userDAO, BookingAssignmentDAO bookingAssignmentDAO, DriverDAO driverDAO, PaymentDAO paymentDAO, VehicleDAO vehicleDAO) {
        this.bookingDAO = bookingDAO;
        this.userDAO = userDAO;
        this.bookingAssignmentDAO = bookingAssignmentDAO;
        this.driverDAO = driverDAO;
        this.paymentDAO = paymentDAO;
        this.vehicleDAO = vehicleDAO;
    }


    // Create a booking
    public boolean createBooking(Booking booking) throws BookingException {
        boolean b = false;
        if (booking == null) {
            throw new BookingException("Booking data cannot be null");
        }
        try {
            // Perform additional validations if needed
            bookingDAO.addBooking(booking);
            b = true;
        } catch (Exception e) {
            throw new BookingException("Error while creating booking", e);
        }
        return b;
    }

    private static final Random random = new Random();

    public RidePayment calculateFare(String destination, String pickupLocation) {
        // Generate a random fare between $10 and $100
        RidePayment ridePayment = new RidePayment();
        double fare = 10 + (90 * random.nextDouble());
        double tax = 0.15 * fare;
        double totalAmount = fare + tax;
        ridePayment.setTotalAfterTax(totalAmount);
        ridePayment.setTax(tax);
        ridePayment.setTotal(fare);
        return ridePayment;
    }

    // Update an existing booking
    public void updateBooking(Booking updatedBooking) throws BookingException {
        if (updatedBooking == null) {
            throw new BookingException("Updated booking data cannot be null");
        }

        try {
            if (!bookingDAO.updateBooking(updatedBooking)) {
                throw new BookingException("Booking not found or could not be updated");
            }
        } catch (Exception e) {
            throw new BookingException("Error while updating booking", e);
        }
    }

    // Get booking by ID
    public Booking getBookingById(int bookingId) throws BookingException {
        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                throw new BookingException("No booking found with ID: " + bookingId);
            }
            return booking;
        } catch (Exception e) {
            throw new BookingException("Error while fetching booking details", e);
        }
    }

    // Get all bookings for a customer
    public List<Booking> getBookingsByCustomerId(int customerId) throws BookingException {
        try {
            return bookingDAO.getBookingsByCustomerId(customerId);
        } catch (Exception e) {
            throw new BookingException("Error while fetching bookings for customer", e);
        }
    }

    // Delete a booking
    public boolean deleteBooking(int bookingId) throws BookingException {
        try {
            if (!bookingDAO.deleteBooking(bookingId)) {
                throw new BookingException("Booking with ID " + bookingId + " could not be canceled or does not exist");
            }
            return true;
        } catch (Exception e) {
            throw new BookingException("Error while canceling booking", e);
        }
    }

    public boolean hasActiveBooking(int customerId) throws BookingException {
        try {
            return bookingDAO.hasActiveBooking(customerId);
        } catch (Exception e) {
            throw new BookingException("Error while checking for active bookings", e);
        }
    }

    public void cancelBooking(int bookingId) throws BookingException  {
        try {
            bookingDAO.updateBookingStatus(bookingId, "Cancelled");
        }
        catch (Exception e) {
            throw new BookingException("Error while canceling booking", e);
        }
    }

    public List<Booking> getBookingsByCustomer(int customerId) throws BookingException {
        try {
            return bookingDAO.getBookingsByCustomerId(customerId);
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching bookings for customer", e);
        }
    }

    public List<Booking> getActiveBookings(int customerId) throws BookingException {
        try {
            return bookingDAO.getBookingsByStatus(customerId, "PENDING", "Approved");
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching active bookings", e);
        }
    }

    public List<Booking> getPreviousBookings(int customerId) throws BookingException {
        try {
            return bookingDAO.getBookingsByStatus(customerId, "Completed");
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching previous bookings", e);
        }
    }

    public List<Booking> getCancelledBookings(int customerId) throws BookingException {
        try {
            return bookingDAO.getBookingsByStatus(customerId, "Cancelled");
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching cancelled bookings", e);
        }
    }

    public void acceptBooking(int bookingId, int driverId) throws BookingException {
        try {
            bookingDAO.acceptBooking(bookingId, driverId);
        }
        catch (Exception e) {
            throw new BookingException("Error while accepting booking", e);
        }
    }

    public BookingDetails getAllDetails(Integer bookingId) throws BookingException  {
        try {
            return bookingDAO.getAllDetails(bookingId);
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching booking details", e);
        }
    }

    public void completeBooking(int bookingId) throws BookingException  {
        try {
            bookingDAO.completeBooking(bookingId);
        }
        catch (Exception e) {
            throw new BookingException("Error while completing booking", e);
        }
    }

    public Vehicle getVehicleById(int vehicleId) throws BookingException  {
        try {
            return vehicleDAO.getVehicleById(vehicleId);
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching vehicle details", e);
        }
    }

    public List<Vehicle> getAvailableVehicles() throws BookingException  {
        try {
            return vehicleDAO.getAvailableVehicles();
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching available vehicles", e);
        }
    }

    public Integer getAndAssignAvailableDriver(int driverId) throws BookingException {
        try {
            return driverDAO.getAndAssignAvailableDriver(driverId);
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching available drivers", e);
        }
    }

    public boolean createAssignment(Assignment bookingAssignment)throws BookingException  {
        try {
            return bookingAssignmentDAO.insertBookingAssignment(bookingAssignment);
        }
        catch (Exception e) {
            throw new BookingException("Error while creating assignment", e);
        }
    }

    public void assignVehicle(int selectedVehicleId) throws BookingException {
        try {
            vehicleDAO.assignVehicle(selectedVehicleId);
        }
        catch (Exception e) {
            throw new BookingException("Error while assigning vehicle", e);
        }
    }

    public User getCustomerById(Integer customerId) throws BookingException {
        try {
            return userDAO.getUserById(customerId);
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching customer details", e);
        }
    }

    public boolean processPayment(Payment payment) throws BookingException {
        try {
            return paymentDAO.processPayment(payment);
        }
        catch (Exception e) {
            throw new BookingException("Error while processing payment", e);
        }
    }

    public Assignment getAssignmentByBookingId(int bookingId) throws BookingException {
        try {
            return bookingAssignmentDAO.getAssignmentByBookingId(bookingId);
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching assignment details", e);
        }
    }

    public User getDriverById(int driverId) throws BookingException {
        try {
            return userDAO.getUserById(driverDAO.getDriverUserIdById(driverId));
        }
        catch (Exception e) {
            throw new BookingException("Error while fetching driver details", e);
        }
    }

    public void releaseVehicle(int vehicleId) throws BookingException {
        try {
            vehicleDAO.releaseVehicle(vehicleId);
        }
        catch (Exception e) {
            throw new BookingException("Error while releasing vehicle", e);
        }
    }

    public void releaseDriver(int driverId) throws BookingException {
        try {
            driverDAO.releaseDriver(driverId);
        }
        catch (Exception e) {
            throw new BookingException("Error while releasing driver", e);
        }
    }
}
