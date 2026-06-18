package com.mindease.controller;

import com.mindease.model.User;
import com.mindease.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Generates admin reports and prepares reporting data for the dashboard view.
 */
@WebServlet("/admin/reports")
public class AdminReportServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();

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

        request.setAttribute("totalUsers", reportService.getTotalUsers());
        request.setAttribute("activeUsers", reportService.getActiveUsers());
        request.setAttribute("pendingUsers", reportService.getPendingUsers());
        request.setAttribute("totalMoodEntries", reportService.getTotalMoodEntries());
        request.setAttribute("avgMoodScore", reportService.getAverageMoodScore());
        request.setAttribute("moodDistribution", reportService.getMoodDistribution());
        // compute percentage map for mood distribution (1..5) to avoid EL coercion issues in JSP
        Map<Integer, Integer> _moodDist = reportService.getMoodDistribution();
        int _totalMoodEntries = reportService.getTotalMoodEntries();
        Map<String, Double> moodDistributionPercent = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            int cnt = _moodDist != null ? _moodDist.getOrDefault(i, 0) : 0;
            double pct = 0.0;
            if (_totalMoodEntries > 0) {
                // compute percentage and round to 1 decimal place
                pct = Math.round((cnt * 1000.0 / _totalMoodEntries)) / 10.0;
            }
            moodDistributionPercent.put(String.valueOf(i), pct);
        }
        // Log computed distribution for runtime inspection
        System.out.println("[Reports] totalMoodEntries=" + _totalMoodEntries + ", moodDistribution=" + _moodDist + ", moodDistributionPercent=" + moodDistributionPercent);
        request.setAttribute("moodDistributionPercent", moodDistributionPercent);
        request.setAttribute("topCounselors", reportService.getTopCounselors(5));
        request.setAttribute("totalResources", reportService.getTotalResources());
        request.setAttribute("publishedResources", reportService.getPublishedResources());
        request.setAttribute("totalAppointments", reportService.getTotalAppointments());
        request.setAttribute("pageTitle", "Reports");

        request.getRequestDispatcher("/WEB-INF/views/admin/reports.jsp").forward(request, response);
    }

    /**
     * Checks whether the current session belongs to an admin user and redirects if not.
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
}
