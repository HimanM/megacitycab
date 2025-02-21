<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.megacitycab.model.Driver" %>
<%@ include file="../shared/admin_navbar.jsp" %>

<%
    List<Driver> drivers = (List<Driver>) request.getAttribute("driversList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Drivers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-4">
    <h2 class="mb-4">Manage Drivers</h2>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Phone</th>
            <th>License Number</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% if (drivers != null) {
            for (Driver driver : drivers) { %>
        <tr>
            <td><%= driver.getId() %></td>
            <td><%= driver.getName() %></td>
            <td><%= driver.getPhoneNumber() %></td>
            <td><%= driver.getLicenseNumber() %></td>
            <td>
                <% if (driver.getStatus().equals("In Trip")) { %>
                <span class="badge bg-primary">In Trip</span>
                <% } else if (driver.isVerified().equals("Yes")) { %>
                <span class="badge bg-success">Available</span>
                <% } else { %>
                <span class="badge bg-warning">Not Verified</span>
                <% } %>
            </td>
            <td>
                <% if (!driver.isVerified().equals("Yes")) { %>
                <form action="${pageContext.request.contextPath}/admin/dashboard/verifyDriver" method="post" class="d-inline">
                    <input type="hidden" name="verifyDriverId" value="<%= driver.getId() %>">
                    <button type="submit" class="btn btn-success btn-sm">Approve</button>
                </form>
                <% } %>

                <% if (driver.getStatus().equals("On Trip")) { %>
                <form action="${pageContext.request.contextPath}/admin/dashboard/viewTrip" method="post" class="d-inline">
                    <input type="hidden" name="driverId" value="<%= driver.getId() %>">
                    <button type="submit" class="btn btn-info btn-sm">View Trip Details</button>
                </form>
                <% } %>
                <form action="${pageContext.request.contextPath}/admin/dashboard/removeDriver" method="post" class="d-inline">
                    <input type="hidden" name="id" value="<%= driver.getId() %>">
                    <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                </form>

            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="7" class="text-center">No drivers found.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
