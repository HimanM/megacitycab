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

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Profile Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .alert {
            padding: 12px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2>Profile Management</h2>

    <!-- Display success or error messages from the controller -->
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form action="<%= request.getContextPath() + "/profile" %>" method="POST" onsubmit="return validateForm()">
        <!-- Email -->
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="<%= loggedInUser.getEmail() %>" required>
            <div class="text-danger" id="email-error"></div>
        </div>

        <!-- NIC -->
        <div class="mb-3">
            <label for="nic" class="form-label">NIC</label>
            <input type="text" class="form-control" id="nic" name="nic" value="<%= loggedInUser.getNic() %>" required>
            <div class="text-danger" id="nic-error"></div>
        </div>

        <!-- Phone Number -->
        <div class="mb-3">
            <label for="phoneNumber" class="form-label">Phone Number</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="<%= loggedInUser.getPhone() %>" required>
            <div class="text-danger" id="phone-error"></div>
        </div>

        <!-- Password Change Section -->
        <div class="mb-3">
            <label for="password" class="form-label">New Password</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>

        <div class="mb-3">
            <label for="confirm-password" class="form-label">Confirm New Password</label>
            <input type="password" class="form-control" id="confirm-password" name="confirm-password">
            <div class="text-danger" id="password-error"></div>
        </div>

        <!-- Submit Button -->
        <button type="submit" class="btn btn-primary">Save Changes</button>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function validateForm() {
        let email = document.getElementById("email").value.trim();
        let nic = document.getElementById("nic").value.trim();
        let phone = document.getElementById("phoneNumber").value.trim();
        let password = document.getElementById("password").value.trim();
        let confirmPassword = document.getElementById("confirm-password").value.trim();

        let valid = true;

        // Clear previous errors
        document.getElementById("email-error").textContent = "";
        document.getElementById("nic-error").textContent = "";
        document.getElementById("phone-error").textContent = "";
        document.getElementById("password-error").textContent = "";

        // Email validation
        if (email === "") {
            document.getElementById("email-error").textContent = "Email is required.";
            valid = false;
        }

        // NIC validation
        if (nic === "") {
            document.getElementById("nic-error").textContent = "NIC is required.";
            valid = false;
        }

        // Phone number validation
        if (phone === "") {
            document.getElementById("phone-error").textContent = "Phone number is required.";
            valid = false;
        }

        // Password confirmation validation
        if (password !== "" && password !== confirmPassword) {
            document.getElementById("password-error").textContent = "Passwords do not match.";
            valid = false;
        }

        return valid;
    }
</script>

</body>
</html>
