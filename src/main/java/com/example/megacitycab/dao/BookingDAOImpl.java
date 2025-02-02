package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Booking;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {

    private static final String INSERT_BOOKING = "INSERT INTO bookings (order_number, customer_id, destination_details, booking_date, total_amount, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BOOKING_BY_ID = "SELECT * FROM bookings WHERE id = ?";
    private static final String SELECT_ALL_BOOKINGS = "SELECT * FROM bookings";
    private static final String SELECT_BOOKINGS_BY_CUSTOMER_ID = "SELECT * FROM bookings WHERE customer_id = ?";
    private static final String SELECT_PENDING_BOOKINGS = "SELECT * FROM bookings WHERE status = 'PENDING'";
    private static final String UPDATE_BOOKING = "UPDATE bookings SET order_number = ?, customer_id = ?, destination_details = ?, booking_date = ?, total_amount = ?, status = ? WHERE id = ?";
    private static final String DELETE_BOOKING = "DELETE FROM bookings WHERE id = ?";
    private static final String UPDATE_BOOKING_DRIVER = "UPDATE bookings SET driver_id = ?, status = 'ACCEPTED' WHERE id = ?";

    @Override
    public void addBooking(Booking booking) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOKING, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, booking.getOrderNumber());
            statement.setInt(2, booking.getCustomerId());
            statement.setString(3, booking.getDestinationDetails());
            statement.setTimestamp(4, Timestamp.valueOf(booking.getBookingDate()));
            statement.setDouble(5, booking.getTotalAmount());
            statement.setString(6, "PENDING");

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

    private Booking mapRowToBooking(ResultSet resultSet) throws SQLException {
        return new Booking(
                resultSet.getInt("id"),
                resultSet.getString("order_number"),
                resultSet.getInt("customer_id"),
                resultSet.getInt("driver_id"),
                resultSet.getString("destination_details"),
                resultSet.getTimestamp("booking_date").toLocalDateTime(),
                resultSet.getDouble("total_amount")
        );
    }
}
