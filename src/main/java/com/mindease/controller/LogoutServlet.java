package com.mindease.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Ends the current user session and redirects to the login page.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Handles GET requests for this servlet and prepares the response view or redirect.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if servlet processing fails
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        Cookie rememberCookie = new Cookie("rememberMe", "");
        rememberCookie.setMaxAge(0);
        rememberCookie.setPath("/");
        rememberCookie.setAttribute("SameSite", "Lax");
        response.addCookie(rememberCookie);

        response.sendRedirect(request.getContextPath() + "/login");
    }
}
