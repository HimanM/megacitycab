<%@ page import="com.example.megacitycab.model.Booking" %>
<%@ page import="com.example.megacitycab.DTO.BookingDetails" %>
<%@ page import="com.example.megacitycab.model.Vehicle" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../shared/driver_navbar.jsp" %>


<%
  BookingDetails bookingDetails = (BookingDetails) request.getAttribute("rideDetails");
  Booking booking = bookingDetails.getBooking();
  Vehicle vehicle = bookingDetails.getVehicle();
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Ride Details</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
  <h2 class="mb-4">Ride Details</h2>

  <!-- Booking Details -->
  <div class="card mb-3">
    <div class="card-header bg-primary text-white">Booking Information</div>
    <div class="card-body">
      <p><strong>Booking ID:</strong> <%= booking.getId() %></p>
      <p><strong>Pickup Location:</strong> <%= booking.getPickupLocation() %></p>
      <p><strong>Drop-off Location:</strong> <%= booking.getDestinationDetails() %></p>
      <p><strong>Status:</strong> <%= booking.getStatus() %></p>
      <p><strong>Price:</strong> $<%= booking.getTotalAmount() %></p>
      <p><strong>Created At:</strong> <%= booking.getBookingDate() %></p>
    </div>
  </div>

  <!-- Vehicle Details -->
  <div class="card mb-3">
    <div class="card-header bg-secondary text-white">Vehicle Information</div>
    <div class="card-body">
      <p><strong>Vehicle Model:</strong> <%= vehicle.getModel() %></p>
      <p><strong>License Plate:</strong> <%= vehicle.getLicensePlate() %></p>
      <p><strong>Capacity:</strong> <%= vehicle.getCapacity() %> passengers</p>
      <p><strong>Vehicle Manufacturer:</strong> <%= vehicle.getManufacturer() %></p>
    </div>
  </div>

  <!-- Action Buttons -->
  <div class="text-center">
    <a href="${pageContext.request.contextPath}/driver/dashboard/finish?bookingId=<%= bookingDetails.getBooking().getId() %>" class="btn btn-success">Finish Ride</a>
    <a href="${pageContext.request.contextPath}/driver/dashboard/cancel?bookingId=<%= bookingDetails.getBooking().getId() %>" class="btn btn-danger">Cancel Ride</a>
  </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
