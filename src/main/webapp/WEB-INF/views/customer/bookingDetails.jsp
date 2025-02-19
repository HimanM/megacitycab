<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.megacitycab.model.Booking" %>

<%
  Booking booking = (Booking) request.getAttribute("booking");
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
  </table>
  <a href="<%= request.getContextPath() %>/customer/booking/viewBookings" class="btn btn-primary">Back to Bookings</a>
</div>
</body>
</html>
