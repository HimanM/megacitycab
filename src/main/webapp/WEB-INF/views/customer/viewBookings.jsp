<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Your Bookings</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2>Your Bookings</h2>

  <c:if test="${not empty message}">
    <div class="alert alert-info">${message}</div>
  </c:if>

  <c:if test="${empty bookings}">
    <div class="alert alert-info">You have no bookings yet.</div>
  </c:if>

  <c:if test="${not empty bookings}">
    <table class="table table-striped">
      <thead>
      <tr>
        <th>#</th>
        <th>Order Number</th>
        <th>Destination</th>
        <th>Booking Date</th>
        <th>Total Amount</th>
        <th>Status</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="booking" items="${bookings}">
        <tr>
          <td>${booking.id}</td>
          <td>${booking.orderNumber}</td>
          <td>${booking.destinationDetails}</td>
          <td>${booking.bookingDate}</td>
          <td>${booking.totalAmount}</td>
          <td>${booking.status}</td>
          <td>
            <form method="post" action="${pageContext.request.contextPath}/booking/cancel">
              <input type="hidden" name="bookingId" value="${booking.id}">
              <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
</div>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
