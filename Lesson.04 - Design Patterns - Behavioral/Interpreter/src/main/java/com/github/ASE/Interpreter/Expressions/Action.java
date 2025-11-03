package com.github.ASE.Interpreter.Expressions;

import com.github.ASE.Interpreter.Expression;
import com.github.ASE.Interpreter.SmartHomeContext;

public class Action implements Expression {
    private final String action;
    private final String target;

    public Action(String action, String target) {
        this.action = action;
        this.target = target;
    }

    @Override
    public void interpret(SmartHomeContext context) {
        switch (action.toLowerCase()) {
            case "turn on":
                context.turnOn(target);
                break;
            case "turn off":
                context.turnOff(target);
                break;
            case "lock":
                context.lock(target);
                break;
            case "unlock":
                context.unlock(target);
                break;
            case "notify":
                context.notifyUser(target);
                break;
            default:
                System.out.println("Unknown action: " + action);
        }
    }
}
