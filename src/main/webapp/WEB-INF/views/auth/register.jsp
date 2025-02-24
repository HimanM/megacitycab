<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="../shared/default_navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"><link rel="stylesheet" type="text/css" href="../../assets/css/styles.css">

</head>
<body class="body-bg">

<div class="container d-flex align-items-center justify-content-center vh-100">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h3 class="text-center mb-4">Register</h3>

                        <!-- Display error messages if any -->
                        <c:if test="${not empty errors}">
                            <div class="alert alert-danger" role="alert">
                                <ul>
                                    <c:forEach var="error" items="${errors}">
                                        <li>${error}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>

                        <form method="post" action="${pageContext.request.contextPath}/auth/register">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control" id="username" name="username" placeholder="Choose a username" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="name" class="form-label">Full Name</label>
                                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter your full name" required>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="confirm-password" class="form-label">Confirm Password</label>
                                    <input type="password" class="form-control" id="confirm-password" name="confirm-password" placeholder="Confirm the password" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Choose a password" required>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="phone" class="form-label">Phone</label>
                                    <input type="text" class="form-control" id="phone" name="phone" placeholder="Enter your phone number" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address</label>
                                <textarea class="form-control" id="address" name="address" rows="2" placeholder="Enter your address" required></textarea>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="nic" class="form-label">NIC</label>
                                    <input type="text" class="form-control" id="nic" name="nic" placeholder="Enter your NIC" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="role" class="form-label">Role</label>
                                    <select class="form-select" id="role" name="role" required onchange="toggleLicenseField()">
                                        <option value="" disabled selected>Select your role</option>
                                        <option value="Customer">Customer</option>
                                        <option value="Driver">Driver</option>
                                    </select>
                                </div>
                            </div>

                            <!-- License number input (hidden by default) -->
                            <div class="mb-3" id="licenseField" style="display: none;">
                                <label for="license_number" class="form-label">Driver's License Number</label>
                                <input type="text" class="form-control" id="license_number" name="license_number" placeholder="Enter your license number">
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Register</button>
                        </form>


                        <p class="text-center mt-3">
                            Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login here</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap JS and dependencies -->
<script>
    function toggleLicenseField() {
        let role = document.getElementById("role").value;
        let licenseField = document.getElementById("licenseField");

        if (role === "Driver") {
            licenseField.style.display = "block";
        } else {
            licenseField.style.display = "none";
        }
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
