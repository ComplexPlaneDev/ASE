package UnitTesting.infrastructure;

import UnitTesting.ports.FraudPolicy;

/**
 * REAL implementation of {@link FraudPolicy}.
 *
 * A simple but genuine rule engine: flags a transfer as suspicious if it is
 * very large OR if money is being moved between two accounts that look like
 * they belong to the same owner (a common self-laundering heuristic).
 *
 * In the live demo this actually runs and makes a decision — unlike the DUMMY
 * used in the unit tests, which is never called at all on the happy path.
 */
public class RuleBasedFraudPolicy implements FraudPolicy {

    private static final long HARD_LIMIT_CENTS = 1_000_000_00L; // 1,000,000 EUR

    @Override
    public boolean isSuspicious(String fromAccountId, String toAccountId, long amountCents) {
        if (amountCents >= HARD_LIMIT_CENTS) {
            return true;
        }
        // Accounts sharing an owner prefix (e.g. "ACME-01" and "ACME-02").
        String fromOwner = ownerPrefix(fromAccountId);
        String toOwner = ownerPrefix(toAccountId);
        return fromOwner.equals(toOwner);
    }

    private String ownerPrefix(String accountId) {
        int dash = accountId.indexOf('-');
        return dash >= 0 ? accountId.substring(0, dash) : accountId;
    }
}
