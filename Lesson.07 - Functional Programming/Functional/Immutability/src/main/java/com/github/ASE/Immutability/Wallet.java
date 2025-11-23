package com.github.ASE.Immutability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Wallet {
    private final String ownerId;
    private final List<Transaction> transactions;
    private final List<BalanceSnapshot> balanceHistory;

    public Wallet(String ownerId, List<Transaction> transactions) {
        if (ownerId == null || transactions == null) {
            throw new IllegalArgumentException("Owner ID and transactions cannot be null");
        }

        this.ownerId = ownerId;
        this.transactions = Collections.unmodifiableList(new ArrayList<>(transactions));
        this.balanceHistory = computeBalanceHistory(transactions);
    }

    private List<BalanceSnapshot> computeBalanceHistory(List<Transaction> txs) {
        List<BalanceSnapshot> history = new ArrayList<>();
        double balance = 0;

        for (Transaction tx : txs) {
            if (tx.getType() == Transaction.Type.CREDIT) {
                balance += tx.getAmount();
            } else {
                balance -= tx.getAmount();
            }
            history.add(new BalanceSnapshot(tx.getTimestamp(), balance));
        }

        return Collections.unmodifiableList(history);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<BalanceSnapshot> getBalanceHistory() {
        return balanceHistory;
    }

    public double getCurrentBalance() {
        return balanceHistory.isEmpty() ? 0 : balanceHistory.get(balanceHistory.size() - 1).getBalance();
    }

    public Wallet addTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction);

        List<Transaction> updatedTxs = new ArrayList<>(transactions);
        updatedTxs.add(transaction);
        return new Wallet(ownerId, updatedTxs);
    }

    public Wallet addTransactions(List<Transaction> newTransactions) {
        Objects.requireNonNull(newTransactions);

        List<Transaction> updatedTxs = new ArrayList<>(transactions);
        updatedTxs.addAll(newTransactions);
        return new Wallet(ownerId, updatedTxs);
    }

    public Wallet replay(List<Transaction> events) {
        return new Wallet(ownerId, events);
    }

    @Override
    public String toString() {
        return "Wallet{" + "ownerId='" + ownerId + '\'' + ", balance=$" + String.format("%.2f", getCurrentBalance())
                + ", transactionCount=" + transactions.size() + '}';
    }
}
