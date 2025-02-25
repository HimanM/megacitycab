package com.example.megacitycab.service;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.BookingDAOImpl;
import com.example.megacitycab.model.Booking;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceTest {
    private static Connection connection;
    private static BookingDAOImpl bookingDAO;
    private static int testBookingId;

    @BeforeAll
    static void setup() {
        bookingDAO = new BookingDAOImpl();

        Booking testBooking =  new Booking(0,"ORD-TEST",22, "Destination Details","Pickup Location", LocalDateTime.now(),500.00);
        testBookingId = bookingDAO.addBooking(testBooking).getId();
        assertNotEquals(0, testBookingId, "Booking should be created successfully.");
    }

    @Test
    @Order(1)
    void testGetBooking_ValidId() {
        Booking booking = bookingDAO.getBookingById(testBookingId);
        assertNotNull(booking);
        assertEquals(testBookingId, booking.getId());
    }

    @Test
    @Order(2)
    void testGetBooking_InvalidId() {
        Booking booking = bookingDAO.getBookingById(99999); // Non-existent ID
        assertNull(booking);
    }

    @Test
    @Order(3)
    void testUpdateBookingStatus() throws InterruptedException {
        boolean updated = bookingDAO.updateBookingStatus(testBookingId, "COMPLETED");
        assertTrue(updated);

        Booking updatedBooking = bookingDAO.getBookingById(testBookingId);
        assertEquals("COMPLETED", updatedBooking.getStatus());
    }

    @Test
    @Order(4)
    void testDeleteBooking() {
        boolean deleted = bookingDAO.deleteBooking(testBookingId);
        assertTrue(deleted);

        Booking deletedBooking = bookingDAO.getBookingById(testBookingId);
        assertNull(deletedBooking);
    }

    @AfterAll
    static void cleanupDatabase() throws SQLException {
        connection = DatabaseConnection.getConnection();
        String deleteBooking = "DELETE FROM bookings WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteBooking)) {
            preparedStatement.setInt(1, testBookingId);
            preparedStatement.executeUpdate();
        }
        connection.close();
    }
}
