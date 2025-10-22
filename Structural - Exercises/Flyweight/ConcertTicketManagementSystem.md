# Concert Ticket Management System

## Problem Description

You are tasked with designing a system for a large concert venue that needs to manage thousands of tickets for multiple concerts. The venue hosts different types of events with various seating arrangements and pricing tiers.

## Requirements

Your system must handle the following:

1. **Ticket Creation**: Create tickets for concerts with the following information:
   - Concert name
   - Venue location
   - Event date
   - Price tier (VIP, Premium, Standard, Economy)
   - Seat number
   - Ticket holder name

2. **Memory Efficiency**: The system will handle thousands of tickets simultaneously. For popular concerts, there might be 50,000+ tickets, many of which share common properties.

3. **Ticket Display**: Implement a method to display complete ticket information.

4. **Batch Operations**: Support creating multiple tickets efficiently for the same concert.

## Abstract Methods Required

Your solution should include these key methods:
- `displayTicket(String seatNumber, String ticketHolder)` - Display complete ticket information
- `getTicketInfo()` - Return basic ticket information
- `createTicket(...)` - Factory method for ticket creation

## Constraints

- Focus on memory efficiency when dealing with large numbers of tickets
- Keep the implementation simple - ticket display can just print to console
- Don't worry about persistence or database operations
- The main concern is how to structure the classes to minimize memory usage

## Test Scenario

Your solution should be able to:
- Create 1000+ tickets for the same concert efficiently
- Display individual ticket information including seat and holder details
- Demonstrate that memory usage is optimized for tickets sharing common properties

## Expected Output Example

```
Concert: Rock Festival 2024
Venue: Madison Square Garden
Date: 2024-07-15
Price Tier: VIP
Seat: A-23
Holder: John Smith
