package com.example.megacitycab.service;

import com.example.megacitycab.dao.Interfaces.PaymentDAO;
import com.example.megacitycab.dao.PaymentDAOImpl;
import com.example.megacitycab.model.Payment;

import java.util.List;

public class PaymentService {

    private final PaymentDAO paymentDAO = new PaymentDAOImpl();

    public boolean processPayment(Payment payment) {
        return paymentDAO.processPayment(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }
}
