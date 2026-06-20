package com.mindease.controller;

import com.mindease.model.User;
import com.mindease.model.WeeklyMood;
import com.mindease.service.DashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Loads and displays the authenticated users dashboard.
 */
@WebServlet("/user/dashboard")
public class UserDashboardServlet extends HttpServlet {

    private final DashboardService dashboardService = new DashboardService();

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

        HttpSession session = request.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!"user".equals(loggedUser.getRole()) && !"admin".equals(loggedUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = loggedUser.getUserId();

        try {
            List<WeeklyMood> weeklyMoodData = dashboardService.getWeeklyMoodData(userId);

            request.setAttribute("moodStreak", dashboardService.getMoodStreak(userId));
            request.setAttribute("totalLogs", dashboardService.getTotalLogs(userId));
            request.setAttribute("savedResourcesCount", dashboardService.getSavedResourcesCount(userId));
            request.setAttribute("appointmentsCount", dashboardService.getAppointmentsCount(userId));
            request.setAttribute("avgMoodScore", dashboardService.getAvgMoodScore(userId));
            request.setAttribute("todayMood", dashboardService.getTodayMood(userId));
            request.setAttribute("weeklyMoodData", dashboardService.buildSevenDayChartData(weeklyMoodData));
            request.setAttribute("upcomingSessions", dashboardService.getUpcomingSessions(userId, 2));
            request.setAttribute("savedResources", dashboardService.getSavedResources(userId, 3));
            request.setAttribute("recentEntries", dashboardService.getRecentMoodEntries(userId, 3));
            request.setAttribute("pageTitle", "Dashboard");
            request.setAttribute("todayDate", LocalDate.now().toString());

            request.getRequestDispatcher("/WEB-INF/views/user/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading dashboard.");
            request.getRequestDispatcher("/WEB-INF/views/user/dashboard.jsp").forward(request, response);
        }
    }
}
