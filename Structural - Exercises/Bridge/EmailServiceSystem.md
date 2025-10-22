# Email Service System

## Problem Description

You need to design an email service system that can handle different email protocols and work with various email service providers. The system should be flexible enough to support new protocols and new providers without modifying existing code.

## Requirements

Your system must support:

**Email Protocols:**
- SMTP: Simple Mail Transfer Protocol for sending emails
- POP3: Post Office Protocol for retrieving emails
- IMAP: Internet Message Access Protocol for email management

**Service Providers:**
- Gmail: Google's email service with OAuth authentication
- Outlook: Microsoft's email service with Exchange integration
- Yahoo: Yahoo's email service with proprietary API

## Task

Design and implement a system where:

1. Each email protocol should have methods to:
   - `initialize()`: Set up the email protocol
   - `sendEmail(String message)`: Send an email message
   - `shutdown()`: Clean up protocol resources

2. Each service provider should provide:
   - `authenticate()`: Handle provider-specific authentication
   - `processEmail(String emailData)`: Process email through provider's infrastructure
   - `logout()`: Clean up provider session

3. The system should allow any email protocol to work with any service provider.

4. Adding a new protocol or a new provider should not require changes to existing classes.

5. For simplicity, the actual email processing can just print messages to the console indicating what operation is being performed.

## Expected Behavior

```
Setting up SMTP with Gmail...
Gmail: Authenticating with OAuth tokens
SMTP: Initializing mail transfer protocol
SMTP: Sending email: Hello from SMTP!
Gmail: Processing email through Google infrastructure
SMTP: Shutting down protocol
Gmail: Logging out from session

Setting up IMAP with Outlook...
Outlook: Authenticating with Exchange credentials
IMAP: Initializing message access protocol
IMAP: Sending email: Hello from IMAP!
Outlook: Processing email through Microsoft Exchange
```

Your solution should demonstrate how different email protocols can seamlessly work with different service providers.
