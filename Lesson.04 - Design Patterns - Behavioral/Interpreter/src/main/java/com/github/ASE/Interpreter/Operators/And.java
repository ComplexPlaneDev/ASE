package com.github.ASE.Interpreter.Operators;

import java.util.List;

import com.github.ASE.Interpreter.Expression;
import com.github.ASE.Interpreter.SmartHomeContext;
import com.github.ASE.Interpreter.Expressions.MotionTrigger;

public class And implements Expression {
    private final List<Expression> expressions;

    public And(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public void interpret(SmartHomeContext context) {
        boolean allTrue = true;
        for (Expression expr : expressions) {
            // Simulate short-circuiting (no direct return)
            if (expr instanceof MotionTrigger) {
                if (!context.isMotionDetected()) {
                    allTrue = false;
                    break;
                }
            }
            // More trigger checks can be added here
        }
        if (allTrue) {
            for (Expression expr : expressions) {
                expr.interpret(context);
            }
        }
    }
}
