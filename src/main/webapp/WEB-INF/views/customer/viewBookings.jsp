<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.megacitycab.model.Booking, java.util.List, java.time.format.DateTimeFormatter" %>

<%
  List<Booking> activeBookings = (List<Booking>) request.getAttribute("activeBookings");
  List<Booking> previousBookings = (List<Booking>) request.getAttribute("previousBookings");
  List<Booking> cancelledBookings = (List<Booking>) request.getAttribute("cancelledBookings");

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Bookings</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script>
    function confirmCancel(bookingId) {
      if (confirm("Are you sure you want to cancel this booking?")) {
        window.location.href = "<%= request.getContextPath() %>/customer/booking/cancel?bookingId=" + bookingId;
      }
    }
  </script>
</head>
<body>

<div class="container mt-5">
  <h2 class="text-center mb-4">My Bookings</h2>

  <!-- Active Bookings -->
  <div class="card mb-4">
    <div class="card-header bg-primary text-white">
      <h4>Active Bookings</h4>
    </div>
    <div class="card-body">
      <% if (activeBookings.isEmpty()) { %>
      <p class="text-center text-muted">No active bookings.</p>
      <% } else { %>
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Order #</th>
          <th>Destination</th>
          <th>Booking Date</th>
          <th>Status</th>
          <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Booking booking : activeBookings) { %>
        <tr>
          <td><%= booking.getOrderNumber() %></td>
          <td><%= booking.getDestinationDetails() %></td>
          <td><%= booking.getBookingDate().format(formatter) %></td>
          <td><%= booking.getStatus() %></td>
          <td>
            <% if ("APPROVED".equals(booking.getStatus())) { %>
            <a href="<%= request.getContextPath() %>/customer/booking/details?bookingId=<%= booking.getId() %>" class="btn btn-info">View Details</a>
            <% } %>
            <% if ("PENDING".equals(booking.getStatus())) { %>
            <button class="btn btn-danger" onclick="confirmCancel(<%= booking.getId() %>)">Cancel Booking</button>
            <% } %>
          </td>
        </tr>
        <% } %>
        </tbody>
      </table>
      <% } %>
    </div>
  </div>

  <!-- Previous Bookings -->
  <div class="card mb-4">
    <div class="card-header bg-success text-white">
      <h4>Previous Bookings</h4>
    </div>
    <div class="card-body">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Order #</th>
          <th>Destination</th>
          <th>Booking Date</th>
        </tr>
        </thead>
        <tbody>
        <% for (Booking booking : previousBookings) { %>
        <tr>
          <td><%= booking.getOrderNumber() %></td>
          <td><%= booking.getDestinationDetails() %></td>
          <td><%= booking.getBookingDate().format(formatter) %></td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Cancelled Bookings -->
  <div class="card">
    <div class="card-header bg-danger text-white">
      <h4>Cancelled Bookings</h4>
    </div>
    <div class="card-body">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Order #</th>
          <th>Destination</th>
          <th>Booking Date</th>
        </tr>
        </thead>
        <tbody>
        <% for (Booking booking : cancelledBookings) { %>
        <tr>
          <td><%= booking.getOrderNumber() %></td>
          <td><%= booking.getDestinationDetails() %></td>
          <td><%= booking.getBookingDate().format(formatter) %></td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>
  </div>
</div>

</body>

</html>
