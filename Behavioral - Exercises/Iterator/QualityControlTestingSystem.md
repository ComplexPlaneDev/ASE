# Quality Control Testing System

## Problem Statement

You are tasked with developing a software system for a manufacturing company's quality control department. The system needs to process different types of product tests in a standardized way.

## Requirements

The company performs three types of tests on their products:

1. **Safety Tests**
2. **Performance Tests**
3. **Durability Tests**

Each test type contains multiple individual test cases. For example:
- Safety Tests might include: "Fire Resistance Check", "Chemical Safety Test", "Electrical Safety Test"
- Performance Tests might include: "Speed Test", "Efficiency Test", "Load Test"
- Durability Tests might include: "Wear Test", "Stress Test", "Environmental Test"

## Technical Requirements

Your system must be able to:

1. **Process any test suite uniformly** - The system should be able to go through all tests in any test suite without knowing the specific implementation details of each test type.

2. **Support different test suite types** - New types of test suites should be easily added without modifying existing code.

3. **Provide consistent access** - All test suites should provide the same interface for accessing individual tests.

## Expected Behavior

The system should be able to:
- Create different types of test suites (Safety, Performance, Durability)
- Process all tests in a suite one by one
- Handle multiple test suites in the same way regardless of their type
- Reset and reprocess test suites when needed
