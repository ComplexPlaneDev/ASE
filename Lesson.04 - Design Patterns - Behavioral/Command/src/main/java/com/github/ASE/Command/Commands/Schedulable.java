package com.github.ASE.Command.Commands;

import com.github.ASE.Command.Command;

public abstract class Schedulable implements Command {
    @Override
    public void schedule(long delayMs) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMs);
                execute();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
