import com.example.megacitycab.dao.BookingDAO;
import com.example.megacitycab.dao.BookingDAOImpl;
import com.example.megacitycab.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDAOTest {
    public static void main(String[] args) {
        BookingDAO bookingDAO = new BookingDAOImpl();

        // 1. Test Add Booking
        System.out.println("Adding a new booking...");
        Booking newBooking = new Booking(0, "ORD003", 2, "123 Main St to 456 Elm St", LocalDateTime.now(), 2500.00);
        bookingDAO.addBooking(newBooking);
        System.out.println("Booking added successfully!");

        // 2. Test Get Booking by ID
        System.out.println("Fetching booking with ID 1...");
        Booking fetchedBooking = bookingDAO.getBookingById(1);
        if (fetchedBooking != null) {
            System.out.println("Booking Details: ");
            System.out.println("Order Number: " + fetchedBooking.getOrderNumber());
            System.out.println("Customer ID: " + fetchedBooking.getCustomerId());
            System.out.println("Destination: " + fetchedBooking.getDestinationDetails());
            System.out.println("Booking Date: " + fetchedBooking.getBookingDate());
            System.out.println("Total Amount: " + fetchedBooking.getTotalAmount());
        } else {
            System.out.println("Booking not found!");
        }

        // 3. Test Get All Bookings
        System.out.println("Fetching all bookings...");
        List<Booking> allBookings = bookingDAO.getAllBookings();
        for (Booking booking : allBookings) {
            System.out.println("Booking ID: " + booking.getId() + ", Order Number: " + booking.getOrderNumber());
        }

        // 4. Test Get Bookings by Customer ID
        System.out.println("Fetching bookings for customer ID 2...");
        List<Booking> customerBookings = bookingDAO.getBookingsByCustomerId(2);
        for (Booking booking : customerBookings) {
            System.out.println("Order Number: " + booking.getOrderNumber() + ", Destination: " + booking.getDestinationDetails());
        }

        // 5. Test Update Booking
        System.out.println("Updating booking with ID 1...");
        if (fetchedBooking != null) {
            fetchedBooking.setDestinationDetails("Updated destination details!");
            fetchedBooking.setTotalAmount(3000.00);
            bookingDAO.updateBooking(fetchedBooking);
            System.out.println("Booking updated successfully!");
        } else {
            System.out.println("Booking not found for update!");
        }

        // 6. Test Delete Booking
        System.out.println("Deleting booking with ID 1...");
        bookingDAO.deleteBooking(1);
        System.out.println("Booking deleted successfully!");

        // Re-fetch bookings to confirm deletion
        System.out.println("Fetching all bookings after deletion...");
        allBookings = bookingDAO.getAllBookings();
        for (Booking booking : allBookings) {
            System.out.println("Booking ID: " + booking.getId() + ", Order Number: " + booking.getOrderNumber());
        }
    }
}
