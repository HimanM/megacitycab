<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Driver Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-success">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Driver Dashboard</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link text-danger" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h1>Welcome, ${sessionScope.username}!</h1>
    <p>Here are your assigned bookings:</p>

    <c:choose>
        <c:when test="${not empty assignedBookings}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer</th>
                    <th>Destination</th>
                    <th>Booking Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="booking" items="${assignedBookings}">
                    <tr>
                        <td>${booking.id}</td>
                        <td>${booking.customerName}</td>
                        <td>${booking.destinationDetails}</td>
                        <td>${booking.bookingDate}</td>
                        <td>${booking.status}</td>
                        <td>
                            <c:if test="${booking.status eq 'PENDING'}">
                                <form action="${pageContext.request.contextPath}/driver/booking/accept" method="post" class="d-inline">
                                    <input type="hidden" name="bookingId" value="${booking.id}">
                                    <button type="submit" class="btn btn-success btn-sm">Accept</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/driver/booking/cancel" method="post" class="d-inline">
                                    <input type="hidden" name="bookingId" value="${booking.id}">
                                    <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="text-muted">No assigned bookings at the moment.</p>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
