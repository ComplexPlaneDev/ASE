package com.github.ASE.Closures;

public class App {

    public static void main(String[] args) {
        FinancialAlertSystem alertSystem = new FinancialAlertSystem();

        // Closure 1: Low balance alert
        double thresholdLow = 500.0;
        alertSystem.addRule(
                // Custom closure type: AccountCondition
                (Account acc) -> acc.getBalance() < thresholdLow,

                // Custom closure type: AccountAlertAction
                (Account acc, double amount) -> System.out
                        .println("[ALERT] Account " + acc.getAccountId() + " has low balance: $" + amount));

        // Closure 2: High balance alert
        double thresholdHigh = 10000.0;
        alertSystem.addRule((Account acc) -> acc.getBalance() > thresholdHigh,
                (Account acc, double amount) -> System.out
                        .println("[ALERT] Account " + acc.getAccountId() + " has high balance: $" + amount));

        // Simulate accounts
        Account acc1 = new Account("A123", 300);
        Account acc2 = new Account("B456", 15000);

        alertSystem.check(acc1); // Should trigger low balance
        alertSystem.check(acc2); // Should trigger high balance
    }
}
