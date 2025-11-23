package com.github.ASE.Immutability;

import java.util.ArrayList;
import java.util.List;

public class WalletService {
    public static void printBalanceHistory(Wallet wallet) {
        System.out.println("\nBalance History:");
        for (int i = 0; i < wallet.getBalanceHistory().size(); i++) {
            BalanceSnapshot snap = wallet.getBalanceHistory().get(i);
            Transaction tx = wallet.getTransactions().get(i);
            System.out.printf("%s → %s\n", tx.toString(), snap.toString());
        }
    }

    public static Wallet merge(Wallet base, Wallet other) {
        List<Transaction> merged = new ArrayList<>(base.getTransactions());
        for (Transaction tx : other.getTransactions()) {
            if (!merged.contains(tx)) {
                merged.add(tx);
            }
        }
        return new Wallet(base.getOwnerId(), merged);
    }
}
