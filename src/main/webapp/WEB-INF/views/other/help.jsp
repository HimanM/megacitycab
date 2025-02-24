<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../shared/message_popup.jsp" %>
<%@ include file="../shared/driver_navbar.jsp" %>
<%@ include file="../shared/admin_navbar.jsp" %>
<%@ include file="../shared/customer_navbar.jsp" %>
<%@ include file="../shared/default_navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Help - Mega City Cab</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Help & Guidelines</h2>
    <div class="card shadow-sm p-4">
        <h4>General Help for New Users</h4>
        <ul>
            <li><strong>Login:</strong> Enter your username and password to access the system.</li>
            <li><strong>Book a Ride:</strong> Provide details like name, address, and destination.</li>
            <li><strong>View Bookings:</strong> Check your ride history.</li>
            <li><strong>Calculate Bill:</strong> The system calculates the total fare with taxes.</li>
            <li><strong>Exit:</strong> Click logout to securely leave the system.</li>
        </ul>

        <% String role = (String) request.getAttribute("role"); %>

        <%-- Help for Customers --%>
        <% if ("Customer".equals(role)) { %>
        <hr>
        <h4>Additional Help for Customers</h4>
        <ul>
            <li><strong>Track Your Ride:</strong> View driver details and estimated arrival time.</li>
            <li><strong>Cancel Bookings:</strong> Cancel rides before pickup.</li>
            <li><strong>Payment Methods:</strong> Pay via credit card, cash, or wallet.</li>
            <li><strong>Support:</strong> Contact customer support for disputes or issues.</li>
        </ul>
        <% } %>

        <%-- Help for Drivers --%>
        <% if ("Driver".equals(role)) { %>
        <hr>
        <h4>Additional Help for Drivers</h4>
        <ul>
            <li><strong>Accept Rides:</strong> View and accept ride requests.</li>
            <li><strong>Navigate:</strong> Use the in-app navigation for the best routes.</li>
            <li><strong>Earnings:</strong> Track your daily and weekly earnings.</li>
            <li><strong>Customer Ratings:</strong> Maintain a high rating for better trip assignments.</li>
        </ul>
        <% } %>

        <%-- Help for Admins --%>
        <% if ("Admin".equals(role)) { %>
        <hr>
        <h4>Additional Help for Admins</h4>
        <ul>
            <li><strong>Manage Users:</strong> Add, remove, or update customer and driver profiles.</li>
            <li><strong>Monitor Rides:</strong> View real-time ride status and reports.</li>
            <li><strong>Billing:</strong> Generate invoices and process payments.</li>
            <li><strong>System Settings:</strong> Configure app settings and operational rules.</li>
        </ul>
        <% } %>

        <p class="text-muted">For further assistance, contact our support team.</p>
    </div>
</div>
</body>
</html>
