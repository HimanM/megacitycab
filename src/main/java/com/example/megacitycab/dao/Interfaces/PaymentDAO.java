package com.example.megacitycab.dao.Interfaces;

import com.example.megacitycab.model.Payment;

import java.util.List;

public interface PaymentDAO {
    boolean processPayment(Payment payment);
    List<Payment> getAllPayments();
}
