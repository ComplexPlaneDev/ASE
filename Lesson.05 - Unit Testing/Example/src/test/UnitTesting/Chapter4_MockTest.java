package UnitTesting;

import UnitTesting.doubles.*;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.service.TransferService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CHAPTER 4 — MOCK (hand-written).
 *
 * A Mock carries its expectations internally: it fails on an unexpected call and
 * its verify() fails if an expected call never arrives. This is BEHAVIOR
 * verification — the specification of interactions lives inside the double.
 */
@DisplayName("Chapter 4 — Mock (hand-written)")
class Chapter4_MockTest {

    @Test
    @DisplayName("Example 1: the mock is satisfied by the exact expected call")
    void mock_expectationMet() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        var mock = new MockNotificationService();
        mock.expectNotifyTransfer("A", "B", Money.euros(100));

        var service = new TransferService(repo, mock, new StubClock(0L), new DummyFraudPolicy());
        service.transfer("A", "B", Money.euros(100));

        mock.verify(); // passes: expectation fulfilled
    }

    @Test
    @DisplayName("Example 2: the mock fails immediately on wrong arguments")
    void mock_wrongArguments_failsFast() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        var mock = new MockNotificationService();
        mock.expectNotifyTransfer("A", "B", Money.euros(999)); // wrong amount

        var service = new TransferService(repo, mock, new StubClock(0L), new DummyFraudPolicy());

        assertThrows(AssertionError.class,
                () -> service.transfer("A", "B", Money.euros(100)));
    }

    @Test
    @DisplayName("Example 3: verify() fails if the expected call never happens")
    void mock_missingCall_failsOnVerify() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(10)));  // transfer will fail → no notification
        repo.seed(new Account("B", Money.euros(0)));

        var mock = new MockNotificationService();
        mock.expectNotifyTransfer("A", "B", Money.euros(100));

        var service = new TransferService(repo, mock, new StubClock(0L), new DummyFraudPolicy());

        assertThrows(RuntimeException.class,
                () -> service.transfer("A", "B", Money.euros(100)));
        assertThrows(AssertionError.class, mock::verify); // expected call never arrived
    }
}
