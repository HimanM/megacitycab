package com.example.megacitycab.dao;
import com.example.megacitycab.model.Booking;

import java.util.List;

public interface BookingDAO {
    void addBooking(Booking booking); // Create
    Booking getBookingById(int id); // Read
    List<Booking> getAllBookings(); // Read all
    List<Booking> getBookingsByCustomerId(int customerId); // Read by customer
    void updateBooking(Booking booking); // Update
    void deleteBooking(int id); // Delete
}