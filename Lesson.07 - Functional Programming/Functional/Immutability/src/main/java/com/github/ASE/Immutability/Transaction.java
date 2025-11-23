package com.github.ASE.Immutability;

import java.time.Instant;

public class Transaction {
    public enum Type {
        DEBIT, CREDIT
    }

    private final String id;
    private final Type type;
    private final double amount;
    private final String description;
    private final Instant timestamp;

    public Transaction(String id, Type type, double amount, String description, Instant timestamp) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f", timestamp, description, amount);
    }
}
