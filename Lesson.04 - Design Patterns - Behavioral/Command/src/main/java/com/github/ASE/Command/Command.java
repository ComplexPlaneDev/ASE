package com.github.ASE.Command;

public interface Command {
    void execute();

    void undo();

    void schedule(long delayMs);
}
