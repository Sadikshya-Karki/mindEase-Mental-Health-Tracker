package com.mindease.service;

import com.mindease.dao.ContactMessageDAO;
import com.mindease.model.ContactMessage;

import java.util.List;

/**
 * Provides operations for user contact messages.
 */
public class ContactMessageService {
    private final ContactMessageDAO messageDAO = new ContactMessageDAO();

    /**
     * Creates a new contact message.
     *
     * @param message the contact message to create
     * @return the created message id
     */
    public int createMessage(ContactMessage message) {
        return messageDAO.createMessage(message);
    }

    /**
     * Retrieves messages for a specific user.
     *
     * @param userId the user id
     * @return the list of messages
     */
    public List<ContactMessage> getMessagesByUserId(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }

    /**
     * Retrieves a single message by id.
     *
     * @param messageId the message id
     * @return the message or null when not found
     */
    public ContactMessage getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    /**
     * Retrieves all messages.
     *
     * @return the list of all messages
     */
    public List<ContactMessage> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Stores an admin reply for a message.
     *
     * @param messageId the message id
     * @param adminReply the reply text
     * @return true when the reply is stored; false otherwise
     */
    public boolean replyToMessage(int messageId, String adminReply) {
        return messageDAO.replyToMessage(messageId, adminReply);
    }
}
