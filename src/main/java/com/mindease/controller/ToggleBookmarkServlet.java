package com.mindease.controller;

import com.mindease.model.User;
import com.mindease.service.BookmarkService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Handles bookmark toggle requests for the current user.
 */
@WebServlet("/user/toggle-bookmark")
public class ToggleBookmarkServlet extends HttpServlet {

    private final BookmarkService bookmarkService = new BookmarkService();

    /**
     * Handles POST requests for this servlet and processes submitted form data.
     *
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("loggedUser");
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            int resourceId = Integer.parseInt(req.getParameter("resourceId"));
            boolean bookmarked = Boolean.parseBoolean(req.getParameter("bookmarked"));

            boolean ok = bookmarked
                    ? bookmarkService.addBookmark(user.getUserId(), resourceId)
                    : bookmarkService.removeBookmark(user.getUserId(), resourceId);

            String returnUrl = req.getParameter("returnUrl");

            // AJAX call (no returnUrl) â€” return HTTP status only
            if (returnUrl == null || returnUrl.isBlank()) {
                resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            // Form-based call with returnUrl â€” redirect
            String target = returnUrl + (returnUrl.contains("?") ? "&" : "?") + (ok ? "success=bookmark" : "error=bookmark");
            resp.sendRedirect(req.getContextPath() + target);

        } catch (NumberFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
