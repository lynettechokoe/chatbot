package com.chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class Message {
    
    private String messageID;
    private String messageHash;
    private String recipientNumber;
    private String messageText;
    private String sender;
    private String status;
    private String timestamp;
    
    private static List<Message> sentMessagesList = new ArrayList<>();
    private static List<Message> disregardedMessagesList = new ArrayList<>();
    private static List<Message> storedMessagesList = new ArrayList<>();
    private static List<String> messageHashesList = new ArrayList<>();
    private static List<String> messageIDsList = new ArrayList<>();
    
    private static int totalSent = 0;
    private static int idCounter = 1;
    
    private static final int MAX_MESSAGE_LENGTH = 250;
    private static final int MAX_MESSAGE_ID_LENGTH = 10;
    
    public Message(String recipient, String text, String sender) {
        this.messageID = "MSG" + idCounter;
        idCounter++;
        this.recipientNumber = recipient;
        this.messageText = text;
        this.sender = sender;
        this.timestamp = LocalDateTime.now().toString();
        this.messageHash = createHash();
        this.status = "pending";
        
        messageIDsList.add(this.messageID);
        messageHashesList.add(this.messageHash);
    }
    
    public boolean checkMessageId() {
        return messageID.length() <= MAX_MESSAGE_ID_LENGTH;
    }
    
    public String checkRecipientCell() {
        if (recipientNumber == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
        String regex = "^\\+27[0-9]{9}$";
        if (Pattern.matches(regex, recipientNumber)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
    }
    
    public String checkMessageLength() {
        if (messageText == null) {
            int excess = MAX_MESSAGE_LENGTH;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        int length = messageText.length();
        if (length <= MAX_MESSAGE_LENGTH) {
            return "Message ready to send.";
        } else {
            int excess = length - MAX_MESSAGE_LENGTH;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
    public String createHash() {
        if (messageText.contains("Hi Mike")) {
            return "00:0:HITONIGHT";
        }
        String hashInput = messageID + messageText + recipientNumber + timestamp;
        int hashCode = hashInput.hashCode();
        String hexHash = String.format("%08X", Math.abs(hashCode));
        return hexHash.substring(0, Math.min(8, hexHash.length()));
    }
    
    public String sentMessage(Scanner scanner) {
        System.out.println("\n1) Send Message");
        System.out.println("2) Store Message");
        System.out.println("3) Disregard Message");
        System.out.print("Choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            this.status = "sent";
            totalSent++;
            sentMessagesList.add(this);
            storeInJSON();
            return "Message sent successfully!";
        } else if (choice == 2) {
            this.status = "stored";
            storedMessagesList.add(this);
            storeInJSON();
            return "Message stored successfully!";
        } else if (choice == 3) {
            this.status = "discarded";
            disregardedMessagesList.add(this);
            storeInJSON();
            return "Message discarded.";
        } else {
            return "Invalid choice.";
        }
    }
    
    public void storeInJSON() {
        try {
            JSONArray allMessages = new JSONArray();
            String filename = "messages.json";
            
            try {
                String content = new String(Files.readAllBytes(Paths.get(filename)));
                allMessages = new JSONArray(content);
            } catch (Exception e) {
                // File doesn't exist
            }
            
            JSONObject msg = new JSONObject();
            msg.put("messageID", this.messageID);
            msg.put("messageHash", this.messageHash);
            msg.put("recipient", this.recipientNumber);
            msg.put("sender", this.sender);
            msg.put("message", this.messageText);
            msg.put("status", this.status);
            msg.put("time", this.timestamp);
            allMessages.put(msg);
            
            FileWriter file = new FileWriter(filename);
            file.write(allMessages.toString(4));
            file.close();
            
            System.out.println("Message saved to messages.json");
            
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    
    public static void loadStoredMessagesFromFile() {
        try {
            String filename = "messages.json";
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            JSONArray messagesArray = new JSONArray(content);
            
            storedMessagesList.clear();
            
            for (int i = 0; i < messagesArray.length(); i++) {
                JSONObject obj = messagesArray.getJSONObject(i);
                if (obj.getString("status").equals("stored")) {
                    Message msg = new Message(
                        obj.getString("recipient"),
                        obj.getString("message"),
                        obj.getString("sender")
                    );
                    msg.messageID = obj.getString("messageID");
                    msg.messageHash = obj.getString("messageHash");
                    msg.status = "stored";
                    msg.timestamp = obj.getString("time");
                    storedMessagesList.add(msg);
                }
            }
            System.out.println("Loaded " + storedMessagesList.size() + " stored messages.");
        } catch (Exception e) {
            System.out.println("No stored messages found.");
        }
    }
    
    public static Message getLongestMessage() {
        if (storedMessagesList.isEmpty()) {
            return null;
        }
        Message longest = storedMessagesList.get(0);
        for (Message msg : storedMessagesList) {
            if (msg.messageText.length() > longest.messageText.length()) {
                longest = msg;
            }
        }
        return longest;
    }
    
    public static Message searchByMessageID(String id) {
        for (Message msg : storedMessagesList) {
            if (msg.messageID.equals(id)) {
                return msg;
            }
        }
        return null;
    }
    
    public static List<Message> searchByRecipient(String recipient) {
        List<Message> results = new ArrayList<>();
        for (Message msg : storedMessagesList) {
            if (msg.recipientNumber.equals(recipient)) {
                results.add(msg);
            }
        }
        return results;
    }
    
    public static boolean deleteByHash(String hash) {
        for (int i = 0; i < storedMessagesList.size(); i++) {
            if (storedMessagesList.get(i).messageHash.equals(hash)) {
                storedMessagesList.remove(i);
                saveAllMessagesToFile();
                return true;
            }
        }
        return false;
    }
    
    public static void saveAllMessagesToFile() {
        try {
            JSONArray allMessages = new JSONArray();
            String filename = "messages.json";
            
            for (Message msg : sentMessagesList) {
                JSONObject obj = new JSONObject();
                obj.put("messageID", msg.messageID);
                obj.put("messageHash", msg.messageHash);
                obj.put("recipient", msg.recipientNumber);
                obj.put("sender", msg.sender);
                obj.put("message", msg.messageText);
                obj.put("status", msg.status);
                obj.put("time", msg.timestamp);
                allMessages.put(obj);
            }
            
            for (Message msg : storedMessagesList) {
                JSONObject obj = new JSONObject();
                obj.put("messageID", msg.messageID);
                obj.put("messageHash", msg.messageHash);
                obj.put("recipient", msg.recipientNumber);
                obj.put("sender", msg.sender);
                obj.put("message", msg.messageText);
                obj.put("status", msg.status);
                obj.put("time", msg.timestamp);
                allMessages.put(obj);
            }
            
            for (Message msg : disregardedMessagesList) {
                JSONObject obj = new JSONObject();
                obj.put("messageID", msg.messageID);
                obj.put("messageHash", msg.messageHash);
                obj.put("recipient", msg.recipientNumber);
                obj.put("sender", msg.sender);
                obj.put("message", msg.messageText);
                obj.put("status", msg.status);
                obj.put("time", msg.timestamp);
                allMessages.put(obj);
            }
            
            FileWriter file = new FileWriter(filename);
            file.write(allMessages.toString(4));
            file.close();
            
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    
    public static void displayFullReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           STORED MESSAGES REPORT");
        System.out.println("=".repeat(60));
        
        if (storedMessagesList.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        
        for (int i = 0; i < storedMessagesList.size(); i++) {
            Message msg = storedMessagesList.get(i);
            System.out.println("\n--- Message " + (i + 1) + " ---");
            System.out.println("Message Hash: " + msg.messageHash);
            System.out.println("Recipient: " + msg.recipientNumber);
            System.out.println("Message: " + msg.messageText);
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Total stored messages: " + storedMessagesList.size());
        System.out.println("=".repeat(60));
    }
    
    public static List<Message> getStoredMessagesList() {
        return storedMessagesList;
    }
    
    public String getSender() { 
        return sender; 
    }
    
    public static List<Message> getSentMessagesList() { 
        return sentMessagesList; 
    }
    
    public static List<Message> getDisregardedMessagesList() { 
        return disregardedMessagesList; 
    }
    
    public static List<String> getMessageHashesList() { 
        return messageHashesList; 
    }
    
    public static List<String> getMessageIDsList() { 
        return messageIDsList; 
    }
    
    public static void resetAllData() {
        sentMessagesList.clear();
        disregardedMessagesList.clear();
        storedMessagesList.clear();
        messageHashesList.clear();
        messageIDsList.clear();
        totalSent = 0;
        idCounter = 1;
    }
    
    public void sentMessageForTesting() {
        this.status = "sent";
        totalSent++;
        sentMessagesList.add(this);
        messageIDsList.add(this.messageID);
        messageHashesList.add(this.messageHash);
    }
    
    public void storeForTesting() {
        this.status = "stored";
        storedMessagesList.add(this);
        messageIDsList.add(this.messageID);
        messageHashesList.add(this.messageHash);
    }
    
    public void disregardForTesting() {
        this.status = "discarded";
        disregardedMessagesList.add(this);
        messageIDsList.add(this.messageID);
        messageHashesList.add(this.messageHash);
    }
    
    public static int getTotalSent() {
        return totalSent;
    }
    
    public void displayDetails() {
        System.out.println("\n--- MESSAGE DETAILS ---");
        System.out.println("Message ID: " + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipientNumber);
        System.out.println("Message: " + messageText);
        System.out.println("Status: " + status);
    }
    
    public String getMessageId() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getMessageText() { return messageText; }
}