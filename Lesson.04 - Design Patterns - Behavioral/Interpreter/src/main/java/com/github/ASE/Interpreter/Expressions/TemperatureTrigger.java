package com.github.ASE.Interpreter.Expressions;

import com.github.ASE.Interpreter.Expression;
import com.github.ASE.Interpreter.SmartHomeContext;

public class TemperatureTrigger implements Expression {
    private final double threshold;
    private final boolean isBelow;
    private final Expression action;

    public TemperatureTrigger(double threshold, boolean isBelow, Expression action) {
        this.threshold = threshold;
        this.isBelow = isBelow;
        this.action = action;
    }

    @Override
    public void interpret(SmartHomeContext context) {
        if ((isBelow && context.getCurrentTemperature() < threshold)
                || (!isBelow && context.getCurrentTemperature() > threshold)) {
            System.out.println("Temperature threshold triggered!");
            action.interpret(context);
        }
    }
}
