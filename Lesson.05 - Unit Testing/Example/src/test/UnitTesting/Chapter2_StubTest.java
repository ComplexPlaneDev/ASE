package UnitTesting;

import UnitTesting.doubles.*;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.service.TransferService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CHAPTER 2 — STUB.
 *
 * A Stub returns canned answers so the SUT can run deterministically.
 * The AuditClock is stubbed to a fixed instant; the FraudPolicy is stubbed
 * to a fixed boolean to drive a specific branch.
 */
@DisplayName("Chapter 2 — Stub")
class Chapter2_StubTest {

    @Test
    @DisplayName("Example 1: StubClock makes the audit timestamp deterministic")
    void stubClock_freezesTime() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        var service = new TransferService(repo, new SpyNotificationService(),
                new StubClock(1_000L), new DummyFraudPolicy());

        service.transfer("A", "B", Money.euros(100));

        assertEquals(1_000L, service.lastTransferTimestamp()); // canned time
    }

    @Test
    @DisplayName("Example 2: a stubbed fraud policy drives the 'suspicious' branch")
    void stubbedFraudPolicy_blocksLargeTransfer() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(50_000)));
        repo.seed(new Account("B", Money.euros(0)));

        // Stub: always answers "suspicious"
        var service = new TransferService(repo, new SpyNotificationService(),
                new StubClock(0L), (from, to, cents) -> true);

        assertThrows(IllegalStateException.class,
                () -> service.transfer("A", "B", Money.euros(20_000)));
    }

    @Test
    @DisplayName("Example 3: a stub returning 'not suspicious' lets a large transfer through")
    void stubbedFraudPolicy_allowsLargeTransfer() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(50_000)));
        repo.seed(new Account("B", Money.euros(0)));

        var service = new TransferService(repo, new SpyNotificationService(),
                new StubClock(0L), (from, to, cents) -> false); // canned "clean"

        assertDoesNotThrow(() -> service.transfer("A", "B", Money.euros(20_000)));
        assertEquals(Money.euros(20_000), repo.findById("B").balance());
    }
}
