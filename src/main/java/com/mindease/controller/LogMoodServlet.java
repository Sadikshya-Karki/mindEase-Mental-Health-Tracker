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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles mood logging requests and prepares the users daily wellness context.
 */
@WebServlet("/user/log-mood")
public class LogMoodServlet extends HttpServlet {

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

        User loggedUser = getLoggedUser(request, response);
        if (loggedUser == null) {
            return;
        }

        int userId = loggedUser.getUserId();
        MoodEntry todayEntry = moodEntryService.getTodayEntry(userId);

        request.setAttribute("pageTitle", "Log Mood");
        request.setAttribute("recentEntries", moodEntryService.getRecentByUserId(userId, 5));
        request.setAttribute("allTags", moodEntryService.getAllTags());
        request.setAttribute("hasTodayEntry", todayEntry != null);
        request.setAttribute("todayMood", todayEntry);
        request.setAttribute("todaySelectedTagNames", todayEntry == null ? new ArrayList<>() : todayEntry.getTags());
        request.setAttribute("wellnessTip", getDailyWellnessTip());

        request.getRequestDispatcher("/WEB-INF/views/user/log-mood.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for this servlet and processes submitted form data.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User loggedUser = getLoggedUser(request, response);
        if (loggedUser == null) {
            return;
        }

        int userId = loggedUser.getUserId();
        String note = safeTrim(request.getParameter("note"));
        int score;

        try {
            score = Integer.parseInt(safeTrim(request.getParameter("score")));
        } catch (NumberFormatException ex) {
            redirectWithError(request, response, "Mood score is required.");
            return;
        }

        if (score < 1 || score > 5) {
            redirectWithError(request, response, "Mood score must be between 1 and 5.");
            return;
        }

        if (note.length() > 500) {
            redirectWithError(request, response, "Note must be 500 characters or less.");
            return;
        }

        List<Integer> tagIds = parseTagIds(request);

        try {
            int entryId;
            if (moodEntryService.hasTodayEntry(userId)) {
                MoodEntry updateEntry = new MoodEntry();
                updateEntry.setUserId(userId);
                updateEntry.setScore(score);
                updateEntry.setNote(note);

                int updated = moodEntryService.update(updateEntry);
                if (updated <= 0) {
                    redirectWithError(request, response, "Could not update today's mood.");
                    return;
                }

                MoodEntry todayEntry = moodEntryService.getTodayEntry(userId);
                if (todayEntry == null) {
                    redirectWithError(request, response, "Could not load today's mood after update.");
                    return;
                }
                entryId = todayEntry.getEntryId();
            } else {
                MoodEntry newEntry = new MoodEntry();
                newEntry.setUserId(userId);
                newEntry.setScore(score);
                newEntry.setNote(note);
                newEntry.setEntryDate(Date.valueOf(LocalDate.now()));

                entryId = moodEntryService.insert(newEntry);
                if (entryId <= 0) {
                    redirectWithError(request, response, "Could not save mood entry.");
                    return;
                }
            }

            moodEntryService.replaceEntryTags(entryId, tagIds);

            response.sendRedirect(buildRedirectUrl(request, "success", "true"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error?message=Database+error");
            return;
        }
    }

    /**
     * Loads the current session user and enforces role access for the mood flow.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the authenticated user or null when unauthorized
     * @throws IOException if an I/O error occurs
     */
    private User getLoggedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        if (!"user".equalsIgnoreCase(loggedUser.getRole()) && !"admin".equalsIgnoreCase(loggedUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        return loggedUser;
    }

    /**
     * Parses tag IDs from request parameters and de-duplicates the result.
     *
     * @param request the HTTP request
     * @return the list of parsed tag IDs
     */
    private List<Integer> parseTagIds(HttpServletRequest request) {
        List<Integer> parsed = new ArrayList<>();

        String[] rawValues = request.getParameterValues("tags");
        if ((rawValues == null || rawValues.length == 0)) {
            String csv = safeTrim(request.getParameter("selectedTags"));
            if (!csv.isEmpty()) {
                rawValues = csv.split(",");
            }
        }

        if (rawValues == null) {
            return parsed;
        }

        for (String raw : rawValues) {
            String value = safeTrim(raw);
            if (value.isEmpty()) {
                continue;
            }
            try {
                int id = Integer.parseInt(value);
                if (id > 0) {
                    parsed.add(id);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return parsed.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Trims a string value or returns an empty string when null.
     *
     * @param value the input string
     * @return the trimmed string or an empty string when null
     */
    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    /**
     * Redirects back to the log mood page with an error message.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param message the error message to include
     * @throws IOException if an I/O error occurs
     */
    private void redirectWithError(HttpServletRequest request, HttpServletResponse response, String message)
            throws IOException {
        String encoded = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect(buildRedirectUrl(request, "error", encoded));
    }

    /**
     * Builds a redirect URL with a single query parameter appended.
     *
     * @param request the HTTP request
     * @param parameterName the parameter name to add
     * @param parameterValue the parameter value to add
     * @return the redirect URL with query parameters applied
     */
    private String buildRedirectUrl(HttpServletRequest request, String parameterName, String parameterValue) {
        String returnUrl = safeTrim(request.getParameter("returnUrl"));
        String target = returnUrl.isEmpty() ? "/user/log-mood" : returnUrl;
        String separator = target.contains("?") ? "&" : "?";
        return request.getContextPath() + target + separator + parameterName + "=" + parameterValue;
    }

    /**
     * Provides a deterministic daily wellness tip string.
     *
     * @return the daily wellness tip
     */
    private String getDailyWellnessTip() {
        String[] tips = {
                "Take three deep breaths before reacting.",
                "It's okay to not be okay. Give yourself grace.",
                "A 5-minute walk can reset your mood.",
                "Write down one thing you're grateful for today.",
                "Reach out to someone you trust."
        };
        int idx = LocalDate.now().getDayOfYear() % tips.length;
        return tips[idx];
    }
}
