package UnitTesting.doubles;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.ports.NotificationService;

/**
 * MOCK (hand-written).
 *
 * A Mock is pre-programmed with EXPECTATIONS that form a specification of the
 * calls it should receive. Unlike a Spy, the mock verifies itself: it fails
 * immediately on an unexpected call, and {@link #verify()} fails if an expected
 * call never arrived. This is behavior verification baked into the double.
 */
public class MockNotificationService implements NotificationService {

    private String expectedFromId;
    private String expectedToId;
    private Money expectedAmount;

    private boolean expectationSet = false;
    private boolean satisfied = false;

    public void expectNotifyTransfer(String fromId, String toId, Money amount) {
        this.expectedFromId = fromId;
        this.expectedToId = toId;
        this.expectedAmount = amount;
        this.expectationSet = true;
    }

    @Override
    public void notifyTransfer(Account from, Account to, Money amount) {
        if (!expectationSet) {
            throw new AssertionError("Unexpected call: no notifyTransfer was expected");
        }
        if (!from.id().equals(expectedFromId)
                || !to.id().equals(expectedToId)
                || !amount.equals(expectedAmount)) {
            throw new AssertionError(String.format(
                    "Unexpected arguments. Expected (%s -> %s, %s) but got (%s -> %s, %s)",
                    expectedFromId, expectedToId, expectedAmount,
                    from.id(), to.id(), amount));
        }
        satisfied = true;
    }

    /** Call at the end of the test: fails if the expected call never happened. */
    public void verify() {
        if (expectationSet && !satisfied) {
            throw new AssertionError("Expected call to notifyTransfer was never made");
        }
    }
}
