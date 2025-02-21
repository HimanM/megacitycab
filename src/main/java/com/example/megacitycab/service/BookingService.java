package com.example.megacitycab.service;

import com.example.megacitycab.dao.Interfaces.BookingDAO;
import com.example.megacitycab.dao.BookingDAOImpl;
import com.example.megacitycab.exceptions.BookingException;
import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.combined.BookingDetails;
import com.example.megacitycab.model.combined.RidePayment;

import java.util.List;
import java.util.Random;

public class BookingService {
    private final BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAOImpl(); // Default implementation
    }

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO; // Allow dependency injection for testing or customization
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
    public boolean hasActiveBooking(int customerId){
        return bookingDAO.hasActiveBooking(customerId);
    }

    public void cancelBooking(int bookingId) {
            bookingDAO.updateBookingStatus(bookingId, "Cancelled");
    }

    public List<Booking> getBookingsByCustomer(int customerId) {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }

    public List<Booking> getActiveBookings(int customerId) {
        return bookingDAO.getBookingsByStatus(customerId, "PENDING", "Approved");
    }

    public List<Booking> getPreviousBookings(int customerId) {
        return bookingDAO.getBookingsByStatus(customerId, "Completed");
    }

    public List<Booking> getCancelledBookings(int customerId) {
        return bookingDAO.getBookingsByStatus(customerId, "Cancelled");
    }

    public List<Booking> getAssignedBookings(Integer driverId) {
        return bookingDAO.getBookingsByDriverId(driverId);
    }

    public boolean acceptBooking(int bookingId, int driverId) {
        return bookingDAO.acceptBooking(bookingId, driverId);
    }

    public BookingDetails getAllDetails(Integer bookingId) {
        return bookingDAO.getAllDetails(bookingId);
    }

    public void completeBooking(int bookingId) {
        bookingDAO.completeBooking(bookingId);
    }
}
