# Vehicle Inspection System

## Problem Description

You are tasked with designing a software system for a vehicle inspection facility that handles different types of vehicles and performs various types of inspections on them.

## Requirements

The system must handle three types of vehicles:
- **Car**: Standard passenger vehicles
- **Motorcycle**: Two-wheeled vehicles
- **Truck**: Commercial heavy-duty vehicles

The facility performs three types of inspections:
- **Safety Inspection**: Checks brakes, lights, tires, etc.
- **Emissions Inspection**: Tests exhaust emissions and environmental compliance
- **Registration Inspection**: Verifies documentation and legal compliance

## Key Constraints

1. **Extensibility**: The system should easily accommodate new types of inspections in the future without modifying existing vehicle classes.

2. **Type-Specific Behavior**: Each inspection type must behave differently depending on the vehicle type being inspected. For example:
   - Safety inspection on a car checks different components than on a motorcycle
   - Emissions testing varies significantly between vehicle types
   - Registration requirements differ for commercial vs. passenger vehicles

3. **Separation of Concerns**: Vehicle classes should focus on vehicle-specific data and behavior, while inspection logic should be kept separate.
