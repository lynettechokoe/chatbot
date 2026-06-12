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
## Stored Messages Menu Options

| Option | Function |
|--------|----------|
| a | Display all stored messages (sender and recipient) |
| b | Display the longest stored message |
| c | Search for a message by ID |
| d | Search for messages by recipient |
| e | Delete a message by hash |
| f | Display full report |
| g | Back to Main Menu |

---

## Unit Tests

### Part3Test.java - 5 Tests
1. testSentMessagesArrayPopulated - Verifies sent messages array
2. testLongestMessage - Finds longest stored message
3. testSearchByMessageID - Searches message by ID
4. testSearchByRecipient - Searches messages by recipient
5. testDeleteByHash - Deletes message using hash

All tests pass successfully.

---

## Coding Constructs Used

| Construct | Where Used |
|-----------|-------------|
| Variables | String, int, boolean, List, arrays |
| Variable Scope | Instance, static, local |
| Data Types | String, int, boolean, char |
| Classes | Login, Message, Main |
| Methods | Getters, setters, validation, storage |
| Operators | &&, ||, ==, != |
| Decisions | if-else, switch statements |
| Loops | while, for, for-each |
| Arrays | Message[], Lists, ArrayLists |
| File I/O | JSON file read/write |

---