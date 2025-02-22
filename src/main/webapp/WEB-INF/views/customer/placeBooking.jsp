<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.megacitycab.model.Booking" %>
<%@ page import="com.example.megacitycab.model.Vehicle" %>
<%@ page import="java.util.List" %>
<%@ include file="../shared/customer_navbar.jsp" %>

<%
    Double fare = (Double) request.getAttribute("fare");
    Double tax = (Double) request.getAttribute("tax");
    Double totalBeforeTax = (Double) request.getAttribute("beforeTax");
    String destination = request.getAttribute("destination") != null ? request.getAttribute("destination").toString() : "";
    String bookingDate = request.getAttribute("bookingDate") != null ? request.getAttribute("bookingDate").toString() : "";
    String pickupLocation = request.getAttribute("pickupLocation") != null ? request.getAttribute("pickupLocation").toString() : "";
    String vehicleId= request.getAttribute("vehicleId") != null ? request.getAttribute("vehicleId").toString() : "";
    String vehicleType = request.getAttribute("vehicleType") != null ? request.getAttribute("vehicleType").toString() : "Choose a vehicle";
    Booking booking = (Booking) request.getAttribute("booking");
    boolean fareCalculated = fare != null;
    Boolean activeBooking = (Boolean) request.getAttribute("activeBooking");
    Boolean noDrivers = (Boolean) request.getAttribute("noDrivers");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Place Booking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }
    </style>
    <script>
        function disableCalculateFare() {
            document.getElementById("calculateFareBtn").disabled = true;
            document.getElementById("placeBookingBtn").style.display = "inline-block";
            document.getElementById("cancelBtn").style.display = "inline-block";
        }
        function showActiveBookingMessage() {
            let modal = new bootstrap.Modal(document.getElementById("activeBookingModal"));
            modal.show();
        }
        function showNoDriversMessage() {
            let modal = new bootstrap.Modal(document.getElementById("noDriverFoundModal"));
            modal.show();
        }

        <% if (activeBooking != null && activeBooking) { %>
        window.onload = function() { showActiveBookingMessage(); };
        <% } %>

        <% if (noDrivers != null && noDrivers) { %>
        window.onload = function() { showNoDriversMessage(); };
        <% } %>
    </script>
</head>
<body>


<!-- Active Booking Alert Modal -->
<div class="modal fade" id="activeBookingModal" tabindex="-1" aria-labelledby="activeBookingModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="activeBookingModalLabel">Active Booking Found</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>You already have an active booking. Please check your dashboard.</p>
            </div>
            <div class="modal-footer">
                <a href="<%= request.getContextPath() %>/customer/booking/viewBookings" class="btn btn-primary">Go to Dashboard</a>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="noDriverFoundModal" tabindex="-1" aria-labelledby="noDriverFoundModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="noDriverFoundModalLabel">No Drivers Available</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>No Drivers Available At the Moment. Please try again later.</p>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <% if (booking == null) { %>
    <h2 class="text-center mb-4">Place a Booking</h2>
    <form action="<%= request.getContextPath() %>/customer/booking/placeBooking" method="post">
        <div class="mb-3">
            <label for="destination" class="form-label">Destination</label>
            <input type="text" class="form-control" id="destination" name="destination" value="<%= destination %>" required>
        </div>

        <div class="mb-3">
            <label for="pickupLocation" class="form-label">Pickup Location</label>
            <input type="text" class="form-control" id="pickupLocation" name="pickupLocation" value="<%= pickupLocation %>" required>
        </div>

        <div class="mb-3">
            <label for="bookingDate" class="form-label">Booking Date & Time</label>
            <input type="datetime-local" class="form-control" id="bookingDate" name="bookingDate" value="<%= bookingDate %>" required>
        </div>

        <div class="mb-3">
            <label for="vehicleId" class="form-label">Select Vehicle</label>
            <select class="form-select" id="vehicleId" name="vehicleId" required>
                <% if (!fareCalculated) { %>
                <option value="" disabled selected><%=vehicleType %></option>
                <%
                    List<Vehicle> vehicles = (List<Vehicle>) request.getAttribute("availableVehicles");
                    if (vehicles != null) {
                        for (Vehicle v : vehicles) {
                            %>
                            <option value="<%= v.getId() %>">
                                <%= v.getManufacturer() %> - <%= v.getModel() %> (Capacity: <%= v.getCapacity() %>)
                            </option>
                            <%
                        }
                    }
                %>
                <% } else { %>
                <option value="<%= vehicleId %>" selected><%=vehicleType %></option>
                <% } %>
            </select>
        </div>



        <% if (fareCalculated) { %>

<%--        Amount Display Container--%>
        <div class="card p-3 shadow-sm">
            <h5 class="card-title text-center mb-3">Fare Breakdown</h5>

            <div class="mb-3">
                <label class="form-label fw-bold">Estimated Fare</label>
                <div class="input-group">
                    <span class="input-group-text">$</span>
                    <input type="text" class="form-control" value="<%= String.format("%.2f", totalBeforeTax) %>" disabled>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Tax (15%)</label>
                <div class="input-group">
                    <span class="input-group-text">$</span>
                    <input type="text" class="form-control" value="<%= String.format("%.2f", tax) %>" disabled>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Estimated Fare After Tax</label>
                <div class="input-group">
                    <span class="input-group-text">$</span>
                    <input type="text" class="form-control text-success fw-bold" value="<%= String.format("%.2f", fare) %>" disabled>
                </div>
            </div>
        </div>
        <%--        Amount Display Container End --%>

        <div class="mb-3">
            <label for="cardNumber" class="form-label">Credit Card Number</label>
            <input type="text" class="form-control" id="cardNumber" name="cardNumber" required pattern="\d{16}" placeholder="Enter 16-digit card number">
        </div>

        <div class="mb-3">
            <label for="expiryDate" class="form-label">Expiration Date</label>
            <input type="month" class="form-control" id="expiryDate" name="expiryDate" required>
        </div>

        <div class="mb-3">
            <label for="cvv" class="form-label">CVV</label>
            <input type="text" class="form-control" id="cvv" name="cvv" required pattern="\d{3}" placeholder="Enter 3-digit CVV">
        </div>

        <% } %>

        <% if (!fareCalculated) { %>
        <button type="submit" name="calculateFare" class="btn btn-secondary">Calculate Fare</button>
        <% } else { %>
        <button type="submit" name="placeBooking" class="btn btn-primary">Place Booking</button>
        <% } %>
    </form>
    <% } %>

    <% if (booking != null) { %>
    <div class="alert mt-4 alert-warning rounded shadow p-4">
        <h4 class="alert-heading text-center fw-bold">Booking Confirmed!</h4>
        <hr>
        <div class="d-flex justify-content-between">
            <div>
                <p class="mb-1"><i class="fas fa-map-marker-alt me-2"></i>Destination:</p>
                <p class="fw-bold"><%= booking.getDestinationDetails() %></p>
            </div>
            <div>
                <p class="mb-1"><i class="fas fa-dollar-sign me-2"></i>Price:</p>
                <p class="fw-bold"><%= String.format("%.2f", booking.getTotalAmount()) %></p>
            </div>
        </div>
        <hr>
        <div class="d-flex justify-content-between align-items-center">
            <div>
                <p class="mb-0"><i class="fas fa-check-circle me-2"></i>Status:</p>
                <p class="fw-bold"><%= booking.getStatus() %></p>
            </div>
            <div>
                <a href="<%= request.getContextPath() %>/customer/booking/viewBookings" class="btn btn-primary">
                    <i class="fas fa-eye me-2"></i>View Booking Dashboard
                </a>
            </div>
        </div>
    </div>
    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
