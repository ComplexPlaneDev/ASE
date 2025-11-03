package com.github.ASE.Command;

public class Scheduler {
    public void schedule(Command command, long delayMs) {
        System.out.println("Scheduling task in " + delayMs + "ms");
        command.schedule(delayMs);
    }
}
