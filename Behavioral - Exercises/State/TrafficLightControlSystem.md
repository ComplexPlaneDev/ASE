# Traffic Light Control System

## Problem Statement

You are tasked with designing and implementing a **Traffic Light Control System** for a busy intersection.

## Requirements

The traffic light system must handle the following scenarios:

### Basic Functionality
- The system controls a single traffic light with three colors: **Red**, **Yellow**, and **Green**
- The light cycles through these colors in a specific sequence
- Each color has different timing and behavior requirements
- The system must respond to manual override commands from traffic controllers

### Specific Behaviors

**When the light is Red:**
- Vehicles must stop completely
- The light stays red for 60 seconds
- Responds to "next" command by changing to Green
- Emergency override changes directly to Green
- Status displays "STOP - Do not proceed"

**When the light is Yellow:**
- Vehicles should prepare to stop
- The light stays yellow for 10 seconds
- Responds to "next" command by changing to Red
- Emergency override changes directly to Red
- Status displays "CAUTION - Prepare to stop"

**When the light is Green:**
- Vehicles may proceed
- The light stays green for 45 seconds
- Responds to "next" command by changing to Yellow
- Emergency override changes directly to Red
- Status displays "GO - Proceed with caution"

### Implementation Notes
- The actual timing implementation can be simulated or simplified
