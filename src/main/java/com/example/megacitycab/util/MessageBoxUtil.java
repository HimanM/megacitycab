package com.example.megacitycab.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class MessageBoxUtil {
    public HttpServletRequest displayMessageBox(HttpServletRequest req, String type, String message, String link) throws ServletException, IOException {
        req.setAttribute("messageType", type); // or "error"
        req.setAttribute("messageText", message);
        req.setAttribute("buttonLink", link);
        return req;
    }
}
