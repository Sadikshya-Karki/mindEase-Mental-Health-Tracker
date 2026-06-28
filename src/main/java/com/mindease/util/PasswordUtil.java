package com.mindease.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility methods for hashing and verifying passwords.
 */
public class PasswordUtil {

    /**
     * Hashes a plaintext password using BCrypt.
     *
     * @param plainPassword the plaintext password
     * @return the hashed password
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Verifies a plaintext password against a BCrypt hash.
     *
     * @param plainPassword the plaintext password
     * @param hashedPassword the stored hash
     * @return true when the password matches; false otherwise
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
