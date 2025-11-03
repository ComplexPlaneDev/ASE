# Scientific Calculator with History Management

## Problem Statement

You are tasked with developing a scientific calculator system that can perform various mathematical operations and maintain a history of calculations with the ability to undo operations.

## Requirements

### Core Functionality
1. The calculator should support basic mathematical operations (addition, subtraction, multiplication, division)
2. The calculator should maintain its current result value
3. Users should be able to perform sequential operations on the current result
4. The system must support an "undo" feature that restores the calculator to its previous state
5. Users should be able to undo multiple operations in sequence

### Specific Features
- **Current Value Display**: The calculator should always show the current result
- **Operation History**: Track all operations performed
- **Undo Mechanism**: Restore the calculator to any previous state
- **State Persistence**: Maintain the complete state of the calculator including current value and operation history

### Example Usage Scenario
```
Calculator starts with value: 0
User performs: add(10) → Current value: 10
User performs: multiply(5) → Current value: 50
User performs: subtract(20) → Current value: 30
User calls: undo() → Current value: 50 (back to after multiply)
User calls: undo() → Current value: 10 (back to after add)
User calls: undo() → Current value: 0 (back to initial state)
```
