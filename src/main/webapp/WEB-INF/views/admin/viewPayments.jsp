<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.megacitycab.model.Payment" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>View Payments</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="../shared/admin_navbar.jsp" %>  <!-- Include Admin Navbar -->

<div class="container mt-4">
  <h2 class="mb-4">Payment Transactions</h2>

  <table class="table table-striped table-bordered">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>User ID</th>
      <th>Amount</th>
      <th>Card Holder Name</th>
      <th>Status</th>
      <th>Date</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="payment" items="${payments}">
      <tr>
        <td>${payment.id}</td>
        <td>${payment.customerId}</td>
        <td>${payment.amount}</td>
        <td>${payment.cardHolderName}</td>
        <td>
          <c:choose>
            <c:when test="${payment.status eq 'Completed'}">
              <span class="badge bg-success">Completed</span>
            </c:when>
            <c:when test="${payment.status eq 'Pending'}">
              <span class="badge bg-warning text-dark">Pending</span>
            </c:when>
            <c:otherwise>
              <span class="badge bg-danger">Failed</span>
            </c:otherwise>
          </c:choose>
        </td>
        <td>${payment.paymentDate}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
