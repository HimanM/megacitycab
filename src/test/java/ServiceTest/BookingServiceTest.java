package ServiceTest;

import com.example.megacitycab.dao.BookingDAO;
import com.example.megacitycab.exceptions.BookingException;
import com.example.megacitycab.model.Booking;
import com.example.megacitycab.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {
    private BookingService bookingService;
    private BookingDAO bookingDAOMock;

    @BeforeEach
    void setUp() {
        bookingDAOMock = mock(BookingDAO.class);
        bookingService = new BookingService(bookingDAOMock);
    }

    @Test
    void createBooking_Success() throws BookingException {
        Booking booking = new Booking(1, "2", 2,3, "Colombo",parseDateTime("2025-01-30 10:00:00"), 2000.00);

        doNothing().when(bookingDAOMock).addBooking(booking);

        assertDoesNotThrow(() -> bookingService.createBooking(booking));
        verify(bookingDAOMock, times(1)).addBooking(booking);
    }

    @Test
    void createBooking_NullBooking_ThrowsException() {
        assertThrows(BookingException.class, () -> bookingService.createBooking(null));
        verify(bookingDAOMock, never()).addBooking(any(Booking.class));
    }

    @Test
    void updateBooking_Success() throws BookingException {
        Booking updatedBooking = new Booking(6, "2", 3,2, "Jaffna",parseDateTime("2025-01-30 10:00:00"), 2000.00);

        doNothing().when(bookingDAOMock).updateBooking(updatedBooking);

        assertDoesNotThrow(() -> bookingService.updateBooking(updatedBooking));
        verify(bookingDAOMock, times(1)).updateBooking(updatedBooking);
    }

    @Test
    void updateBooking_NullBooking_ThrowsException() {
        assertThrows(BookingException.class, () -> bookingService.updateBooking(null));
        verify(bookingDAOMock, never()).updateBooking(any(Booking.class));
    }

    @Test
    void getBookingById_Success() throws BookingException {
        Booking booking = new Booking(2, "2", 3,2, "Jaffna",parseDateTime("2025-01-30 10:00:00"), 2000.00);

        when(bookingDAOMock.getBookingById(2)).thenReturn(booking);

        Booking fetchedBooking = bookingService.getBookingById(2);

        assertNotNull(fetchedBooking);
        assertEquals(2, fetchedBooking.getId());
        verify(bookingDAOMock, times(1)).getBookingById(2);
    }

    @Test
    void getBookingById_NotFound_ThrowsException() {
        when(bookingDAOMock.getBookingById(1)).thenReturn(null);

        BookingException exception = assertThrows(BookingException.class, () -> bookingService.getBookingById(1));
        assertEquals("Error while fetching booking details", exception.getMessage());
        verify(bookingDAOMock, times(1)).getBookingById(1);
    }

    @Test
    void getBookingsByCustomerId_Success() throws BookingException {
        List<Booking> bookings = Arrays.asList(
                new Booking(4, "2", 3,2, "Jaffna",parseDateTime("2025-01-30 10:00:00"), 2000.00),
                new Booking(5, "2", 3,2, "Jaffna",parseDateTime("2025-01-30 10:00:00"), 2000.00)
        );

        when(bookingDAOMock.getBookingsByCustomerId(2)).thenReturn(bookings);

        List<Booking> fetchedBookings = bookingService.getBookingsByCustomerId(2);

        assertNotNull(fetchedBookings);
        assertEquals(2, fetchedBookings.size());
        verify(bookingDAOMock, times(1)).getBookingsByCustomerId(2);
    }

    @Test
    void cancelBooking_Success() throws BookingException {
        doNothing().when(bookingDAOMock).deleteBooking(2);

        boolean result = bookingService.cancelBooking(2);

        assertTrue(result);
        verify(bookingDAOMock, times(1)).deleteBooking(2);
    }

    @Test
    void cancelBooking_Failure_ThrowsException() {
        doThrow(new RuntimeException()).when(bookingDAOMock).deleteBooking(1);

        assertThrows(BookingException.class, () -> bookingService.cancelBooking(1));
        verify(bookingDAOMock, times(1)).deleteBooking(1);
    }

    private LocalDateTime parseDateTime(String bookingDateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return LocalDateTime.parse(bookingDateTimeString, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date and time: " + e.getMessage());
            return null;
        }
    }
}
