<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.megacitycab.model.combined.Ride" %>
<%@ include file="../shared/admin_navbar.jsp" %>

<%
    Ride trip = (Ride) request.getAttribute("trip");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trip Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-4">
    <h2>Trip Details</h2>

    <% if (trip != null) { %>
    <table class="table">
        <tr><th>Trip ID:</th><td><%= trip.getAssignmentId() %></td></tr>
        <tr><th>Driver ID:</th><td><%= trip.getDriverId() %></td></tr>
        <tr><th>Passenger ID:</th><td><%= trip.getCustomerId() %></td></tr>
        <tr><th>Pickup Location:</th><td><%= trip.getPickupLocation() %></td></tr>
        <tr><th>Dropoff Location:</th><td><%= trip.getDestinationDetails() %></td></tr>
        <tr><th>Status:</th><td><%= trip.getStatus() %></td></tr>
        <tr><th>Fare:</th><td>$<%= trip.getTotalAmount() %></td></tr>
    </table>
    <% } else { %>
    <p class="alert alert-danger">No trip found for this driver.</p>
    <% } %>

    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">Back to Drivers</a>
</div>

</body>
</html>
