package UnitTesting;

import UnitTesting.doubles.*;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.service.TransferService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CHAPTER 3 — SPY.
 *
 * A Spy records how it was called; the test inspects those recordings afterwards.
 * The verification lives in the TEST (assert*), which is what distinguishes a Spy
 * from a Mock.
 */
@DisplayName("Chapter 3 — Spy")
class Chapter3_SpyTest {

    @Test
    @DisplayName("Example 1: verify the notification was sent exactly once")
    void spy_recordsCallCount() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        var spy = new SpyNotificationService();
        var service = new TransferService(repo, spy, new StubClock(0L), new DummyFraudPolicy());

        service.transfer("A", "B", Money.euros(100));

        assertEquals(1, spy.callCount()); // assertion is in the test, not the double
    }

    @Test
    @DisplayName("Example 2: verify the arguments captured by the spy")
    void spy_capturesArguments() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        var spy = new SpyNotificationService();
        var service = new TransferService(repo, spy, new StubClock(0L), new DummyFraudPolicy());

        service.transfer("A", "B", Money.euros(250));

        SpyNotificationService.Call call = spy.lastCall();
        assertEquals("A", call.fromId());
        assertEquals("B", call.toId());
        assertEquals(Money.euros(250), call.amount());
    }

    @Test
    @DisplayName("Example 3: no notification is sent when the transfer fails")
    void spy_provesAbsenceOfCall() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(10)));   // too little
        repo.seed(new Account("B", Money.euros(0)));

        var spy = new SpyNotificationService();
        var service = new TransferService(repo, spy, new StubClock(0L), new DummyFraudPolicy());

        assertThrows(RuntimeException.class,
                () -> service.transfer("A", "B", Money.euros(100)));
        assertEquals(0, spy.callCount()); // spies can also verify a call did NOT happen
    }
}
