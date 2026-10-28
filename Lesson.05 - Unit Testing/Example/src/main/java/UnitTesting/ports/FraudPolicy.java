package UnitTesting.ports;

/**
 * A generic policy interface used to demonstrate the Dummy pattern:
 * the SUT requires it in its constructor but a given code path never calls it.
 */
public interface FraudPolicy {

    boolean isSuspicious(String fromAccountId, String toAccountId, long amountCents);
}
