package com.example.megacitycab.dao;

import com.example.megacitycab.config.DatabaseConnection;
import com.example.megacitycab.model.Booking;
import com.example.megacitycab.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean processPayment(Payment payment) {
        String sql = "INSERT INTO payment (booking_id, customer_id, amount, card_holder_name, card_number, expiry_date, cvv, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getBookingId());
            stmt.setInt(2, payment.getCustomerId());
            stmt.setDouble(3, payment.getAmount());
            stmt.setString(4, payment.getCardHolderName());
            stmt.setString(5, payment.getCardNumber());
            stmt.setString(6, payment.getExpiryDate());
            stmt.setString(7, payment.getCvv());
            stmt.setString(8, "Completed");


            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                payments.add(mapRowToPayment(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }


    private Payment mapRowToPayment(java.sql.ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setBookingId(rs.getInt("booking_id"));
        payment.setCustomerId(rs.getInt("customer_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setCardHolderName(rs.getString("card_holder_name"));
        payment.setCardNumber(rs.getString("card_number"));
        payment.setExpiryDate(rs.getString("expiry_date"));
        payment.setCvv(rs.getString("cvv"));
        payment.setStatus(rs.getString("status"));
        return payment;
    }
}
