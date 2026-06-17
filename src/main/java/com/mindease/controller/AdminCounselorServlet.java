package com.mindease.controller;

import com.mindease.model.Counselor;
import com.mindease.model.User;
import com.mindease.service.CounselorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Handles admin counselor management requests for reviewing counselor profiles and maintaining counselor records.
 */
@WebServlet("/admin/counselors")
public class AdminCounselorServlet extends HttpServlet {

    private final CounselorService counselorService = new CounselorService();

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

        String editId = request.getParameter("editId");
        if (editId != null && !editId.isBlank()) {
            request.setAttribute("editCounselor", counselorService.getCounselorById(parseInt(editId)));
        }

        request.setAttribute("counselors", counselorService.getAllCounselors());
        request.setAttribute("pageTitle", "Manage Counselors");
        request.getRequestDispatcher("/WEB-INF/views/admin/counselors.jsp").forward(request, response);
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
        boolean ok = false;

        if ("create".equals(action) || "update".equals(action)) {
            Counselor c = new Counselor();
            c.setCounselorId(parseInt(request.getParameter("counselorId")));
            c.setName(request.getParameter("name"));
            c.setSpecialization(request.getParameter("specialization"));
            c.setEmail(request.getParameter("email"));
            c.setPhone(request.getParameter("phone"));
            c.setAvailableDays(request.getParameter("availableDays"));
            c.setStatus(request.getParameter("status"));
            ok = "create".equals(action) ? counselorService.createCounselor(c) : counselorService.updateCounselor(c);
        } else if ("deactivate".equals(action)) {
            ok = counselorService.deactivateCounselor(parseInt(request.getParameter("counselorId")));
        } else if ("activate".equals(action)) {
            ok = counselorService.activateCounselor(parseInt(request.getParameter("counselorId")));
        }

        response.sendRedirect(request.getContextPath() + "/admin/counselors" + (ok ? "?success=true" : "?error=true"));
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
     * Parses an integer value, returning 0 when invalid.
     *
     * @param value the raw string value
     * @return the parsed integer or 0 when invalid
     */
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
