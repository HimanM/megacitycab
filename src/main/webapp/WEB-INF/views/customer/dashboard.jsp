<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../shared/customer_navbar.jsp" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <title>Customer Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h1>Welcome, ${sessionScope.username}!</h1>
</div>

<div class="container mt-4">
    <h2 class="text-center">Dashboard</h2>
    <div class="row mt-5">
        <div class="col-6 col-md-4">
            <a href="${pageContext.request.contextPath}/user/dashboard" class="card text-center text-decoration-none">
                <div class="card-body">
                    <h5 class="card-title">Dashboard</h5>
                    <p class="card-text">View your dashboard</p>
                </div>
            </a>
        </div>
        <div class="col-6 col-md-4">
            <a href="${pageContext.request.contextPath}/customer/booking/placeBooking" class="card text-center text-decoration-none">
                <div class="card-body">
                    <h5 class="card-title">Request A Ride</h5>
                    <p class="card-text">Request a ride with a driver for your own location</p>
                </div>
            </a>
        </div>
        <div class="col-6 col-md-4">
            <a href="${pageContext.request.contextPath}/customer/booking/viewBookings" class="card text-center text-decoration-none">
                <div class="card-body">
                    <h5 class="card-title">View Bookings</h5>
                    <p class="card-text">View your past and current bookings</p>
                </div>
            </a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
