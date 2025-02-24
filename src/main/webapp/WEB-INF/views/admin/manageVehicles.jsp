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
  <div class="container shadow-sm mt-2">
    <h2>Manage Vehicles</h2>

    <!-- ✅ Add New Vehicle -->
    <form class="mb-4 pb-3" method="post" action="addVehicles">
      <input type="hidden" name="action" value="add">
      <div class="row mt-2">
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
  </div>

  <!-- ✅ Assigned Vehicles (Cannot Modify) -->
  <div class="container shadow-sm mt-4">
    <h3 class="mt-4 p-2">Assigned Vehicles</h3>
    <div class="container pb-3">
      <table class="table table-striped mb-4">
        <tr><th>ID</th><th>License Plate</th><th>Model</th><th>Status</th></tr>
        <% for (Vehicle v : assignedVehicles) { %>
        <tr><td><%= v.getId() %></td><td><%= v.getLicensePlate() %></td><td><%= v.getModel() %></td><td>Assigned</td></tr>
        <% } %>
      </table>
    </div>
  </div>

  <!-- ✅ Maintenance Vehicles (Cannot Modify) -->
  <div class="container shadow-sm mt-4">
    <h3 class="mt-4 p-2">Vehicles Under Maintenance</h3>
    <div class="container pb-3">
      <table class="table table-striped mb-4">
        <tr><th>ID</th><th>License Plate</th><th>Model</th><th>Status</th></tr>
        <% for (Vehicle v : maintenanceVehicles) { %>
        <tr><td><%= v.getId() %></td><td><%= v.getLicensePlate() %></td><td><%= v.getModel() %></td><td>In Maintenance</td></tr>
        <% } %>
      </table>
    </div>
  </div>

  <!-- ✅ Available Vehicles (Can Modify) -->
  <div class="container shadow-sm mt-4">
    <h3 class="mt-4 p-2">Available Vehicles</h3>
    <div class="container pb-3">
      <table class="table table-striped mb-4">
        <tr><th>ID</th><th>License Plate</th><th>Model</th><th>Status</th></tr>
        <% for (Vehicle v : availableVehicles) { %>
        <tr>
          <td><%= v.getId() %></td>
          <td><%= v.getLicensePlate() %></td>
          <td><%= v.getModel() %></td>
          <td>
            <form method="post" action="updateVehicles" class="d-flex align-items-center">
              <input type="hidden" name="action" value="update">
              <input type="hidden" name="vehicleId" value="<%= v.getId() %>">

              <select name="status" class="form-control me-2 w-auto"> <!-- 'me-2' adds margin to the right of the select -->
                <option value="Available">Available</option>
                <option value="In Maintenance">In Maintenance</option>
              </select>

              <button type="submit" class="btn btn-warning">Update</button>
            </form>
          </td>
        </tr>
        <% } %>
      </table>
    </div>
  </div>
</div>
