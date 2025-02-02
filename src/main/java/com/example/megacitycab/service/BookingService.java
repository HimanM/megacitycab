package com.example.megacitycab.service;

import com.example.megacitycab.dao.BookingDAO;
import com.example.megacitycab.dao.BookingDAOImpl;
import com.example.megacitycab.exceptions.BookingException;
import com.example.megacitycab.model.Booking;

import java.util.List;

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

    // Cancel a booking
    public boolean cancelBooking(int bookingId) throws BookingException {
        try {
            if (!bookingDAO.deleteBooking(bookingId)) {
                throw new BookingException("Booking with ID " + bookingId + " could not be canceled or does not exist");
            }
            return true;
        } catch (Exception e) {
            throw new BookingException("Error while canceling booking", e);
        }
    }

    public List<Booking> getBookingsByCustomer(int customerId) {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }
}
