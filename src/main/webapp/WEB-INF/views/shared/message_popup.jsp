<%-- message_popup.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%
    String messageType = (String) request.getAttribute("messageType"); // "success" or "error"
    String messageText = (String) request.getAttribute("messageText"); // Message details
    String buttonLink = (String) request.getAttribute("buttonLink"); // Redirect link
    System.out.println("messageType: " + messageType + ", messageText: " + messageText + ", buttonLink: " + buttonLink);
    boolean showPopup = messageType != null && messageText != null && buttonLink != null;
%>

<% if (showPopup) { %>
<div id="messagePopup" class="modal fade show" style="display: block; background: rgba(0, 0, 0, 0.5);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="background-color: <%= "success".equals(messageType) ? "#28a745" : "#dc3545" %>; color: white;">
                <h5 class="modal-title">
                    <%= "success".equals(messageType) ? "Success!" : "Error!" %>
                </h5>
            </div>
            <div class="modal-body">
                <p><%= messageText %></p>
            </div>
            <div class="modal-footer">
                <a href="<%= buttonLink %>" class="btn <%= "success".equals(messageType) ? "btn-success" : "btn-danger" %>">OK</a>
            </div>
        </div>
    </div>
</div>
<script>
    setTimeout(() => { document.getElementById("messagePopup").style.display = "none"; }, 5000); // Auto-hide after 5 sec
</script>
<% } %>
