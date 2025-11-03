package com.github.ASE.Interpreter;

import java.time.LocalTime;
import java.util.List;

import com.github.ASE.Interpreter.Expressions.Action;
import com.github.ASE.Interpreter.Expressions.MotionTrigger;
import com.github.ASE.Interpreter.Expressions.TemperatureTrigger;
import com.github.ASE.Interpreter.Expressions.TimeTrigger;
import com.github.ASE.Interpreter.Operators.And;

public class App {

    public static void main(String[] args) {
        // Simulated context (e.g., 7:00 PM, no motion, 68°F)
        SmartHomeContext context = new SmartHomeContext(LocalTime.now(), false, 68.0);

        // Rule: At 7 PM, turn on lights
        Expression rule1 = new TimeTrigger(LocalTime.of(23, 49), new Action("turn on", "lights"));

        // Rule: If motion detected, turn on lights and notify
        Expression rule2 = new MotionTrigger(
                new And(List.of(new Action("turn on", "lights"), new Action("notify", "Motion detected!"))));

        // Rule: If temperature drops below 60°F, turn on heater
        Expression rule3 = new TemperatureTrigger(60.0, true, new Action("turn on", "heater"));

        // Simulate time and conditions
        System.out.println("== Rule 1: Time-based trigger");
        context.setMotionDetected(false);
        context.setCurrentTemperature(68.0);
        rule1.interpret(context);

        System.out.println("\n== Rule 2: Motion-based trigger");
        context.setMotionDetected(true);
        rule2.interpret(context);

        System.out.println("\n== Rule 3: Temperature-based trigger");
        context.setCurrentTemperature(55.0);
        rule3.interpret(context);
    }
}
