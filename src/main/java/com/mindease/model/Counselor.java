package com.mindease.model;

/**
 * Represents a counselor profile used for appointment booking.
 */
public class Counselor {

    private int counselorId;
    private String name;
    private String specialization;
    private String email;
    private String phone;
    private String availableDays;
    private String status;

    /**
     * Creates an empty counselor instance.
     */
    public Counselor() {}

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
     * Gets the counselor name.
     *
     * @return counselor name
     */
    public String getName() { return name; }

    /**
     * Sets the counselor name.
     *
     * @param name counselor name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the counselor specialization.
     *
     * @return specialization
     */
    public String getSpecialization() { return specialization; }

    /**
     * Sets the counselor specialization.
     *
     * @param specialization specialization
     */
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    /**
     * Gets the counselor email.
     *
     * @return email address
     */
    public String getEmail() { return email; }

    /**
     * Sets the counselor email.
     *
     * @param email email address
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets the counselor phone number.
     *
     * @return phone number
     */
    public String getPhone() { return phone; }

    /**
     * Sets the counselor phone number.
     *
     * @param phone phone number
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Gets the counselor availability days.
     *
     * @return available days string
     */
    public String getAvailableDays() { return availableDays; }

    /**
     * Sets the counselor availability days.
     *
     * @param availableDays available days string
     */
    public void setAvailableDays(String availableDays) { this.availableDays = availableDays; }

    /**
     * Gets the counselor status.
     *
     * @return status
     */
    public String getStatus() { return status; }

    /**
     * Sets the counselor status.
     *
     * @param status status
     */
    public void setStatus(String status) { this.status = status; }
}

