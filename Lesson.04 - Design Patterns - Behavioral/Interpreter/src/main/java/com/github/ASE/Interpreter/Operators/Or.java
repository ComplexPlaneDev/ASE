package com.github.ASE.Interpreter.Operators;

import java.util.List;

import com.github.ASE.Interpreter.Expression;
import com.github.ASE.Interpreter.SmartHomeContext;
import com.github.ASE.Interpreter.Expressions.MotionTrigger;
import com.github.ASE.Interpreter.Expressions.TemperatureTrigger;

public class Or implements Expression {
    private final List<Expression> expressions;

    public Or(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public void interpret(SmartHomeContext context) {
        for (Expression expr : expressions) {
            // Simulate partial evaluation
            if (expr instanceof MotionTrigger && context.isMotionDetected()) {
                expr.interpret(context);
                break;
            } else if (expr instanceof TemperatureTrigger) {
                expr.interpret(context);
                break;
            }
        }
    }
}
