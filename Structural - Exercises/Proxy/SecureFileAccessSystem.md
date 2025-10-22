# Secure File Access System

## Problem Description

You are developing a file management system for a company where employees need to access different types of files. However, not all employees should have access to all files - some files contain sensitive information that should only be accessible to employees with appropriate security clearance.

Your system needs to:

1. Allow employees to read file contents
2. Control access based on employee security levels
3. Log all access attempts for security auditing
4. Provide clear feedback when access is denied

## Requirements

Create a system that can:

- Store and retrieve file contents
- Verify employee credentials and security clearance levels
- Grant or deny access based on file sensitivity and employee clearance
- Log all file access attempts (successful and failed)
- Maintain a consistent interface for file operations

## Security Levels

The system should support these security clearance levels (from lowest to highest):
- PUBLIC (Level 1)
- INTERNAL (Level 2)
- CONFIDENTIAL (Level 3)
- SECRET (Level 4)

## Expected Interface

Your solution should include these abstract methods:

```java
public abstract String readFile(String filename, String employeeId, int clearanceLevel);
public abstract void logAccess(String employeeId, String filename, boolean accessGranted);
```

## Implementation Notes

- File contents can be simple strings for this exercise
- Security levels can be represented as integers (1-4)
- Employee information can be stored in simple data structures
- Focus on the design pattern rather than complex security implementations
- Access should be denied if employee clearance is lower than file security level

## Test Cases

Your implementation should handle:
1. Successful file access by authorized employees
2. Access denial for employees with insufficient clearance
3. Logging of all access attempts
4. Different employees accessing the same file
5. Different files with various security levels
