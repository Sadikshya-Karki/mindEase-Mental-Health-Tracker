package com.mindease.controller;

import com.mindease.model.Appointment;
import com.mindease.model.Counselor;
import com.mindease.model.User;
import com.mindease.service.AppointmentService;
import com.mindease.service.CounselorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Handles appointment booking requests for authenticated users.
 */
@WebServlet("/user/book-appointment")
public class BookAppointmentServlet extends HttpServlet {

    private final CounselorService counselorService = new CounselorService();
    private final AppointmentService appointmentService = new AppointmentService();

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

        List<Counselor> counselors = counselorService.getAllActiveCounselors();
        req.setAttribute("counselors", counselors);
        req.setAttribute("pageTitle", "Book Appointment");
        req.getRequestDispatcher("/WEB-INF/views/user/book-appointment.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests for this servlet and processes submitted form data.
     *
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("loggedUser");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            int counselorId = Integer.parseInt(req.getParameter("counselorId"));
            String date = req.getParameter("apptDate");
            String time = req.getParameter("apptTime");
            String notes = req.getParameter("notes");

            Appointment appt = new Appointment();
            appt.setUserId(user.getUserId());
            appt.setCounselorId(counselorId);
            appt.setApptDate(Date.valueOf(date));
            appt.setApptTime(Time.valueOf(time + ":00"));
            appt.setNotes((notes == null || notes.isBlank()) ? null : notes.trim());
            appt.setStatus("pending");

            boolean success = appointmentService.createAppointment(appt);
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/user/appointments?success=true");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user/book-appointment?error=true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/error?message=Database+error");
            return;
        }
    }
}
