package com.example.megacitycab.dao.Interfaces;
import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.DTO.BookingDetails;

import java.util.List;

public interface BookingDAO {
    Booking addBooking(Booking booking); // Create
    Booking getBookingById(int id); // Read
    List<Booking> getAllBookings(); // Read all
    List<Booking> getBookingsByCustomerId(int customerId); // Read by customer
    boolean updateBooking(Booking booking); // Update
    boolean deleteBooking(int id); // Delete
    List<Booking> getPendingBookings();
    boolean assignDriverToBooking(int bookingId, int driverId);
    boolean hasActiveBooking(int customerId);
    boolean updateBookingStatus(int bookingId, String status);
    List<Booking> getBookingsByStatus(int customerId,  String... statuses);
    List<Booking> getBookingsByDriverId(Integer driverId);
    boolean acceptBooking(int bookingId, int driverId);
    BookingDetails getAllDetails(Integer bookingId);
    void completeBooking(int bookingId);
}