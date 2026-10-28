package UnitTesting.doubles;

import java.util.ArrayList;
import java.util.List;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.ports.NotificationService;

/**
 * SPY.
 *
 * A Spy is a Stub that ALSO records how it was called, so the test can inspect
 * that information afterwards (state-based verification of an interaction).
 * Note the key difference from a Mock: the Spy records passively; the ASSERTIONS
 * live in the test, not in the double.
 */
public class SpyNotificationService implements NotificationService {

    public record Call(String fromId, String toId, Money amount) {}

    private final List<Call> calls = new ArrayList<>();

    @Override
    public void notifyTransfer(Account from, Account to, Money amount) {
        calls.add(new Call(from.id(), to.id(), amount));   // record, don't assert
    }

    public int callCount() {
        return calls.size();
    }

    public List<Call> calls() {
        return List.copyOf(calls);
    }

    public Call lastCall() {
        return calls.get(calls.size() - 1);
    }
}
