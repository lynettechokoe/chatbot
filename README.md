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