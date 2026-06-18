package com.mindease.controller;

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

/**
 * Handles admin resource management requests, including resource listing and maintenance actions.
 */
@WebServlet("/admin/resources")
public class AdminResourceServlet extends HttpServlet {

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
        if (!isAdmin(request, response)) return;

        String search = trimToNull(request.getParameter("search"));
        Integer categoryId = parseIntegerOrNull(request.getParameter("categoryId"));
        String status = trimToNull(request.getParameter("status"));

        if (search != null || categoryId != null || status != null) {
            request.setAttribute("resources", resourceService.getFilteredResources(search, categoryId, status));
        } else {
            request.setAttribute("resources", resourceService.getAllResources());
        }

        String editId = request.getParameter("editId");
        if (editId != null && !editId.isBlank()) {
            request.setAttribute("editResource", resourceService.getResourceById(parseInt(editId)));
        }

        request.setAttribute("categories", resourceService.getAllCategories());
        request.setAttribute("pageTitle", "Manage Resources");
        request.setAttribute("currentSearch", search);
        request.setAttribute("currentCategoryId", categoryId);
        request.setAttribute("currentStatus", status);
        request.getRequestDispatcher("/WEB-INF/views/admin/resources.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for this servlet and processes submitted form data.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if servlet processing fails
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        User loggedUser = getAdminUser(request, response);
        if (loggedUser == null) return;

        String action = request.getParameter("action");
        String redirectBase = request.getContextPath() + "/admin/resources";

        if ("delete".equals(action)) {
            boolean ok = resourceService.deleteResource(parseInt(request.getParameter("resourceId")));
            response.sendRedirect(redirectBase + (ok ? "?success=deleted" : "?error=true"));
            return;
        }
        if ("publish".equals(action)) {
            boolean ok = resourceService.updateStatus(parseInt(request.getParameter("resourceId")), "published");
            response.sendRedirect(redirectBase + (ok ? "?success=updated" : "?error=true"));
            return;
        }
        if ("draft".equals(action)) {
            boolean ok = resourceService.updateStatus(parseInt(request.getParameter("resourceId")), "draft");
            response.sendRedirect(redirectBase + (ok ? "?success=updated" : "?error=true"));
            return;
        }

        if (!"create".equals(action) && !"update".equals(action)) {
            response.sendRedirect(redirectBase + "?error=true");
            return;
        }

        Resource resource = new Resource();
        if ("update".equals(action)) {
            resource.setResourceId(parseInt(request.getParameter("resourceId")));
        }
        resource.setTitle(request.getParameter("title"));
        resource.setDescription(request.getParameter("description"));
        resource.setUrl(request.getParameter("url"));
        resource.setImageUrl(request.getParameter("imageUrl"));
        Integer catId = parseIntegerOrNull(request.getParameter("categoryId"));
        resource.setCategoryId(catId == null ? 0 : catId);
        resource.setStatus(request.getParameter("status"));
        resource.setReadTime(request.getParameter("readTime"));
        resource.setTags(request.getParameter("tags"));
        resource.setAddedBy(loggedUser.getUserId());

        resourceService.normalizeResource(resource);
        String validationError = resourceService.validateResource(resource);
        if (validationError != null) {
            request.setAttribute("error", validationError);
            request.setAttribute("resources", resourceService.getAllResources());
            request.setAttribute("categories", resourceService.getAllCategories());
            request.setAttribute("currentSearch", null);
            request.setAttribute("currentCategoryId", null);
            request.setAttribute("currentStatus", null);
            request.setAttribute("pageTitle", "Manage Resources");
            request.getRequestDispatcher("/WEB-INF/views/admin/resources.jsp").forward(request, response);
            return;
        }

        if ("create".equals(action)) {
            int id = resourceService.createResource(resource);
            response.sendRedirect(redirectBase + (id > 0 ? "?success=created" : "?error=true"));
            return;
        }

        boolean updated = resourceService.updateResource(resource);
        response.sendRedirect(redirectBase + (updated ? "?success=updated" : "?error=true"));
    }

    /**
     * Checks whether the current session belongs to an admin user.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return true when the user is an authenticated admin; false otherwise
     * @throws IOException if an I/O error occurs
     */
    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return getAdminUser(request, response) != null;
    }

    /**
     * Returns the authenticated admin user or redirects to login when unauthorized.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the admin user or null when unauthorized
     * @throws IOException if an I/O error occurs
     */
    private User getAdminUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;
        if (loggedUser == null || !"admin".equalsIgnoreCase(loggedUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        return loggedUser;
    }

    /**
     * Parses an integer from the input string, returning null when missing or invalid.
     *
     * @param value the raw string value
     * @return the parsed integer or null when invalid
     */
    private Integer parseIntegerOrNull(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Trims a string or returns null when the value is empty.
     *
     * @param value the raw string value
     * @return the trimmed string or null when empty
     */
    private String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
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
