<%@ page import="com.example.megacitycab.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../shared/message_popup.jsp" %>
<%@ include file="../shared/driver_navbar.jsp" %>
<%@ include file="../shared/admin_navbar.jsp" %>
<%@ include file="../shared/customer_navbar.jsp" %>

<%
    User loggedInUser = (User) request.getAttribute("loggedInUser");
    String role = (String) request.getAttribute("role");
%>

<%-- Check if the user is logged in --%>
<% if (loggedInUser != null) { %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Profile Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Profile Management</h2>

    <form action="<%= request.getContextPath() + "/profile" %>" method="POST">
        <!-- Email -->
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="<%= loggedInUser.getEmail() %>" required>
        </div>

        <div class="mb-3">
            <label for="nic" class="form-label">NIC</label>
            <input type="text" class="form-control" id="nic" name="nic" value="<%= loggedInUser.getNic() %>" required>
        </div>

        <!-- Phone Number -->
        <div class="mb-3">
            <label for="phoneNumber" class="form-label">Phone Number</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="<%= loggedInUser.getPhone() %>" required>
        </div>

        <!-- Password Change Section -->
        <div class="mb-3">
            <label for="password" class="form-label">New Password</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>

        <div class="mb-3">
            <label for="confirm-password" class="form-label">Confirm New Password</label>
            <input type="password" class="form-control" id="confirm-password" name="confirm-password">
        </div>

        <!-- Submit Button -->
        <button type="submit" class="btn btn-primary">Save Changes</button>
    </form>
</div>

<% } else { %>
<p>You need to be logged in to view and edit your profile.</p>
<% } %>
</body>
</html>