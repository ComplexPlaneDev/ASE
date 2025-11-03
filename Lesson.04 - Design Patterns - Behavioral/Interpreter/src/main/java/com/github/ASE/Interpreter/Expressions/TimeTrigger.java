package com.github.ASE.Interpreter.Expressions;

import java.time.LocalTime;

import com.github.ASE.Interpreter.Expression;
import com.github.ASE.Interpreter.SmartHomeContext;

public class TimeTrigger implements Expression {
    private final LocalTime triggerTime;
    private final Expression action;

    public TimeTrigger(LocalTime triggerTime, Expression action) {
        this.triggerTime = triggerTime;
        this.action = action;
    }

    @Override
    public void interpret(SmartHomeContext context) {
        if (context.getCurrentTime().isAfter(triggerTime.minusMinutes(1))
                && context.getCurrentTime().isBefore(triggerTime.plusMinutes(1))) {
            System.out.println("Triggered by time: " + triggerTime);
            action.interpret(context);
        }
    }
}
