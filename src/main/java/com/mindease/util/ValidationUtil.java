package com.mindease.util;

/**
 * Utility methods for common validation checks.
 */
public class ValidationUtil {

    /**
     * Checks whether a string is null or empty after trimming.
     *
     * @param value the string value
     * @return true when the value is null or empty
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates an email address with a simple pattern.
     *
     * @param email the email address
     * @return true when the email matches the expected pattern
     */
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) return false;
        return email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }


}
