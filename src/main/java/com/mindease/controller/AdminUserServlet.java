package com.mindease.controller;

import com.mindease.model.User;
import com.mindease.service.AdminUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Handles admin user management requests, including user listing and account status updates.
 */
@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {

    private final AdminUserService userService = new AdminUserService();

    /**
     * Handles GET requests for this servlet and prepares the response view or redirect.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if servlet processing fails
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request, response)) return;

        String status = request.getParameter("status");
        java.util.List<User> users;

        if ("pending".equals(status)) {
            users = userService.getUsersByStatus("pending");
        } else if ("active".equals(status)) {
            users = userService.getUsersByStatus("active");
        } else if ("inactive".equals(status)) {
            users = userService.getUsersByStatus("inactive");
        } else {
            users = userService.getAllUsers();
        }

        request.setAttribute("users", users);
        request.setAttribute("totalAll", userService.getAllUsers().size());
        request.setAttribute("totalPending", userService.getUsersByStatus("pending").size());
        request.setAttribute("totalActive", userService.getUsersByStatus("active").size());
        request.setAttribute("totalInactive", userService.getUsersByStatus("inactive").size());
        request.setAttribute("pageTitle", "Manage Users");
        request.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for this servlet and processes submitted form data.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isAdmin(request, response)) return;

        String action = request.getParameter("action");
        int userId = parseInt(request.getParameter("userId"));
        boolean ok = false;

        if (userId > 0) {
            if ("approve".equals(action) || "activate".equals(action)) {
                ok = userService.updateUserStatus(userId, "active");
            } else if ("reject".equals(action) || "deactivate".equals(action)) {
                ok = userService.updateUserStatus(userId, "inactive");
            }
        }

        String redirect = request.getContextPath() + "/admin/users" + (ok ? "?success=true" : "?error=true");
        response.sendRedirect(redirect);
    }

    /**
     * Verifies the current session is an admin session and redirects if not.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return true when the user is an authenticated admin; false otherwise
     * @throws IOException if an I/O error occurs
     */
    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;
        if (loggedUser == null || !"admin".equals(loggedUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    /**
     * Parses an integer value, returning -1 when invalid.
     *
     * @param value the raw string value
     * @return the parsed integer or -1 when invalid
     */
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return -1;
        }
    }
}
