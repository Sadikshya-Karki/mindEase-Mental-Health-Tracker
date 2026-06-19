package com.mindease.controller;

import com.mindease.model.Resource;
import com.mindease.model.User;
import com.mindease.service.BookmarkService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays the current users bookmarked resources.
 */
@WebServlet("/user/bookmarks")
public class BookmarksServlet extends HttpServlet {

    private final BookmarkService bookmarkService = new BookmarkService();

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

        List<Resource> bookmarkedResources = bookmarkService.getBookmarkedResourcesByUserId(user.getUserId());
        if (bookmarkedResources == null) bookmarkedResources = new ArrayList<>();

        req.setAttribute("bookmarkedResources", bookmarkedResources);
        req.setAttribute("totalBookmarks", bookmarkedResources.size());
        req.setAttribute("pageTitle", "My Bookmarks");
        req.getRequestDispatcher("/WEB-INF/views/user/bookmarks.jsp").forward(req, resp);
    }
}
