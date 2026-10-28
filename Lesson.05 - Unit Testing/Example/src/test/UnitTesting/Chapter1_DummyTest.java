package UnitTesting;

import UnitTesting.doubles.*;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.ports.FraudPolicy;
import UnitTesting.service.TransferService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CHAPTER 1 — DUMMY.
 *
 * A Dummy fills a required parameter slot but is never invoked on the path
 * under test. Here, a normal (small) transfer never triggers fraud screening,
 * so the FraudPolicy dependency is a Dummy.
 */
@DisplayName("Chapter 1 — Dummy")
class Chapter1_DummyTest {

    @Test
    @DisplayName("Example 1: a small transfer never calls the fraud policy (pure dummy)")
    void smallTransfer_doesNotUseFraudPolicy() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        FraudPolicy dummy = new DummyFraudPolicy(); // throws if ever called

        var service = new TransferService(repo, new SpyNotificationService(),
                new StubClock(0L), dummy);

        // Must NOT throw the AssertionError from the dummy → proves it was untouched.
        assertDoesNotThrow(() -> service.transfer("A", "B", Money.euros(100)));
    }

    @Test
    @DisplayName("Example 2: a null-object dummy also documents 'this value is irrelevant'")
    void dummy_communicatesIrrelevance() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        // Reader immediately sees: "fraud policy plays no role in this scenario".
        FraudPolicy dummy = new DummyFraudPolicy();
        var service = new TransferService(repo, new SpyNotificationService(),
                new StubClock(0L), dummy);

        service.transfer("A", "B", Money.euros(10));
        assertEquals(Money.euros(490), repo.findById("A").balance());
    }

    @Test
    @DisplayName("Example 3: a lambda can serve as an inline dummy")
    void lambdaDummy() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        // Any FraudPolicy works as a dummy as long as the path never calls it.
        FraudPolicy inlineDummy = (from, to, cents) -> {
            throw new AssertionError("dummy must not be called");
        };
        var service = new TransferService(repo, new SpyNotificationService(),
                new StubClock(0L), inlineDummy);

        assertDoesNotThrow(() -> service.transfer("A", "B", Money.euros(1)));
    }
}
