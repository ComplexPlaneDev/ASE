package UnitTesting.doubles;

import UnitTesting.ports.FraudPolicy;

/**
 * DUMMY.
 *
 * A Dummy is passed to satisfy a parameter list but is never actually used
 * on the code path under test. Its methods should never be called; if they
 * are, that is a bug in either the test or the SUT, so we fail loudly.
 */
public class DummyFraudPolicy implements FraudPolicy {

    @Override
    public boolean isSuspicious(String fromAccountId, String toAccountId, long amountCents) {
        throw new AssertionError("DummyFraudPolicy should never be called on this path");
    }
}
