# Expense Approval System

## Problem Description

You are tasked with building an expense approval system for a mid-sized company. The company has a hierarchical approval process where different expense amounts require approval from different levels of management.

## Business Rules

The company has the following approval authority structure:

1. **Team Lead**: Can approve expenses up to $500
2. **Department Manager**: Can approve expenses up to $2,000
3. **Director**: Can approve expenses up to $10,000
4. **CEO**: Can approve expenses above $10,000

## Requirements

When an expense request is submitted, the system should:

1. Route the request to the appropriate approver based on the expense amount
2. If an approver cannot handle the request (amount exceeds their authority), it should be escalated to the next level
3. The system should provide feedback on who approved the expense or if it was rejected
4. Handle the case where an expense amount exceeds even the CEO's typical approval range (treat as special case requiring board approval)

Your task is to design and implement a flexible system that can easily accommodate changes in the approval hierarchy or approval limits.
