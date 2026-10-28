package UnitTesting;

import UnitTesting.doubles.InMemoryAccountRepository;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.service.TransferService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CHAPTER 5 — FAKE.
 *
 * A Fake has a genuine, working implementation but takes a shortcut unfit for
 * production — here an in-memory Map instead of a database. Fakes let us verify
 * end-to-end STATE across several operations without touching real infrastructure.
 */
@DisplayName("Chapter 5 — Fake")
class Chapter5_FakeTest {

    @Test
    @DisplayName("Example 1: the fake persists and retrieves updated balances")
    void fake_persistsState() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));
        repo.seed(new Account("B", Money.euros(0)));

        var service = new TransferService(repo, (f, t, a) -> {}, () -> 0L, (f, t, c) -> false);
        service.transfer("A", "B", Money.euros(200));

        // Reload through the fake — proves save() actually stored the new state.
        assertEquals(Money.euros(300), repo.findById("A").balance());
        assertEquals(Money.euros(200), repo.findById("B").balance());
    }

    @Test
    @DisplayName("Example 2: multiple transfers accumulate correctly through the fake")
    void fake_supportsMultipleOperations() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(1_000)));
        repo.seed(new Account("B", Money.euros(0)));

        var service = new TransferService(repo, (f, t, a) -> {}, () -> 0L, (f, t, c) -> false);
        service.transfer("A", "B", Money.euros(100));
        service.transfer("A", "B", Money.euros(250));
        service.transfer("B", "A", Money.euros(50));

        assertEquals(Money.euros(700), repo.findById("A").balance());
        assertEquals(Money.euros(300), repo.findById("B").balance());
    }

    @Test
    @DisplayName("Example 3: the fake behaves like the contract for a missing account")
    void fake_returnsNullForUnknownId() {
        var repo = new InMemoryAccountRepository();
        repo.seed(new Account("A", Money.euros(500)));

        var service = new TransferService(repo, (f, t, a) -> {}, () -> 0L, (f, t, c) -> false);

        // 'B' was never seeded → repository contract returns null → service rejects.
        assertThrows(IllegalArgumentException.class,
                () -> service.transfer("A", "B", Money.euros(100)));
        assertEquals(1, repo.size());
    }
}
