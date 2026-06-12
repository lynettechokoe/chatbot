
    package com.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Login class.
 * 
 * @author Your Full Name
 * @version 1.0
 */
public class LoginTest {
    
    private Login login;
    
    // This method runs before each test - creates a fresh Login object
    @BeforeEach
    public void setUp() {
        login = new Login();
    }
    
    // ========== TEST 1: Username correctly formatted ==========
    @Test
    public void testUsernameCorrectlyFormatted() {
        // Test Data: "kyl_1"
        boolean result = login.checkUserName("kyl_1");
        assertTrue(result, "Username 'kyl_1' should be valid");
        
        // Register and test full flow
        String registrationMsg = login.registerUser("kyl_1", "Ch&sec@ke99!", 
                                                      "+27838968976", "Kyle", "Smith");
        assertEquals("User successfully registered!", registrationMsg);
        
        String loginStatus = login.returnLoginStatus("kyl_1", "Ch&sec@ke99!");
        assertEquals("Welcome Kyle, Smith it is great to see you again.", loginStatus);
    }
    
    // ========== TEST 2: Username incorrectly formatted ==========
    @Test
    public void testUsernameIncorrectlyFormatted() {
        // Test Data: "kyle!!!!!!!"
        boolean result = login.checkUserName("kyle!!!!!!!");
        assertFalse(result, "Username 'kyle!!!!!!!' should be invalid");
        
        String registrationMsg = login.registerUser("kyle!!!!!!!", "Ch&sec@ke99!", 
                                                      "+27838968976", "Kyle", "Smith");
        assertEquals("Username is not correctly formatted; please ensure that your username " +
                    "contains an underscore and is no more than five characters in length.", 
                    registrationMsg);
    }
    
    // ========== TEST 3: Password meets complexity ==========
    @Test
    public void testPasswordMeetsComplexity() {
        // Test Data: "Ch&sec@ke99!"
        boolean result = login.checkPasswordComplexity("Ch&sec@ke99!");
        assertTrue(result, "Password 'Ch&sec@ke99!' should meet requirements");
        
        String registrationMsg = login.registerUser("kyl_1", "Ch&sec@ke99!", 
                                                      "+27838968976", "Kyle", "Smith");
        assertEquals("User successfully registered!", registrationMsg);
    }
    
    // ========== TEST 4: Password does NOT meet complexity ==========
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        // Test Data: "password"
        boolean result = login.checkPasswordComplexity("password");
        assertFalse(result, "Password 'password' should NOT meet requirements");
        
        String registrationMsg = login.registerUser("kyl_1", "password", 
                                                      "+27838968976", "Kyle", "Smith");
        assertEquals("Password is not correctly formatted; please ensure that the password " +
                    "contains at least eight characters, a capital letter, a number, and a special character.", 
                    registrationMsg);
    }
    
    // ========== TEST 5: Cell phone correctly formatted ==========
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        // Test Data: +27838968976
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue(result, "Phone number '+27838968976' should be valid");
        
        String registrationMsg = login.registerUser("kyl_1", "Ch&sec@ke99!", 
                                                      "+27838968976", "Kyle", "Smith");
        assertEquals("User successfully registered!", registrationMsg);
    }
    
    // ========== TEST 6: Cell phone incorrectly formatted ==========
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        // Test Data: 08966553
        boolean result = login.checkCellPhoneNumber("08966553");
        assertFalse(result, "Phone number '08966553' should be invalid");
        
        String registrationMsg = login.registerUser("kyl_1", "Ch&sec@ke99!", 
                                                      "08966553", "Kyle", "Smith");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", 
                    registrationMsg);
    }
    
    // ========== TEST 7: Login Successful ==========
    @Test
    public void testLoginSuccessful() {
        // First register a user
        login.registerUser("john_1", "Pass@1234", "+27831234567", "John", "Doe");
        
        // Then test successful login
        boolean result = login.loginUser("john_1", "Pass@1234");
        assertTrue(result, "Login should be successful with correct credentials");
        
        String status = login.returnLoginStatus("john_1", "Pass@1234");
        assertTrue(status.contains("Welcome"), "Login status should show welcome message");
    }
    
    // ========== TEST 8: Login Failed ==========
    @Test
    public void testLoginFailed() {
        // First register a user
        login.registerUser("jane_1", "MyPass@99", "+27839876543", "Jane", "Smith");
        
        // Then test failed login with wrong password
        boolean result = login.loginUser("jane_1", "wrongpassword");
        assertFalse(result, "Login should fail with incorrect password");
        
        String status = login.returnLoginStatus("jane_1", "wrongpassword");
        assertTrue(status.contains("incorrect"), "Login status should show failure message");
    }
    
    // ========== TEST 9: Boolean - Username correctly formatted ==========
    @Test
    public void testUsernameCorrectlyFormattedBoolean() {
        boolean result = login.checkUserName("test_1");
        assertTrue(result, "checkUserName should return true for 'test_1'");
    }
    
    // ========== TEST 10: Boolean - Username incorrectly formatted ==========
    @Test
    public void testUsernameIncorrectlyFormattedBoolean() {
        boolean result = login.checkUserName("invalid!");
        assertFalse(result, "checkUserName should return false for 'invalid!'");
    }
    
    // ========== TEST 11: Boolean - Password meets complexity ==========
    @Test
    public void testPasswordMeetsComplexityBoolean() {
        boolean result = login.checkPasswordComplexity("Strong@1");
        assertTrue(result, "checkPasswordComplexity should return true for 'Strong@1'");
    }
    
    // ========== TEST 12: Boolean - Password does NOT meet complexity ==========
    @Test
    public void testPasswordDoesNotMeetComplexityBoolean() {
        boolean result = login.checkPasswordComplexity("weak");
        assertFalse(result, "checkPasswordComplexity should return false for 'weak'");
    }
    
    // ========== TEST 13: Boolean - Cell phone correctly formatted ==========
    @Test
    public void testCellPhoneCorrectlyFormattedBoolean() {
        boolean result = login.checkCellPhoneNumber("+27831234567");
        assertTrue(result, "checkCellPhoneNumber should return true for valid number");
    }
    
    // ========== TEST 14: Boolean - Cell phone incorrectly formatted ==========
    @Test
    public void testCellPhoneIncorrectlyFormattedBoolean() {
        boolean result = login.checkCellPhoneNumber("0831234567");
        assertFalse(result, "checkCellPhoneNumber should return false for missing +27");
    }
}