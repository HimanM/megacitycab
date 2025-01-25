package com.example.megacitycab.model;
import java.time.LocalDateTime;

public class Booking {
    private int id; // Primary Key
    private String orderNumber;
    private int customerId;
    private String destinationDetails;
    private LocalDateTime bookingDate;
    private double totalAmount;

    // Constructors
    public Booking() {}

    public Booking(int id, String orderNumber, int customerId, String destinationDetails, LocalDateTime bookingDate, double totalAmount) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.destinationDetails = destinationDetails;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDestinationDetails() {
        return destinationDetails;
    }

    public void setDestinationDetails(String destinationDetails) {
        this.destinationDetails = destinationDetails;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}