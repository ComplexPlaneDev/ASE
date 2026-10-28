package UnitTesting.domain;

/**
 * Thrown when a withdrawal or transfer exceeds the available balance.
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String accountId, Money requested, Money available) {
        super("Account " + accountId + ": cannot take " + requested + ", only " + available + " available");
    }
}
