package com.mindease.service;

import com.mindease.dao.AppointmentDAO;
import com.mindease.model.Appointment;

import java.util.List;

/**
 * Provides appointment-related operations for users and admins.
 */
public class AppointmentService {
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    /**
     * Retrieves all appointments.
     *
     * @return the list of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    /**
     * Retrieves appointments for a specific user and optional status filter.
     *
     * @param userId the user id
     * @param statusFilter the status filter
     * @return the list of matching appointments
     */
    public List<Appointment> getAppointmentsByUserId(int userId, String statusFilter) {
        return appointmentDAO.getAppointmentsByUserId(userId, statusFilter);
    }

    /**
     * Creates a new appointment.
     *
     * @param appointment the appointment to create
     * @return true when the creation succeeds; false otherwise
     */
    public boolean createAppointment(Appointment appointment) {
        return appointmentDAO.createAppointment(appointment);
    }

    /**
     * Updates an appointment status and notes.
     *
     * @param apptId the appointment id
     * @param status the new status
     * @param notes the notes to record
     * @return true when the update succeeds; false otherwise
     */
    public boolean updateAppointmentStatus(int apptId, String status, String notes) {
        return appointmentDAO.updateAppointmentStatus(apptId, status, notes);
    }

    /**
     * Retrieves the appointment count for a user and status.
     *
     * @param userId the user id
     * @param status the status filter
     * @return the appointment count
     */
    public int getCountByUserId(int userId, String status) {
        return appointmentDAO.getCountByUserId(userId, status);
    }

    /**
     * Counts appointments by status.
     *
     * @param status the appointment status
     * @return the count of matching appointments
     */
    public int countByStatus(String status) {
        return appointmentDAO.countByStatus(status);
    }

    /**
     * Returns the number of pending appointments.
     *
     * @return the pending appointment count
     */
    public int getPendingAppointmentsCount() {
        return appointmentDAO.getPendingAppointmentsCount();
    }

    /**
     * Returns the number of approved sessions scheduled for today.
     *
     * @return the count of today's approved sessions
     */
    public int getTodayApprovedSessionsCount() {
        return appointmentDAO.getTodayApprovedSessionsCount();
    }

    /**
     * Returns the total appointment count for a user.
     *
     * @param userId the user id
     * @return the total appointment count
     */
    public int getAppointmentsCount(int userId) {
        return appointmentDAO.getAppointmentsCount(userId);
    }

    /**
     * Retrieves upcoming sessions for a user.
     *
     * @param userId the user id
     * @param limit the maximum number of sessions to return
     * @return the list of upcoming sessions
     */
    public List<Appointment> getUpcomingSessions(int userId, int limit) {
        return appointmentDAO.getUpcomingSessions(userId, limit);
    }

    /**
     * Returns the total appointment count.
     *
     * @return the total appointment count
     */
    public int getTotalAppointmentsCount() {
        return appointmentDAO.getTotalAppointmentsCount();
    }
}