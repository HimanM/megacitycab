package com.example.megacitycab.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final String PHONE_REGEX = "^\\+?[1-9]\\d{1,14}$"; // E.164 format
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$"; // Alphanumeric with underscores, 3-20 characters
    private static final String NUMBER_REGEX = "^\\d+$"; // Only digits

    /**
     * Validates a phone number against the E.164 format.
     * @param phoneNumber The phone number to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Pattern.matches(PHONE_REGEX, phoneNumber);
    }

    /**
     * Validates a username.
     * @param username The username to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidUsername(String username) {
        return username != null && Pattern.matches(USERNAME_REGEX, username);
    }


    /**
     * Validates if a string represents a positive integer.
     * @param number The string to validate.
     * @return True if it represents a positive integer, false otherwise.
     */
    public static boolean isPositiveNumber(String number) {
        return number != null && Pattern.matches(NUMBER_REGEX, number);
    }

    /**
     * Validates if a string is not null and not empty.
     * @param value The string to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Validate password (8-20 characters, must include uppercase, lowercase, number, special character)
    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        return Pattern.matches(regex, password);
    }

    // Validate email
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return Pattern.matches(emailRegex, email);
    }

    // Validate NIC (e.g., numbers or specific formats)
    public static boolean isValidNIC(String nic) {
        return nic != null && nic.matches("^[0-9]{9}[vV]$|^[0-9]{12}$");
    }
}
