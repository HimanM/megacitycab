package com.example.megacitycab.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/customer/*")
public class CustomerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        switch (action) {
            case "/dashboard":
                forward(req, resp, "/WEB-INF/views/customer/dashboard.jsp");
                break;
            case "/placeBooking":
                forward(req, resp, "/WEB-INF/views/customer/placeBooking.jsp");
                break;
            case "/viewBookings":
                forward(req, resp, "/WEB-INF/views/customer/viewBookings.jsp");
                break;
            case "/availableVehicles":
                forward(req, resp, "/WEB-INF/views/customer/availableVehicles.jsp");
                break;
            case "/calculateBill":
                forward(req, resp, "/WEB-INF/views/customer/calculateBill.jsp");
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }
}