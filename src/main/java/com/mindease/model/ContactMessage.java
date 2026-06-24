package com.mindease.model;

import java.sql.Timestamp;

/**
 * Represents a support or contact message submitted by a user.
 */
public class ContactMessage {

    private int messageId;
    private int userId;
    private String category;
    private String subject;
    private String message;
    private String status;
    private String adminReply;
    private Timestamp createdAt;
    private Timestamp repliedAt;
    private String userName;
    private String userEmail;

    /**
     * Creates an empty contact message instance.
     */
    public ContactMessage() {}

    /**
     * Gets the message id.
     *
     * @return message id
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Sets the message id.
     *
     * @param messageId message id
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets the user id that created the message.
     *
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id that created the message.
     *
     * @param userId user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the message category.
     *
     * @return message category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the message category.
     *
     * @param category message category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the message subject.
     *
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the message subject.
     *
     * @param subject subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the message body.
     *
     * @return message body
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message body.
     *
     * @param message message body
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the processing status.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the processing status.
     *
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the admin reply text.
     *
     * @return admin reply
     */
    public String getAdminReply() {
        return adminReply;
    }

    /**
     * Sets the admin reply text.
     *
     * @param adminReply admin reply
     */
    public void setAdminReply(String adminReply) {
        this.adminReply = adminReply;
    }

    /**
     * Gets when the message was created.
     *
     * @return creation timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets when the message was created.
     *
     * @param createdAt creation timestamp
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets when the message was replied to.
     *
     * @return reply timestamp
     */
    public Timestamp getRepliedAt() {
        return repliedAt;
    }

    /**
     * Sets when the message was replied to.
     *
     * @param repliedAt reply timestamp
     */
    public void setRepliedAt(Timestamp repliedAt) {
        this.repliedAt = repliedAt;
    }

    /**
     * Gets the name of the user who submitted the message.
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the name of the user who submitted the message.
     *
     * @param userName user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the email of the user who submitted the message.
     *
     * @return user email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the email of the user who submitted the message.
     *
     * @param userEmail user email
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

