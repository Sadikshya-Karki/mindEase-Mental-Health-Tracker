package com.mindease.service;

import com.mindease.dao.UserDAO;
import com.mindease.model.User;

import java.util.List;

/**
 * Provides administrative operations for managing user accounts.
 */
public class AdminUserService {
    private final UserDAO userDAO = new UserDAO();

    /**
     * Retrieves all users.
     *
     * @return the list of all users
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * Retrieves users filtered by status.
     *
     * @param status the user status filter
     * @return the list of users matching the status
     */
    public List<User> getUsersByStatus(String status) {
        return userDAO.getUsersByStatus(status);
    }

    /**
     * Updates a user's status.
     *
     * @param userId the user id
     * @param status the new status
     * @return true when the update succeeds; false otherwise
     */
    public boolean updateUserStatus(int userId, String status) {
        return userDAO.updateUserStatus(userId, status);
    }

    /**
     * Returns the total number of users.
     *
     * @return the total user count
     */
    public int getTotalUsers() {
        return userDAO.getTotalUsers();
    }

    /**
     * Returns the number of users registered today.
     *
     * @return today's registration count
     */
    public int getTodayRegistrationsCount() {
        return userDAO.getTodayRegistrationsCount();
    }

    /**
     * Returns the number of active users.
     *
     * @return active user count
     */
    public int getActiveUsersCount() {
        return userDAO.getActiveUsersCount();
    }

    /**
     * Returns the number of pending users.
     *
     * @return pending user count
     */
    public int getPendingUsersCount() {
        return userDAO.getPendingUsersCount();
    }

    /**
     * Retrieves the most recent users.
     *
     * @param limit the maximum number of users to return
     * @return the list of recent users
     */
    public List<User> getRecentUsers(int limit) {
        return userDAO.getRecentUsers(limit);
    }
}
