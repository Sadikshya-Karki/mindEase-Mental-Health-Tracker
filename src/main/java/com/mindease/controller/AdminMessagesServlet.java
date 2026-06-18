package com.mindease.controller;

import com.mindease.model.ContactMessage;
import com.mindease.model.User;
import com.mindease.service.ContactMessageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Handles admin message inbox requests and message processing actions.
 */
@WebServlet("/admin/messages")
public class AdminMessagesServlet extends HttpServlet {

    private final ContactMessageService contactMessageService = new ContactMessageService();

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
        if (!isAdmin(request, response)) {
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isBlank()) {
            int messageId = parseInt(idParam);
            if (messageId > 0) {
                ContactMessage viewMessage = contactMessageService.getMessageById(messageId);
                request.setAttribute("viewMessage", viewMessage);
            }
        } else {
            List<ContactMessage> allMessages = contactMessageService.getAllMessages();
            request.setAttribute("allMessages", allMessages);
        }

        request.setAttribute("pageTitle", "Messages");
        request.getRequestDispatcher("/WEB-INF/views/admin/messages.jsp").forward(request, response);
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
        if (!isAdmin(request, response)) {
            return;
        }

        int messageId = parseInt(request.getParameter("messageId"));
        String adminReply = trimOrEmpty(request.getParameter("adminReply"));

        if (messageId <= 0 || adminReply.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/messages?id=" + messageId + "&error=true");
            return;
        }

        boolean success = contactMessageService.replyToMessage(messageId, adminReply);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/messages?id=" + messageId + "&success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/messages?id=" + messageId + "&error=true");
        }
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
     * Parses an integer value, returning -1 when invalid.
     *
     * @param value the raw string value
     * @return the parsed integer or -1 when invalid
     */
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Trims a string value or returns an empty string when null.
     *
     * @param value the input string
     * @return the trimmed string or an empty string when null
     */
    private String trimOrEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}
