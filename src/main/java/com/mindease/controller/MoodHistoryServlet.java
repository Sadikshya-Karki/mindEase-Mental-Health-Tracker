package com.mindease.controller;

import com.mindease.model.MoodEntry;
import com.mindease.model.User;
import com.mindease.service.MoodEntryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Displays the logged-in users mood history.
 */
@WebServlet("/user/mood-history")
public class MoodHistoryServlet extends HttpServlet {

    private final MoodEntryService moodEntryService = new MoodEntryService();

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

        int userId = loggedUser.getUserId();

        int totalEntries = moodEntryService.getTotalCountByUserId(userId);
        double avgScore = moodEntryService.getAverageScoreByUserId(userId);
        int positiveDays = moodEntryService.getPositiveDaysCount(userId);
        String bestDay = moodEntryService.getBestDayByUserId(userId);

        List<MoodEntry> chartEntries = moodEntryService.getRecentByUserId(userId, 12);
        List<MoodEntry> allEntries = moodEntryService.getAllByUserId(userId);

        request.setAttribute("totalEntries", totalEntries);
        request.setAttribute("avgScore", avgScore);
        request.setAttribute("positiveDays", positiveDays);
        if (bestDay == null || bestDay.isEmpty()) {
            request.setAttribute("bestDay", null);
        } else {
            request.setAttribute("bestDay", bestDay);
        }
        request.setAttribute("chartEntries", chartEntries);
        request.setAttribute("allEntries", allEntries);
        request.setAttribute("filteredEntries", allEntries);
        request.setAttribute("pageTitle", "Mood History");

        request.getRequestDispatcher("/WEB-INF/views/user/mood-history.jsp").forward(request, response);
    }
}
