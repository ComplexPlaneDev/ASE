package com.github.ASE.Immutability;

import java.util.ArrayList;
import java.util.List;

public class WalletBuilder {
    private String ownerId;
    private List<Transaction> transactions = new ArrayList<>();

    public WalletBuilder setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public WalletBuilder addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return this;
    }

    public Wallet build() {
        return new Wallet(ownerId, transactions);
    }
}
