package com.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class Part3Test {
    
    private Message msg1, msg2, msg3, msg4, msg5;
    
    @BeforeEach
    public void setUp() {
        Message.resetAllData();
        
        msg1 = new Message("+27834557896", "Did you get the cake?", "John");
        msg1.sentMessageForTesting();
        
        msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "John");
        msg2.storeForTesting();
        
        msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.", "John");
        msg3.disregardForTesting();
        
        msg4 = new Message("0838884567", "It is dinner time !", "John");
        msg4.sentMessageForTesting();
        
        msg5 = new Message("+27838884567", "Ok, I am leaving without you.", "John");
        msg5.storeForTesting();
    }
    
    @Test
    public void testSentMessagesArrayPopulated() {
        List<Message> sentMessages = Message.getSentMessagesList();
        assertTrue(sentMessages.size() >= 2);
        
        boolean foundMessage1 = false;
        boolean foundMessage4 = false;
        
        for (Message msg : sentMessages) {
            if (msg.getMessageText().equals("Did you get the cake?")) {
                foundMessage1 = true;
            }
            if (msg.getMessageText().equals("It is dinner time !")) {
                foundMessage4 = true;
            }
        }
        
        assertTrue(foundMessage1);
        assertTrue(foundMessage4);
    }
    
    @Test
    public void testLongestMessage() {
        Message longest = Message.getLongestMessage();
        assertNotNull(longest);
        assertEquals("Where are you? You are late! I have asked you to be on time.", 
                     longest.getMessageText());
    }
    
    @Test
    public void testSearchByMessageID() {
        String msg4ID = msg4.getMessageId();
        Message found = Message.searchByMessageID(msg4ID);
        assertNotNull(found);
        assertEquals("It is dinner time !", found.getMessageText());
    }
    
    @Test
    public void testSearchByRecipient() {
        List<Message> results = Message.searchByRecipient("+27838884567");
        assertEquals(2, results.size());
        
        boolean foundMessage2 = false;
        boolean foundMessage5 = false;
        
        for (Message msg : results) {
            if (msg.getMessageText().equals("Where are you? You are late! I have asked you to be on time.")) {
                foundMessage2 = true;
            }
            if (msg.getMessageText().equals("Ok, I am leaving without you.")) {
                foundMessage5 = true;
            }
        }
        
        assertTrue(foundMessage2);
        assertTrue(foundMessage5);
    }
    
    @Test
    public void testDeleteByHash() {
        String hashToDelete = msg2.getMessageHash();
        boolean deleted = Message.deleteByHash(hashToDelete);
        assertTrue(deleted);
        
        Message found = Message.searchByMessageID(msg2.getMessageId());
        assertNull(found);
    }
}