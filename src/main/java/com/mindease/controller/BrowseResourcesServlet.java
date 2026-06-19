package com.mindease.controller;

import com.mindease.model.Category;
import com.mindease.model.Resource;
import com.mindease.model.User;
import com.mindease.service.ResourceService;
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
 * Displays available wellness resources for browsing.
 */
@WebServlet("/user/resources")
public class BrowseResourcesServlet extends HttpServlet {

    private final ResourceService resourceService = new ResourceService();

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
        String searchQuery = request.getParameter("search");
        String categoryId = request.getParameter("category");
        String page = request.getParameter("page");

        searchQuery = searchQuery == null ? "" : searchQuery.trim();
        categoryId = categoryId == null ? "all" : categoryId.trim();
        if (categoryId.isEmpty()) {
            categoryId = "all";
        }

        int currentPage;
        try {
            currentPage = Integer.parseInt(page == null ? "1" : page.trim());
            if (currentPage < 1) {
                currentPage = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error?message=Database+error");
            return;
        }

        List<Category> allCategories = resourceService.getAllCategories();
        if (allCategories == null) {
            allCategories = new ArrayList<>();
        }

        List<Resource> resources = resourceService.getPublishedResources(searchQuery, categoryId, String.valueOf(currentPage), 9);
        if (resources == null) {
            resources = new ArrayList<>();
        }

        int totalResources = resourceService.getPublishedResourcesCount(searchQuery, categoryId);
        if (totalResources < 0) {
            totalResources = 0;
        }

        List<Integer> bookmarkedIds = resourceService.getBookmarkedResourceIdsByUserId(userId);
        if (bookmarkedIds == null) {
            bookmarkedIds = new ArrayList<>();
        }

        int totalPages = (int) Math.ceil(totalResources / 9.0);

        request.setAttribute("categories", allCategories);
        request.setAttribute("resources", resources);
        request.setAttribute("totalResources", totalResources);
        request.setAttribute("bookmarkedIds", bookmarkedIds);
        request.setAttribute("selectedCategory", categoryId);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("pageTitle", "Browse Resources");
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/WEB-INF/views/user/browse-resources.jsp").forward(request, response);
    }
}
