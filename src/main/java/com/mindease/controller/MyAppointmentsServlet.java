package com.mindease.controller;

import com.mindease.model.Appointment;
import com.mindease.model.User;
import com.mindease.service.AppointmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Displays the logged-in users appointments.
 */
@WebServlet("/user/appointments")
public class MyAppointmentsServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentService();

    /**
     * Handles GET requests for this servlet and prepares the response view or redirect.
     *
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws ServletException if servlet processing fails
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("loggedUser");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String statusFilter = req.getParameter("status");
        if (statusFilter == null || statusFilter.isBlank()) {
            statusFilter = "all";
        }

        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(user.getUserId(), statusFilter);

        int totalCount = appointmentService.getCountByUserId(user.getUserId(), "all");
        int approvedCount = appointmentService.getCountByUserId(user.getUserId(), "approved");
        int pendingCount = appointmentService.getCountByUserId(user.getUserId(), "pending");
        int rejectedCount = appointmentService.getCountByUserId(user.getUserId(), "rejected");

        req.setAttribute("appointments", appointments);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("approvedCount", approvedCount);
        req.setAttribute("pendingCount", pendingCount);
        req.setAttribute("rejectedCount", rejectedCount);
        req.setAttribute("activeFilter", statusFilter);
        req.setAttribute("pageTitle", "My Appointments");

        req.getRequestDispatcher("/WEB-INF/views/user/my-appointments.jsp").forward(req, resp);
    }
}
