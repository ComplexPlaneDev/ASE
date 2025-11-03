package com.github.ASE.Command.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.github.ASE.Command.Command;

public class Macro extends Schedulable {
    private final List<Command> commands = new ArrayList<>();
    private final Stack<Command> history = new Stack<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
            history.push(command);
        }
    }

    @Override
    public void undo() {
        while (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
        }
    }
}
