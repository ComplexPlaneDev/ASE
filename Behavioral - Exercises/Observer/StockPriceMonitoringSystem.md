# Stock Price Monitoring System

## Problem Statement

You are tasked with designing a system for a financial trading platform that monitors and displays stock prices in real-time. The system needs to handle multiple types of displays that show stock information to different users and systems.

## Requirements

### Core Functionality
- The system must track stock prices for multiple companies
- Stock prices change frequently throughout the trading day
- Multiple display components need to show current stock price, previous close, and percentage change

### Technical Requirements
- When a stock price changes, ALL relevant displays must be updated immediately
- The system should support adding new types of displays without modifying existing code
- Displays can be dynamically added or removed from the monitoring system
- Each display type shows information in its own format
- The stock data source should not need to know about specific display implementations

## Example Scenario

```
Initial State:
- AAPL: $150.00
- Trading Dashboard and Mobile Alert registered for AAPL updates

Price Update:
- AAPL price changes to $152.50

Expected Behavior:
- Trading Dashboard displays: "AAPL: $152.50 (+$2.50, +1.67%)"
- Mobile Alert displays: "ALERT: AAPL increased by 1.67%"
- Any other registered displays are automatically updated
```
