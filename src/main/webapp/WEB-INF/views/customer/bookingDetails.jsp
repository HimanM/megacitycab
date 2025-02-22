<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.megacitycab.model.Booking" %>
<%@ include file="../shared/customer_navbar.jsp" %>

<%
  Booking booking = (Booking) request.getAttribute("booking");
  String driverName = (String) request.getAttribute("driverName");
  String driverPhone = (String) request.getAttribute("driverPhone");
  String vehicleDetails = (String) request.getAttribute("vehicle");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Booking Details</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2>Booking Details</h2>
  <table class="table">
    <tr><th>Order #</th><td><%= booking.getOrderNumber() %></td></tr>
    <tr><th>Destination</th><td><%= booking.getDestinationDetails() %></td></tr>
    <tr><th>Booking Date</th><td><%= booking.getBookingDate() %></td></tr>
    <tr><th>Total Amount</th><td>$<%= booking.getTotalAmount() %></td></tr>
    <tr><th>Status</th><td><%= booking.getStatus() %></td></tr>
    <tr><th>Driver</th><td><%= driverName %></td></tr>
    <tr><th>Driver Contact No</th><td><%= driverPhone %></td></tr>
    <tr><th>Vehicle</th><td><%= vehicleDetails %></td></tr>
  </table>
  <a href="<%= request.getContextPath() %>/customer/booking/viewBookings" class="btn btn-primary">Back to Bookings</a>
</div>
</body>
</html>
