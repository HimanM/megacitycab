<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../shared/admin_navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
  <h2 class="text-center">Welcome to Admin Dashboard</h2>
  <div class="row mt-5">
    <div class="col-md-3">
      <a href="${pageContext.request.contextPath}/admin/dashboard/viewPayments" class="card text-center text-decoration-none">
        <div class="card-body">
          <h5 class="card-title">View Payments</h5>
          <p class="card-text">Check all completed transactions</p>
        </div>
      </a>
    </div>
    <div class="col-md-3">
      <a href="${pageContext.request.contextPath}/admin/dashboard/manageDrivers" class="card text-center text-decoration-none">
        <div class="card-body">
          <h5 class="card-title">Manage Drivers</h5>
          <p class="card-text">Approve, reject, or update driver details</p>
        </div>
      </a>
    </div>
    <div class="col-md-3">
      <a href="${pageContext.request.contextPath}/admin/dashboard/manageUsers" class="card text-center text-decoration-none">
        <div class="card-body">
          <h5 class="card-title">Manage Users</h5>
          <p class="card-text">View and manage customer accounts</p>
        </div>
      </a>
    </div>
    <div class="col-md-3">
      <a href="${pageContext.request.contextPath}/admin/dashboard/manageVehicles" class="card text-center text-decoration-none">
        <div class="card-body">
          <h5 class="card-title">Manage Vehicles</h5>
          <p class="card-text">Add, update, or remove vehicles</p>
        </div>
      </a>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
