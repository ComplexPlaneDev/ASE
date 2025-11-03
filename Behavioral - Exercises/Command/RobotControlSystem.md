# Robot Control System

## Problem Description

You are developing a control system for an industrial robot that performs various operations in a manufacturing facility. The robot needs to execute different types of movements and actions based on commands received from operators or automated systems.

## Requirements

The system must handle the following operations:
- **Move Forward**: Robot moves forward by a specified distance
- **Move Backward**: Robot moves backward by a specified distance
- **Turn Left**: Robot rotates left by a specified angle
- **Turn Right**: Robot rotates right by a specified angle
- **Pick Up**: Robot picks up an object at current position
- **Drop**: Robot drops the currently held object

1. **Operation Queuing**: The system should be able to queue multiple operations to be executed in sequence
2. **Undo Functionality**: Each operation must be reversible - the system should track executed operations and provide an undo mechanism
3. **Logging**: All operations (both executed and undone) must be logged for audit purposes
4. **Flexibility**: New types of operations should be easy to add without modifying existing code

## Technical Requirements

- Operations should be stored and managed independently of the robot itself

Your task is to design and implement a flexible system that meets these requirements.
