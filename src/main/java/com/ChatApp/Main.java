package com.chatapp;

import java.util.Scanner;
import java.util.List;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Login loginSystem = new Login();
    private static boolean loggedIn = false;
    
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("   WELCOME TO QUICKCHAT");
        System.out.println("=".repeat(50));
        
        register();
        login();
        
        if (loggedIn) {
            chatMenu();
        }
        
        scanner.close();
    }
    
    private static void register() {
        System.out.println("\n--- REGISTRATION ---");
        
        boolean done = false;
        while (!done) {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            
            System.out.print("Username (must have _ and <=5 chars): ");
            String username = scanner.nextLine();
            
            System.out.print("Password (8+ chars, 1 capital, 1 number, 1 special): ");
            String password = scanner.nextLine();
            
            System.out.print("Cell Phone (+27XXXXXXXXX): ");
            String phone = scanner.nextLine();
            
            String result = loginSystem.registerUser(username, password, phone, firstName, lastName);
            System.out.println(result);
            
            if (result.equals("User successfully registered!")) {
                done = true;
            }
        }
    }
    
    private static void login() {
        System.out.println("\n--- LOGIN ---");
        
        int attempts = 0;
        while (!loggedIn && attempts < 3) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            String status = loginSystem.returnLoginStatus(username, password);
            System.out.println(status);
            
            if (status.startsWith("Welcome")) {
                loggedIn = true;
            } else {
                attempts++;
            }
        }
        
        if (!loggedIn) {
            System.out.println("Too many attempts. Goodbye!");
            System.exit(0);
        }
    }
    
    private static void chatMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   WELCOME TO QUICKCHAT");
        System.out.println("=".repeat(50));
        
        System.out.print("\nHow many messages do you want to create? ");
        int maxMessages = scanner.nextInt();
        scanner.nextLine();
        
        Message[] messages = new Message[maxMessages];
        int messageCount = 0;
        
        boolean running = true;
        
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Send a Message");
            System.out.println("2) Show Sent Messages (Coming Soon)");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");
            System.out.print("Choose: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    if (messageCount < maxMessages) {
                        createMessage(messages, messageCount);
                        messageCount++;
                    } else {
                        System.out.println("You've reached your limit of " + maxMessages + " messages.");
                    }
                    break;
                    
                case 2:
                    System.out.println("\nComing Soon - This feature is still in development.");
                    break;
                    
                case 3:
                    storedMessagesMenu();
                    break;
                    
                case 4:
                    System.out.println("\nGoodbye! Total messages sent: " + Message.getTotalSent());
                    running = false;
                    break;
                    
                default:
                    System.out.println("\nInvalid choice. Pick 1, 2, 3, or 4.");
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SESSION SUMMARY");
        System.out.println("=".repeat(50));
        System.out.println("Total messages sent: " + Message.getTotalSent());
    }
    
    private static void createMessage(Message[] messages, int index) {
        System.out.println("\n--- NEW MESSAGE " + (index + 1) + " ---");
        
        System.out.print("Your name (sender): ");
        String sender = scanner.nextLine();
        
        System.out.print("Recipient number (+27XXXXXXXXX): ");
        String recipient = scanner.nextLine();
        
        System.out.print("Your message: ");
        String text = scanner.nextLine();
        
        Message msg = new Message(recipient, text, sender);
        
        System.out.println("\n" + msg.checkRecipientCell());
        System.out.println(msg.checkMessageLength());
        System.out.println("Message ID: " + msg.getMessageId());
        System.out.println("Message Hash: " + msg.getMessageHash());
        
        String result = msg.sentMessage(scanner);
        System.out.println(result);
        
        if (result.equals("Message sent successfully!")) {
            msg.displayDetails();
        }
        
        messages[index] = msg;
    }
    
    private static void storedMessagesMenu() {
        Message.loadStoredMessagesFromFile();
        
        boolean back = false;
        
        while (!back) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("STORED MESSAGES MENU");
            System.out.println("-".repeat(40));
            System.out.println("a) Display all stored messages (sender and recipient)");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message by ID");
            System.out.println("d) Search for messages by recipient");
            System.out.println("e) Delete a message by hash");
            System.out.println("f) Display full report");
            System.out.println("g) Back to Main Menu");
            System.out.print("Choose: ");
            
            String choice = scanner.nextLine().toLowerCase();
            
            switch (choice) {
                case "a":
                    displaySenderAndRecipient();
                    break;
                case "b":
                    displayLongestMessage();
                    break;
                case "c":
                    searchByMessageID();
                    break;
                case "d":
                    searchByRecipient();
                    break;
                case "e":
                    deleteByHash();
                    break;
                case "f":
                    Message.displayFullReport();
                    break;
                case "g":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    
    private static void displaySenderAndRecipient() {
        List<Message> stored = Message.getStoredMessagesList();
        if (stored.isEmpty()) {
            System.out.println("\nNo stored messages found.");
            return;
        }
        
        System.out.println("\n--- STORED MESSAGES (Sender & Recipient) ---");
        for (int i = 0; i < stored.size(); i++) {
            Message msg = stored.get(i);
            System.out.println((i + 1) + ". Sender: " + msg.getSender() + " | Recipient: " + msg.getRecipientNumber());
        }
    }
    
    private static void displayLongestMessage() {
        Message longest = Message.getLongestMessage();
        if (longest == null) {
            System.out.println("\nNo stored messages found.");
            return;
        }
        
        System.out.println("\n--- LONGEST STORED MESSAGE ---");
        System.out.println("Message: " + longest.getMessageText());
        System.out.println("Length: " + longest.getMessageText().length() + " characters");
        System.out.println("Recipient: " + longest.getRecipientNumber());
    }
    
    private static void searchByMessageID() {
        System.out.print("\nEnter Message ID to search (e.g., MSG1): ");
        String id = scanner.nextLine();
        
        Message found = Message.searchByMessageID(id);
        if (found != null) {
            System.out.println("\n--- MESSAGE FOUND ---");
            System.out.println("Recipient: " + found.getRecipientNumber());
            System.out.println("Message: " + found.getMessageText());
        } else {
            System.out.println("Message ID '" + id + "' not found.");
        }
    }
    
    private static void searchByRecipient() {
        System.out.print("\nEnter recipient number to search: ");
        String recipient = scanner.nextLine();
        
        List<Message> results = Message.searchByRecipient(recipient);
        if (results.isEmpty()) {
            System.out.println("No messages found for recipient: " + recipient);
        } else {
            System.out.println("\n--- MESSAGES FOR " + recipient + " ---");
            for (int i = 0; i < results.size(); i++) {
                Message msg = results.get(i);
                System.out.println((i + 1) + ". " + msg.getMessageText());
            }
        }
    }
    
    private static void deleteByHash() {
        System.out.print("\nEnter Message Hash to delete: ");
        String hash = scanner.nextLine();
        
        boolean deleted = Message.deleteByHash(hash);
        if (deleted) {
            System.out.println("Message successfully deleted.");
        } else {
            System.out.println("Message hash not found.");
        }
    }
}