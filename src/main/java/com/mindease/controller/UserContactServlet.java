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
 * Handles user contact form submissions and displays the contact page.
 */
@WebServlet("/user/contact")
public class UserContactServlet extends HttpServlet {

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
        User loggedUser = getLoggedUser(request);
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<ContactMessage> messages = contactMessageService.getMessagesByUserId(loggedUser.getUserId());
        request.setAttribute("messages", messages);
        request.setAttribute("pageTitle", "Contact Support");
        request.getRequestDispatcher("/WEB-INF/views/user/contact.jsp").forward(request, response);
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
            throws ServletException, IOException {
        User loggedUser = getLoggedUser(request);
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String category = trimOrEmpty(request.getParameter("category"));
        String subject = trimOrEmpty(request.getParameter("subject"));
        String message = trimOrEmpty(request.getParameter("message"));

        if (category.isEmpty() || subject.isEmpty() || message.isEmpty()) {
            request.setAttribute("error", "Please complete category, subject, and message.");
            request.setAttribute("formCategory", category);
            request.setAttribute("formSubject", subject);
            request.setAttribute("formMessage", message);
            doGet(request, response);
            return;
        }

        if (message.length() > 500) {
            request.setAttribute("error", "Message must be 500 characters or less.");
            request.setAttribute("formCategory", category);
            request.setAttribute("formSubject", subject);
            request.setAttribute("formMessage", message);
            doGet(request, response);
            return;
        }

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setUserId(loggedUser.getUserId());
        contactMessage.setCategory(category);
        contactMessage.setSubject(subject);
        contactMessage.setMessage(message);

        int messageId = contactMessageService.createMessage(contactMessage);
        if (messageId > 0) {
            response.sendRedirect(request.getContextPath() + "/user/contact?success=true");
        } else {
            request.setAttribute("error", "Unable to send message right now. Please try again.");
            request.setAttribute("formCategory", category);
            request.setAttribute("formSubject", subject);
            request.setAttribute("formMessage", message);
            doGet(request, response);
        }
    }

    /**
     * Gets the currently logged-in user from the session.
     *
     * @param request the HTTP request
     * @return the logged-in user or null when no session exists
     */
    private User getLoggedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (User) session.getAttribute("loggedUser");
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
