package com.github.ASE.Immutability;

import java.time.Instant;

public class BalanceSnapshot {
    private final Instant timestamp;
    private final double balance;

    public BalanceSnapshot(Instant timestamp, double balance) {
        this.timestamp = timestamp;
        this.balance = balance;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("Balance: $%.2f as of %s", balance, timestamp);
    }
}
