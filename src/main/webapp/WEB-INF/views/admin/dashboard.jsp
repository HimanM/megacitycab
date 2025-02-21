<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="CSS/main.css"> <!-- Your custom styles -->
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Admin Panel</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item">
          <a class="nav-link" href="view_payments.jsp">View Payments</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="manage_drivers.jsp">Manage Drivers</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="manage_users.jsp">Manage Users</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="manage_vehicles.jsp">Manage Vehicles</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-danger" href="logout.jsp">Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<div class="container mt-4">
  <h2 class="text-center">Welcome to Admin Dashboard</h2>
  <div class="row mt-5">
    <div class="col-md-3">
      <a href="view_payments.jsp" class="card text-center text-decoration-none">
        <div class="card-body">
          <h5 class="card-title">View Payments</h5>
          <p class="card-text">Check all completed transactions</p>
        </div>
      </a>
    </div>
    <div class="col-md-3">
      <a href="manage_drivers.jsp" class="card text-center text-decoration-none">
        <div class="card-body">
          <h5 class="card-title">Manage Drivers</h5>
          <p class="card-text">Approve, reject, or update driver details</p>
        </div>
      </a>
    </div>
    <div class="col-md-3">
      <a href="manage_users.jsp" class="card text-center text-decoration-none">
        <div class="card-body">
          <h5 class="card-title">Manage Users</h5>
          <p class="card-text">View and manage customer accounts</p>
        </div>
      </a>
    </div>
    <div class="col-md-3">
      <a href="manage_vehicles.jsp" class="card text-center text-decoration-none">
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
