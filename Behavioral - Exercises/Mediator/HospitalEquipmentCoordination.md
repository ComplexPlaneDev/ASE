# Hospital Equipment Coordination

## Problem Statement

You are tasked with designing a software system for coordinating medical equipment in a modern hospital. The hospital has various types of medical equipment that need to work together but should not directly communicate with each other for safety and maintenance reasons.

## Equipment Types

The system must handle the following types of medical equipment:

1. **MRI Scanner** - Performs magnetic resonance imaging
2. **X-Ray Machine** - Takes radiographic images
3. **Patient Monitor** - Monitors vital signs
4. **Ventilator** - Assists with patient breathing

## Coordination Requirements

The equipment must coordinate their operations according to these rules:

- When an **MRI Scanner** starts an operation, all other equipment in the same room must pause their operations due to magnetic interference
- When an **X-Ray Machine** is taking an image, **Patient Monitors** must temporarily switch to backup mode to avoid interference
- When a **Ventilator** detects an emergency, it must immediately alert all **Patient Monitors** to increase monitoring frequency
- When **Patient Monitors** detect critical readings, they must notify **Ventilators** to adjust settings if needed

## Technical Requirements

Your solution must include:

1. A base class or interface called `MedicalEquipment` with these abstract methods:
   - `startOperation()`
   - `pauseOperation()`
   - `receiveAlert(String alertType, String fromEquipment)`

2. Concrete equipment classes that extend/implement `MedicalEquipment`

3. A coordination mechanism that ensures equipment can communicate indirectly without being tightly coupled

## Implementation Guidelines

- Equipment should not have direct references to other equipment
- The system should be easily extensible to add new equipment types

## Expected Output

When you run your system, it should demonstrate:
- Equipment starting operations and coordinating with others
- Proper handling of interference scenarios
- Alert propagation between relevant equipment
- Clean separation of concerns between equipment types
