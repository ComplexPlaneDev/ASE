# Text Message Processing System

## Problem Description

You are building a text message processing system for a communication platform. The system needs to handle basic text messages, but also provide various processing capabilities that can be applied to messages before they are sent or stored.

The core functionality should include:
- A basic text message that contains content and can be processed
- The ability to apply encryption to messages for security
- The ability to compress messages to save bandwidth
- The ability to add timestamps to messages for auditing
- The ability to apply multiple processing operations to the same message

## Requirements

1. Create a system that can handle basic text messages
2. Implement encryption processing that adds security to messages
3. Implement compression processing that reduces message size
4. Implement timestamp processing that adds time information
5. Allow combining multiple processing operations on the same message
6. Each processing operation should be able to work independently
7. The system should be extensible to add new processing types in the future

## Interface Requirements

Your solution must include:
- A `process()` method that returns the processed message content
- A `getSize()` method that returns the current size of the message

## Example Usage Scenario

```
Original message: "Hello World"
After encryption: "3nc5yp73d:Hello World"
After compression: "c0mpr3553d:3nc5yp73d:Hello World"
After timestamp: "[2023-10-15 14:30:22] c0mpr3553d:3nc5yp73d:Hello World"
```

## Notes

- Keep the implementation simple - encryption can just add a prefix
- Compression can just add a prefix to simulate compression
- Timestamps can be simulated with a fixed format
