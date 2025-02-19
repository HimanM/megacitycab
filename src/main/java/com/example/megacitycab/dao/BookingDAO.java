package com.example.megacitycab.dao;
import com.example.megacitycab.model.Booking;

import java.util.List;

public interface BookingDAO {
    void addBooking(Booking booking); // Create
    Booking getBookingById(int id); // Read
    List<Booking> getAllBookings(); // Read all
    List<Booking> getBookingsByCustomerId(int customerId); // Read by customer
    boolean updateBooking(Booking booking); // Update
    boolean deleteBooking(int id); // Delete
    List<Booking> getPendingBookings();
    boolean assignDriverToBooking(int bookingId, int driverId);
    public boolean hasActiveBooking(int customerId);
    boolean updateBookingStatus(int bookingId, String status);
    List<Booking> getBookingsByStatus(int customerId,  String... statuses);
}