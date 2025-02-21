<%@ page import="com.example.megacitycab.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="../shared/admin_navbar.jsp" %>

<%
  List<User> users = (List<User>) request.getAttribute("users");
%>

<div class="container mt-4">
  <h2>Manage Users</h2>

  <table class="table table-striped">
    <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Email</th>
      <th>Role</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% for (User user : users) { %>
    <tr>
      <td><%= user.getId() %></td>
      <td><%= user.getName() %></td>
      <td><%= user.getEmail() %></td>
      <td><%= user.getRole() %></td>
      <td>
        <% if (!(user.getRole().equals("Admin"))) { %>
        <form method="post" action="deleteUsers" onsubmit="return confirm('Are you sure you want to delete this user?');">
          <input type="hidden" name="userId" value="<%= user.getId() %>">
          <button type="submit" class="btn btn-danger">Remove</button>
        </form>
        <% } %>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
