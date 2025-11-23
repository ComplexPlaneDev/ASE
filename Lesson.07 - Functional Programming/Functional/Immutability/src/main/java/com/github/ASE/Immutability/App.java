package com.github.ASE.Immutability;

import java.time.Instant;
import java.util.List;

public class App {

    public static void main(String[] args) {
        Transaction t1 = new Transaction("T1", Transaction.Type.CREDIT, 1000.0, "Initial deposit", Instant.now());
        Transaction t2 = new Transaction("T2", Transaction.Type.DEBIT, 250.0, "Grocery purchase",
                Instant.now().plusSeconds(60));

        // Build initial wallet
        Wallet wallet = new WalletBuilder().setOwnerId("U123").addTransaction(t1).addTransaction(t2).build();

        System.out.println("Initial Wallet:");
        System.out.println(wallet);
        WalletService.printBalanceHistory(wallet);

        // Add more transactions
        Transaction t3 = new Transaction("T3", Transaction.Type.CREDIT, 500.0, "Refund",
                Instant.now().plusSeconds(120));
        Wallet updatedWallet = wallet.addTransaction(t3);

        System.out.println("\nUpdated Wallet:");
        System.out.println(updatedWallet);
        WalletService.printBalanceHistory(updatedWallet);

        // Replay from scratch
        Wallet replayed = updatedWallet.replay(List.of(t1, t2, t3));
        System.out.println("\nReplayed Wallet (same result):");
        System.out.println(replayed);
        WalletService.printBalanceHistory(replayed);

        // Try modifying list directly
        try {
            updatedWallet.getTransactions().add(t1);
        } catch (UnsupportedOperationException e) {
            System.out.println("\nTried to mutate transaction list, got error as expected.");
        }
    }
}
