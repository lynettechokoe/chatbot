# Lynettechokoe/chatbot
# ChatApp

A console-based chat application with registration, login, and messaging features.

## What I Built

### Part 1 - Registration and Login

- Created Login class with methods for validation
- Username validation: checks for underscore (_) and maximum 5 characters
- Password validation: checks for 8+ characters, capital letter, number, and special character
- Cell phone validation: checks for +27 international code followed by 9 digits
- Registration returns appropriate success or error messages
- Login system verifies username and password against stored credentials
- Created unit tests for all validation methods# ChatApp

A console-based chat application with registration, login, and messaging features.

### Part 2 - Messaging System

- Created Message class with all required methods
- Users can only send messages after successful login
- Menu system with options: Send Messages, Show Recent, Quit
- User defines how many messages to create at the start
- Message ID validation (max 10 characters)
- Recipient cell number validation (reused from Part 1)
 Message length validation (max 250 characters)
- Message hash generation using content and timestamp
- Options to send, store, or disregard each message
- JSON file storage for saved messages
- Displays message details in order: ID, Hash, Recipient, Message
- Total messages sent displayed at the end
- Created unit tests for Message class methods
 Test Values That Work

| Field | Enter This |
|-------|-------------|
| First Name | John |
| Last Name | Doe |
| Username | jo_1 |
| Password | MyPass@123 |
| Cell Phone | +27831234567 |


refencing
Java Regex Documentation: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html

JSON in Java: https://www.oracle.com/technical-resources/articles/java/json.html

JUnit 5 Documentation: https://junit.org/junit5/docs/current/user-guide/

 Part 3 - Data Storage and Reports
- Arrays populated with:
  - Sent Messages
  - Disregarded Messages
  - Stored Messages (loaded from JSON)
  - Message Hashes
  - Message IDs
   Stored Messages menu with options:
  - Display sender and recipient of all stored messages
  - Display the longest stored message
  - Search for message by ID
  - Search for messages by recipient
  - Delete message using message hash
  - Display full report of all stored messages
  ## Files in Project

| File | Description |
|------|-------------|
| Login.java | Registration and login validation |
| Message.java | Message creation, validation, arrays, JSON storage |
| Main.java | Main application with all menus |
| LoginTest.java | Unit tests for Login class |
| MessageTest.java | Unit tests for Message class |
| Part3Test.java | Unit tests for Part 3 features |
## How to Run

1. Open project in NetBeans
2. Clean and Build the project
3. Right-click Main.java → Run File
## Test Data Used

### Registration Test Data
| Field | Value |
|-------|-------|
| First Name | John |
| Last Name | Doe |
| Username | jo_1 |
| Password | MyPass@123 |
| Cell Phone | +27831234567 |
### Message Test Data

| Message | Recipient | Message Text | Action |
|---------|-----------|--------------|--------|
| 1 | +27834557896 | Did you get the cake? | Send |
| 2 | +27838884567 | Where are you? You are late! I have asked you to be on time. | Store |
| 3 | +27834484567 | Yohoooo, I am at your gate. | Disregard |
| 4 | 0838884567 | It is dinner time ! | Send |
| 5 | +27838884567 | Ok, I am leaving without you. | Store |
