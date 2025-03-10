package com.example.megacitycab.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Booking {
    private int id; // Primary Key
    private String orderNumber;
    private int customerId;
    private String customerName;
    private String destinationDetails;
    private String pickupLocation;
    private LocalDateTime bookingDate;
    private double totalAmount;
    private String status;

    // Constructors
    public Booking() {}

    public Booking(int id, String orderNumber, int customerId, String destinationDetails, String pickupLocation, LocalDateTime bookingDate, double totalAmount) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.destinationDetails = destinationDetails;
        this.pickupLocation = pickupLocation;
        this.bookingDate =bookingDate;
        this.totalAmount = totalAmount;
        this.customerName = "";
        this.status = "PENDING";
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerName() {
        return customerName;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
}