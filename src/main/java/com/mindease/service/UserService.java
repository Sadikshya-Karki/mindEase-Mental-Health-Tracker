package com.mindease.service;

import com.mindease.dao.UserDAO;
import com.mindease.model.User;
import com.mindease.util.PasswordUtil;
import com.mindease.util.ValidationUtil;

/**
 * Provides user account operations such as registration and login.
 */
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Registers a new user after validating input and applying defaults.
     *
     * @param user the user to register
     * @return a status message or "success" when registration completes
     */
    public String registerUser(User user) {
        String name = user.getName() == null ? null : user.getName().trim();
        String email = user.getEmail() == null ? null : user.getEmail().trim();

        user.setName(name);
        user.setEmail(email);

        if (ValidationUtil.isNullOrEmpty(name)) return "Name is required.";
        if (name.length() < 2) return "Name must be at least 2 characters.";
        if (!ValidationUtil.isValidEmail(email)) return "Invalid email address.";
        if (ValidationUtil.isNullOrEmpty(user.getPassword())) return "Password is required.";
        if (user.getPassword().length() < 6) return "Password must be at least 6 characters.";
        if (userDAO.emailExists(email)) return "Email already registered.";

        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        user.setRole("user");
        user.setStatus("pending");

        try {
            boolean created = userDAO.createUser(user);
            return created ? "success" : "Registration failed. Please try again.";
        } catch (RuntimeException e) {
            if ("DUPLICATE_PHONE".equals(e.getMessage())) {
                return "Phone number already registered. Please use a different number.";
            }
            return "Registration failed. Please try again.";
        }
    }

    /**
     * Authenticates a user by email and password.
     *
     * @param email the user email
     * @param password the user password
     * @return the authenticated user or null when authentication fails
     */
    public User loginUser(String email, String password) {
        if (ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(password)) return null;
        User user = userDAO.getUserByEmail(email.trim());
        if (user == null) return null;
        if (!PasswordUtil.checkPassword(password, user.getPassword())) return null;
        return user;
    }

    /**
     * Finds a user by email.
     *
     * @param email the user email
     * @return the user or null when not found
     */
    public User getUserByEmail(String email) {
        if (ValidationUtil.isNullOrEmpty(email)) return null;
        return userDAO.getUserByEmail(email.trim());
    }
}
