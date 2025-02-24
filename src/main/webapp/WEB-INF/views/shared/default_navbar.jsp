<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    /* Navbar styling */
    .navbar {
      background: rgba(0, 0, 0, 0.8);
    }
  </style>
</head>
<body>
<c:if test="${sessionScope.userId == null}">
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
      <a class="navbar-brand fw-bold text-warning" href="#">Mega City Cab</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav me-auto">
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/help">Get Help</a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link btn btn-outline-warning mx-2" href="${pageContext.request.contextPath}/auth/login">Login</a>
          </li>
          <li class="nav-item">
            <a class="nav-link btn btn-warning text-light" href="${pageContext.request.contextPath}/auth/register">Register</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
