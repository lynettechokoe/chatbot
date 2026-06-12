package com.chatapp;

import java.util.regex.Pattern;

/**
 * Login class handles user registration and authentication for the chat application.
 * 
 * Reference for regex pattern: 
 * Based on Java Regex documentation - https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
 * 
 * @author Your Full Name
 * @version 1.0
 */
public class Login {
    
    // These variables store the user's information after registration
    private String registeredUsername;
    private String registeredPassword;
    private String registeredPhoneNumber;
    private String firstName;
    private String lastName;
    
    /**
     * Method 1: Check if username is valid
     * Rule: Must contain underscore (_) AND be 5 characters or less
     */
    public boolean checkUserName(String username) {
        // If username is empty, return false
        if (username == null) {
            return false;
        }
        
        // Check both conditions: contains _ AND length <= 5
        boolean hasUnderscore = username.contains("_");
        boolean correctLength = username.length() <= 5;
        
        // Return true ONLY if BOTH conditions are true
        return hasUnderscore && correctLength;
    }
    
    /**
     * Method 2: Check if password meets complexity requirements
     * Rules: 
     * - At least 8 characters long
     * - Contains at least 1 capital letter (A-Z)
     * - Contains at least 1 number (0-9)
     * - Contains at least 1 special character (!@#$%^&* etc.)
     */
    public boolean checkPasswordComplexity(String password) {
        // First check: password must not be empty and must be at least 8 chars
        if (password == null || password.length() < 8) {
            return false;
        }
        
        // Variables to track if we found each requirement
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        // Loop through each character in the password
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            
            if (Character.isUpperCase(currentChar)) {
                hasCapital = true;  // Found a capital letter
            }
            else if (Character.isDigit(currentChar)) {
                hasNumber = true;   // Found a number
            }
            else if (!Character.isLetterOrDigit(currentChar)) {
                hasSpecial = true;  // Found a special character (not letter or number)
            }
        }
        
        // Return true ONLY if ALL THREE requirements are met
        return hasCapital && hasNumber && hasSpecial;
    }
    
    /**
     * Method 3: Check if cell phone number is valid South African number
     * Rule: Must start with +27 followed by exactly 9 digits
     * Example: +27831234567 (total 12 characters including +)
     */
    public boolean checkCellPhoneNumber(String phoneNumber) {
        // Check if phone number is empty
        if (phoneNumber == null) {
            return false;
        }
        
        // Regular expression pattern:
        // ^      - start of string
        // \\+    - plus sign (need double backslash because + is special)
        // 27     - South Africa country code
        // [0-9]{9} - exactly 9 digits
        // $      - end of string
        String regex = "^\\+27[0-9]{9}$";
        
        // Check if phone number matches the pattern
        return Pattern.matches(regex, phoneNumber);
    }
    
    /**
     * Method 4: Register a new user
     * Checks all validations and stores user data if valid
     */
    public String registerUser(String username, String password, 
                               String phoneNumber, String userFirstName, String userLastName) {
        
        // Check each validation rule
        boolean validUsername = checkUserName(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validPhone = checkCellPhoneNumber(phoneNumber);
        
        // If username is invalid, return error message
        if (!validUsername) {
            return "Username is not correctly formatted; please ensure that your username " +
                   "contains an underscore and is no more than five characters in length.";
        }
        
        // If password is invalid, return error message
        if (!validPassword) {
            return "Password is not correctly formatted; please ensure that the password " +
                   "contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        // If phone number is invalid, return error message
        if (!validPhone) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
        // ALL validations passed - save the user's information
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredPhoneNumber = phoneNumber;
        this.firstName = userFirstName;
        this.lastName = userLastName;
        
        // Return success message
        return "User successfully registered!";
    }
    
    /**
     * Method 5: Verify login credentials
     * Compares entered username/password with stored registration data
     */
    public boolean loginUser(String username, String password) {
        // If no user is registered yet, login fails
        if (registeredUsername == null) {
            return false;
        }
        
        // Check if entered username matches registered username
        // AND entered password matches registered password
        return registeredUsername.equals(username) && registeredPassword.equals(password);
    }
    
    /**
     * Method 6: Return login status message
     */
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            // Successful login
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            // Failed login
            return "Username or password incorrect, please try again.";
        }
    }
    
    // Getter methods - these allow other classes to access the stored data
    public String getRegisteredUsername() { 
        return registeredUsername; 
    }
    
    public String getRegisteredPassword() { 
        return registeredPassword; 
    }
    
    public String getRegisteredPhoneNumber() { 
        return registeredPhoneNumber; 
    }
    
    public String getFirstName() { 
        return firstName; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
}
