package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.dao.Interfaces.BookingDAO;
import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.combined.BookingDetails;
import com.example.megacitycab.model.Vehicle;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {

    private static final String INSERT_BOOKING = "INSERT INTO bookings (order_number, customer_id, destination_details, pickup_location, booking_date, total_amount, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BOOKING_BY_ID = "SELECT * FROM bookings WHERE id = ?";
    private static final String SELECT_ALL_BOOKINGS = "SELECT * FROM bookings";
    private static final String SELECT_BOOKINGS_BY_CUSTOMER_ID = "SELECT * FROM bookings WHERE customer_id = ?";
    private static final String SELECT_PENDING_BOOKINGS = "SELECT * FROM bookings WHERE status = 'PENDING'";
    private static final String UPDATE_BOOKING = "UPDATE bookings SET order_number = ?, customer_id = ?, destination_details = ?, booking_date = ?, total_amount = ?, status = ? WHERE id = ?";
    private static final String DELETE_BOOKING = "DELETE FROM bookings WHERE id = ?";
    private static final String UPDATE_BOOKING_DRIVER = "UPDATE bookings SET driver_id = ?, status = 'ACCEPTED' WHERE id = ?";
    private static final String UPDATE_BOOKING_STATUS ="UPDATE bookings SET status = ? WHERE id = ?";

    @Override
    public void addBooking(Booking booking) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOKING, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, booking.getOrderNumber());
            statement.setInt(2, booking.getCustomerId());
            statement.setString(3, booking.getDestinationDetails());
            statement.setString(4, booking.getPickupLocation());
            statement.setTimestamp(5, Timestamp.valueOf(booking.getBookingDate()));
            statement.setDouble(6, booking.getTotalAmount());
            statement.setString(7, "PENDING");

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                booking.setId(generatedKeys.getInt(1));  // Set the generated ID
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Booking getBookingById(int id) {
        Booking booking = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOKING_BY_ID)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                booking = mapRowToBooking(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKINGS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                bookings.add(mapRowToBooking(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByCustomerId(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOKINGS_BY_CUSTOMER_ID)) {

            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(mapRowToBooking(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> getPendingBookings() {
        List<Booking> pendingBookings = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PENDING_BOOKINGS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                pendingBookings.add(mapRowToBooking(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingBookings;
    }

    @Override
    public boolean updateBooking(Booking booking) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOKING)) {

            statement.setString(1, booking.getOrderNumber());
            statement.setInt(2, booking.getCustomerId());
            statement.setString(3, booking.getDestinationDetails());
            statement.setTimestamp(4, Timestamp.valueOf(booking.getBookingDate()));
            statement.setDouble(5, booking.getTotalAmount());
            statement.setString(6, booking.getStatus());
            statement.setInt(7, booking.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBooking(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOKING)) {

            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean assignDriverToBooking(int bookingId, int driverId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOKING_DRIVER)) {

            statement.setInt(1, driverId);
            statement.setInt(2, bookingId);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBookingStatus(int bookingId, String status) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOKING_STATUS)) {

            statement.setString(1, status);
            statement.setInt(2, bookingId);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> getBookingsByStatus(int customerId, String... statuses) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE customer_id = ? AND status IN (" +
                String.join(",", "?".repeat(statuses.length).split("")) + ") ORDER BY booking_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            for (int i = 0; i < statuses.length; i++) {
                stmt.setString(i + 2, statuses[i]);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setOrderNumber(rs.getString("order_number"));
                booking.setDestinationDetails(rs.getString("destination_details"));
                booking.setBookingDate(rs.getTimestamp("booking_date").toLocalDateTime());
                booking.setTotalAmount(rs.getDouble("total_amount"));
                booking.setStatus(rs.getString("status"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByDriverId(Integer driverId) {
        List<Booking> bookings = new ArrayList<>();

        String QUERY = "SELECT b.id, u.name AS customer_name, b.destination_details, b.booking_date, b.status " +
                "FROM bookings b " +
                "JOIN users u ON b.customer_id = u.id " +
                "JOIN booking_assignments ba ON b.id = ba.booking_id " +
                "WHERE ba.driver_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY)) {

            statement.setInt(1, driverId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setCustomerName(resultSet.getString("customer_name"));
                booking.setDestinationDetails(resultSet.getString("destination_details"));
                booking.setBookingDate(resultSet.getTimestamp("booking_date").toLocalDateTime());
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    @Override
    public boolean acceptBooking(int bookingId, int driverId) {
        String UPDATE_QUERY = "UPDATE bookings " +
                "SET status = 'APPROVED' " +
                "WHERE id = ? " +
                "AND EXISTS ( " +
                "    SELECT 1 FROM booking_assignments " +
                "    WHERE booking_assignments.booking_id = bookings.id " +
                "    AND booking_assignments.driver_id = ? " +
                ")";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setInt(1, bookingId);
            statement.setInt(2, driverId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public BookingDetails getAllDetails(Integer bookingId) {
        String BOOKING_QUERY = "SELECT * FROM bookings WHERE id = ?";
        String VEHICLE_QUERY = "SELECT v.* FROM vehicles v " +
                "JOIN booking_assignments ba ON v.id = ba.vehicle_id " +
                "WHERE ba.booking_id = ?";

        Booking booking = null;
        Vehicle vehicle = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement bookingStmt = connection.prepareStatement(BOOKING_QUERY);
             PreparedStatement vehicleStmt = connection.prepareStatement(VEHICLE_QUERY)) {

            // Fetch Booking Details
            bookingStmt.setInt(1, bookingId);
            ResultSet bookingRs = bookingStmt.executeQuery();
            if (bookingRs.next()) {
                booking = new Booking();
                booking.setId(bookingRs.getInt("id"));
                booking.setOrderNumber(bookingRs.getString("order_number"));
                booking.setCustomerId(bookingRs.getInt("customer_id"));
                booking.setStatus(bookingRs.getString("status"));
                booking.setPickupLocation(bookingRs.getString("pickup_location"));
                booking.setDestinationDetails(bookingRs.getString("destination_details"));
                booking.setBookingDate(bookingRs.getTimestamp("booking_date").toLocalDateTime());
                booking.setTotalAmount(bookingRs.getDouble("total_amount"));
            }



            vehicleStmt.setInt(1, bookingId);
            ResultSet vehicleRs = vehicleStmt.executeQuery();
            if (vehicleRs.next()) {
                vehicle = new Vehicle();
                vehicle.setId(vehicleRs.getInt("id"));
                vehicle.setModel(vehicleRs.getString("model"));
                vehicle.setLicensePlate(vehicleRs.getString("license_plate"));
                vehicle.setCapacity(vehicleRs.getInt("capacity"));
                vehicle.setManufacturer(vehicleRs.getString("manufacturer"));
            }


            // Return the Combined Object
            if (booking != null && vehicle != null) {
                return new BookingDetails(booking, vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void completeBooking(int bookingId) {
        updateBookingStatus(bookingId, "COMPLETED");
    }


    @Override
    public boolean hasActiveBooking(int customerId) {
        String CHECK_ACTIVE_BOOKING = "SELECT COUNT(*) FROM bookings WHERE customer_id = ? AND status IN ('APPROVED', 'PENDING')";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_ACTIVE_BOOKING)) {

            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true; // User has active bookings
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // No active bookings found
    }

    private Booking mapRowToBooking(ResultSet resultSet) throws SQLException {
        return new Booking(
                resultSet.getInt("id"),
                resultSet.getString("order_number"),
                resultSet.getInt("customer_id"),
                resultSet.getString("destination_details"),
                resultSet.getString("pickup_location"),
                resultSet.getTimestamp("booking_date").toLocalDateTime(),
                resultSet.getDouble("total_amount")
        );
    }
}
