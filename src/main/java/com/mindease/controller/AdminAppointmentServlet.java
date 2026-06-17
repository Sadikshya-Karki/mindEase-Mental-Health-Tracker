package com.mindease.controller;

import com.mindease.model.User;
import com.mindease.service.AppointmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Handles admin appointment management requests, including viewing appointments and updating appointment status.
 */
@WebServlet("/admin/appointments")
public class AdminAppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentService();

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

        request.setAttribute("appointments", appointmentService.getAllAppointments());
        request.setAttribute("pendingCount", appointmentService.countByStatus("pending"));
        request.setAttribute("approvedCount", appointmentService.countByStatus("approved"));
        request.setAttribute("completedCount", appointmentService.countByStatus("completed"));
        request.setAttribute("rejectedCount", appointmentService.countByStatus("rejected"));
        request.setAttribute("pageTitle", "Appointments");
        request.getRequestDispatcher("/WEB-INF/views/admin/appointments.jsp").forward(request, response);
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
        int apptId = parseInt(request.getParameter("apptId"));
        boolean ok = false;

        if (apptId > 0) {
            if ("approve".equals(action)) {
                ok = appointmentService.updateAppointmentStatus(apptId, "approved", request.getParameter("notes"));
            } else if ("reject".equals(action)) {
                ok = appointmentService.updateAppointmentStatus(apptId, "rejected", request.getParameter("notes"));
            } else if ("complete".equals(action)) {
                ok = appointmentService.updateAppointmentStatus(apptId, "completed", request.getParameter("notes"));
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/appointments" + (ok ? "?success=true" : "?error=true"));
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
