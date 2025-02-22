<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../shared/driver_navbar.jsp" %>
<%
    Boolean finishRide = (Boolean) request.getAttribute("rideComplete");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Driver Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function showFinishRideMessage() {
            let modal = new bootstrap.Modal(document.getElementById("finishRideModel"));
            modal.show();
        }

        <% if (finishRide != null && finishRide) { %>
        window.onload = function() { showFinishRideMessage(); };
        <% } %>
    </script>
</head>
<body>

<!-- Ride Finished Modal -->
<div class="modal fade" id="finishRideModel" tabindex="-1" aria-labelledby="finishRideModelLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="finishRideModelLabel">Ride Finished</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>The ride is now Complete</p>
            </div>
            <div class="modal-footer">
                <a href="<%= request.getContextPath() %>/driver/dashboard/" class="btn btn-primary">Go to Dashboard</a>
            </div>
        </div>
    </div>
</div>

<div class="container mt-4">
    <h1>Welcome, ${sessionScope.username}!</h1>

    <!-- Active Rides Table -->
    <h3>Active Rides</h3>
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
                    <c:if test="${booking.status ne 'COMPLETED'}">
                        <tr>
                            <td>${booking.id}</td>
                            <td>${booking.customerName}</td>
                            <td>${booking.destinationDetails}</td>
                            <td>${booking.bookingDate}</td>
                            <td>${booking.status}</td>
                            <td>
                                <c:if test="${booking.status eq 'PENDING'}">
                                    <form action="${pageContext.request.contextPath}/driver/dashboard/accept" method="post" class="d-inline">
                                        <input type="hidden" name="bookingId" value="${booking.id}">
                                        <button type="submit" class="btn btn-success btn-sm">Accept</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/driver/dashboard/cancel" method="post" class="d-inline">
                                        <input type="hidden" name="bookingId" value="${booking.id}">
                                        <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                                    </form>
                                </c:if>
                                <c:if test="${booking.status eq 'APPROVED'}">
                                    <form action="${pageContext.request.contextPath}/driver/dashboard/details" method="post" class="d-inline">
                                        <input type="hidden" name="bookingId" value="${booking.id}">
                                        <button type="submit" class="btn btn-success btn-sm">View Details</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="text-muted">No active bookings at the moment.</p>
        </c:otherwise>
    </c:choose>

    <!-- Completed Rides Table -->
    <h3 class="mt-5">Completed Rides</h3>
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
                </tr>
                </thead>
                <tbody>
                <c:forEach var="booking" items="${assignedBookings}">
                    <c:if test="${booking.status eq 'COMPLETED'}">
                        <tr>
                            <td>${booking.id}</td>
                            <td>${booking.customerName}</td>
                            <td>${booking.destinationDetails}</td>
                            <td>${booking.bookingDate}</td>
                            <td class="text-success fw-bold">${booking.status}</td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="text-muted">No completed rides yet.</p>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
