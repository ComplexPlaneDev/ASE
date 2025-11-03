package com.github.ASE.Interpreter.Expressions;

import com.github.ASE.Interpreter.Expression;
import com.github.ASE.Interpreter.SmartHomeContext;

public class MotionTrigger implements Expression {
    private final Expression action;

    public MotionTrigger(Expression action) {
        this.action = action;
    }

    @Override
    public void interpret(SmartHomeContext context) {
        if (context.isMotionDetected()) {
            System.out.println("Motion detected!");
            action.interpret(context);
        }
    }
}
