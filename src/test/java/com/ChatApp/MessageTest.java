package com.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    
    private Message message;
    
    @BeforeEach
    public void setup() {
        message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", "John");
    }
    
    @Test
    public void testMessageLengthSuccess() {
        String result = message.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    public void testMessageLengthFailure() {
        String longText = "A".repeat(300);
        Message longMsg = new Message("+27712345678", longText, "John");
        String result = longMsg.checkMessageLength();
        assertTrue(result.contains("exceeds 250 characters by 50"));
    }
    
    @Test
    public void testRecipientSuccess() {
        String result = message.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }
    
    @Test
    public void testRecipientFailure() {
        Message badMsg = new Message("08575975889", "Hello", "John");
        String result = badMsg.checkRecipientCell();
        assertTrue(result.contains("incorrectly formatted"));
    }
    
    @Test
    public void testMessageHash() {
        String hash = message.createHash();
        assertEquals("00:0:HITONIGHT", hash);
    }
    
    @Test
    public void testMessageIDLength() {
        assertTrue(message.checkMessageId());
        assertTrue(message.getMessageId().length() <= 10);
    }
}