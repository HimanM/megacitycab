<%@ page import="com.example.megacitycab.model.Vehicle" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="../shared/admin_navbar.jsp" %>

<%@ include file="../shared/message_popup.jsp" %>

<%
  List<Vehicle> assignedVehicles = (List<Vehicle>) request.getAttribute("assignedVehicles");
  List<Vehicle> availableVehicles = (List<Vehicle>) request.getAttribute("availableVehicles");
  List<Vehicle> maintenanceVehicles = (List<Vehicle>) request.getAttribute("maintenanceVehicles");
%>

<div class="container mt-4">
  <h2>Manage Vehicles</h2>

  <!-- ✅ Add New Vehicle -->
  <form method="post" action="addVehicles">
    <input type="hidden" name="action" value="add">
    <div class="row">
      <div class="col"><input type="text" name="license_plate" placeholder="License Plate" required class="form-control"></div>
      <div class="col"><input type="text" name="model" placeholder="Model" required class="form-control"></div>
      <div class="col"><input type="text" name="manufacturer" placeholder="Manufacturer" required class="form-control"></div>
    </div>
    <div class="row mt-2">
      <div class="col"><input type="text" name="vehicle_type" placeholder="Vehicle Type" required class="form-control"></div>
      <div class="col"><input type="number" name="year" placeholder="Year" required class="form-control"></div>
      <div class="col"><input type="number" name="capacity" placeholder="Seat Capacity" required class="form-control"></div>
    </div>
    <button type="submit" class="btn btn-primary mt-3">Add Vehicle</button>
  </form>

  <!-- ✅ Assigned Vehicles (Cannot Modify) -->
  <h3 class="mt-4">Assigned Vehicles</h3>
  <table class="table table-striped">
    <tr><th>ID</th><th>License Plate</th><th>Model</th><th>Status</th></tr>
    <% for (Vehicle v : assignedVehicles) { %>
    <tr><td><%= v.getId() %></td><td><%= v.getLicensePlate() %></td><td><%= v.getModel() %></td><td>Assigned</td></tr>
    <% } %>
  </table>

  <!-- ✅ Maintenance Vehicles (Cannot Modify) -->
  <h3 class="mt-4">Vehicles Under Maintenance</h3>
  <table class="table table-striped">
    <tr><th>ID</th><th>License Plate</th><th>Model</th><th>Status</th></tr>
    <% for (Vehicle v : maintenanceVehicles) { %>
    <tr><td><%= v.getId() %></td><td><%= v.getLicensePlate() %></td><td><%= v.getModel() %></td><td>In Maintenance</td></tr>
    <% } %>
  </table>

  <!-- ✅ Available Vehicles (Can Modify) -->
  <h3 class="mt-4">Available Vehicles</h3>
  <table class="table table-striped">
    <% for (Vehicle v : availableVehicles) { %>
    <tr>
      <td><%= v.getId() %></td>
      <td><%= v.getLicensePlate() %></td>
      <td><%= v.getModel() %></td>
      <td>
        <form method="post" action="updateVehicles">
          <input type="hidden" name="action" value="update">
          <input type="hidden" name="vehicleId" value="<%= v.getId() %>">
          <select name="status" class="form-control">
            <option value="Available">Available</option>
            <option value="In Maintenance">In Maintenance</option>
          </select>
          <button type="submit" class="btn btn-warning mt-1">Update</button>
        </form>
      </td>
    </tr>
    <% } %>
  </table>
</div>
