package com.mindease.model;

import java.sql.Timestamp;

/**
 * Represents an application user account.
 */
public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String status;
    private Timestamp createdAt;

    /**
     * Creates an empty user instance.
     */
    public User() {}

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
     * Gets the user name.
     *
     * @return user name
     */
    public String getName() { return name; }

    /**
     * Sets the user name.
     *
     * @param name user name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the email address.
     *
     * @return email address
     */
    public String getEmail() { return email; }

    /**
     * Sets the email address.
     *
     * @param email email address
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets the hashed password.
     *
     * @return password hash
     */
    public String getPassword() { return password; }

    /**
     * Sets the hashed password.
     *
     * @param password password hash
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Gets the phone number.
     *
     * @return phone number
     */
    public String getPhone() { return phone; }

    /**
     * Sets the phone number.
     *
     * @param phone phone number
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Gets the user role.
     *
     * @return role
     */
    public String getRole() { return role; }

    /**
     * Sets the user role.
     *
     * @param role role
     */
    public void setRole(String role) { this.role = role; }

    /**
     * Gets the account status.
     *
     * @return account status
     */
    public String getStatus() { return status; }

    /**
     * Sets the account status.
     *
     * @param status account status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Gets the account creation timestamp.
     *
     * @return creation timestamp
     */
    public Timestamp getCreatedAt() { return createdAt; }

    /**
     * Sets the account creation timestamp.
     *
     * @param createdAt creation timestamp
     */
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

