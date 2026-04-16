package com.aluguel.util;

import java.util.regex.Pattern;

/**
 * Input Validation Utility
 * Provides methods to sanitize and validate user input
 * Prevents XSS, injection attacks, and other security vulnerabilities
 */
public class InputValidator {

    // Regex patterns for validation
    private static final Pattern SAFE_STRING = Pattern.compile("^[a-zA-Z0-9\\s\\-._@()&]*$");
    private static final Pattern SQL_INJECTION = Pattern.compile(".*(['\";\\\\]).*|.*(--|;|\\*|xp_|sp_).*", Pattern.CASE_INSENSITIVE);
    private static final Pattern XSS_PATTERN = Pattern.compile(".*(<|>|javascript:|onerror=|onclick=).*", Pattern.CASE_INSENSITIVE);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9\\-\\s()]+$");

    /**
     * Sanitize string input to remove potential XSS vectors
     */
    public static String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        
        return input
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;")
            .replace("/", "&#x2F;");
    }

    /**
     * Validate string is safe (no special characters)
     */
    public static boolean isValidString(String input, int maxLength) {
        if (input == null || input.isEmpty() || input.length() > maxLength) {
            return false;
        }
        
        // Check for XSS patterns
        if (XSS_PATTERN.matcher(input).matches()) {
            return false;
        }
        
        // Check for SQL injection patterns
        if (SQL_INJECTION.matcher(input).matches()) {
            return false;
        }
        
        return true;
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty() || email.length() > 255) {
            return false;
        }
        
        return EMAIL_PATTERN.matcher(email).matches() &&
               !XSS_PATTERN.matcher(email).matches() &&
               !SQL_INJECTION.matcher(email).matches();
    }

    /**
     * Validate phone number format
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty() || phone.length() > 20) {
            return false;
        }
        
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validate URL
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.isEmpty() || url.length() > 2048) {
            return false;
        }
        
        try {
            new java.net.URL(url);
            return !XSS_PATTERN.matcher(url).matches();
        } catch (java.net.MalformedURLException e) {
            return false;
        }
    }

    /**
     * Validate numeric input
     */
    public static boolean isValidNumber(String input, long min, long max) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        
        try {
            long value = Long.parseLong(input);
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Remove potentially dangerous characters
     */
    public static String removeSpecialCharacters(String input) {
        if (input == null) {
            return null;
        }
        
        return input.replaceAll("[^a-zA-Z0-9\\s\\-._@]", "");
    }

    /**
     * Validate CPF format (basic validation)
     */
    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }
        
        // Remove non-numeric characters
        cpf = cpf.replaceAll("\\D", "");
        
        // CPF must have 11 digits
        if (cpf.length() != 11) {
            return false;
        }
        
        // CPF cannot be all same digits
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        return true;
    }

    /**
     * Validate CNPJ format (basic validation)
     */
    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return false;
        }
        
        // Remove non-numeric characters
        cnpj = cnpj.replaceAll("\\D", "");
        
        // CNPJ must have 14 digits
        if (cnpj.length() != 14) {
            return false;
        }
        
        // CNPJ cannot be all same digits
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        
        return true;
    }

    /**
     * Escape string for database use (basic protection)
     * Note: Use parameterized queries/prepared statements instead!
     */
    public static String escapeForSQL(String input) {
        if (input == null) {
            return null;
        }
        
        return input.replace("'", "''")
                   .replace("\\", "\\\\")
                   .replace("\"", "\\\"");
    }

    /**
     * Limit string length
     */
    public static String limitLength(String input, int maxLength) {
        if (input == null) {
            return null;
        }
        
        return input.length() > maxLength ? input.substring(0, maxLength) : input;
    }
}
