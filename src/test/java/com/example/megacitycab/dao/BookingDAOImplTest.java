package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Booking;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingDAOImplTest {

    private static BookingDAOImpl bookingDAO;
    private static int testBookingId;

    @BeforeAll
    static void setup() {
        bookingDAO = new BookingDAOImpl();
    }

    @Test
    @Order(1)
    void testAddBooking() {
        Booking booking = new Booking();
        booking.setOrderNumber("TEST12345");
        booking.setCustomerId(22);
        booking.setDestinationDetails("Test Destination");
        booking.setPickupLocation("Test Pickup");
        booking.setBookingDate(LocalDateTime.now());
        booking.setTotalAmount(50.0);

        Booking savedBooking = bookingDAO.addBooking(booking);
        assertNotNull(savedBooking, "Booking should be saved successfully");
        assertTrue(savedBooking.getId() > 0, "Booking ID should be generated");

        testBookingId = savedBooking.getId(); // Save the ID for further testing
    }

    @Test
    @Order(2)
    void testGetBookingById() {
        Booking booking = bookingDAO.getBookingById(testBookingId);
        assertNotNull(booking, "Booking should exist");
        assertEquals("TEST12345", booking.getOrderNumber(), "Order number should match");
    }

    @Test
    @Order(3)
    void testGetAllBookings() {
        List<Booking> bookings = bookingDAO.getAllBookings();
        assertFalse(bookings.isEmpty(), "Bookings list should not be empty");
    }

    @Test
    @Order(4)
    void testUpdateBooking() {
        Booking booking = bookingDAO.getBookingById(testBookingId);
        assertNotNull(booking, "Booking should exist");

        booking.setTotalAmount(100.0);
        boolean updated = bookingDAO.updateBooking(booking);
        assertTrue(updated, "Booking should be updated successfully");

        Booking updatedBooking = bookingDAO.getBookingById(testBookingId);
        assertEquals(100.0, updatedBooking.getTotalAmount(), "Total amount should be updated");
    }

    @Test
    @Order(5)
    void testDeleteBooking() {
        boolean deleted = bookingDAO.deleteBooking(testBookingId);
        assertTrue(deleted, "Booking should be deleted");

        Booking booking = bookingDAO.getBookingById(testBookingId);
        assertNull(booking, "Deleted booking should not exist");
    }

    @AfterAll
    static void cleanup() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM bookings WHERE order_number = 'TEST12345'")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
