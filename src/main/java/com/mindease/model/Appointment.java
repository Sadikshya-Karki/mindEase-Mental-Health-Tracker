package com.mindease.model;

import java.sql.Date;
import java.sql.Time;

/**
 * Represents a counseling appointment between a user and a counselor.
 */
public class Appointment {

    private int apptId;
    private int userId;
    private int counselorId;
    private Date apptDate;
    private Time apptTime;
    private String status;
    private String notes;
    private String userName;
    private String counselorName;
    private String counselorSpecialization;

    /**
     * Creates an empty appointment instance.
     */
    public Appointment() {}

    /**
     * Gets the appointment id.
     *
     * @return appointment id
     */
    public int getApptId() { return apptId; }

    /**
     * Sets the appointment id.
     *
     * @param apptId appointment id
     */
    public void setApptId(int apptId) { this.apptId = apptId; }

    /**
     * Gets the user id.
     *
     * @return user id
     */
    public int getUserId() { return userId; }

    /**
     * Sets the user id.
     *
     * @param userId user id
     */
    public void setUserId(int userId) { this.userId = userId; }

    /**
     * Gets the counselor id.
     *
     * @return counselor id
     */
    public int getCounselorId() { return counselorId; }

    /**
     * Sets the counselor id.
     *
     * @param counselorId counselor id
     */
    public void setCounselorId(int counselorId) { this.counselorId = counselorId; }

    /**
     * Gets the appointment date.
     *
     * @return appointment date
     */
    public Date getApptDate() { return apptDate; }

    /**
     * Sets the appointment date.
     *
     * @param apptDate appointment date
     */
    public void setApptDate(Date apptDate) { this.apptDate = apptDate; }

    /**
     * Gets the appointment time.
     *
     * @return appointment time
     */
    public Time getApptTime() { return apptTime; }

    /**
     * Sets the appointment time.
     *
     * @param apptTime appointment time
     */
    public void setApptTime(Time apptTime) { this.apptTime = apptTime; }

    /**
     * Gets the appointment status.
     *
     * @return appointment status
     */
    public String getStatus() { return status; }

    /**
     * Sets the appointment status.
     *
     * @param status appointment status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Gets appointment notes.
     *
     * @return notes
     */
    public String getNotes() { return notes; }

    /**
     * Sets appointment notes.
     *
     * @param notes notes
     */
    public void setNotes(String notes) { this.notes = notes; }

    /**
     * Gets the user name for display.
     *
     * @return user name
     */
    public String getUserName() { return userName; }

    /**
     * Sets the user name for display.
     *
     * @param userName user name
     */
    public void setUserName(String userName) { this.userName = userName; }

    /**
     * Gets the counselor name for display.
     *
     * @return counselor name
     */
    public String getCounselorName() { return counselorName; }

    /**
     * Sets the counselor name for display.
     *
     * @param counselorName counselor name
     */
    public void setCounselorName(String counselorName) { this.counselorName = counselorName; }

    /**
     * Gets the counselor specialization for display.
     *
     * @return counselor specialization
     */
    public String getCounselorSpecialization() { return counselorSpecialization; }

    /**
     * Sets the counselor specialization for display.
     *
     * @param counselorSpecialization counselor specialization
     */
    public void setCounselorSpecialization(String counselorSpecialization) { this.counselorSpecialization = counselorSpecialization; }
}

