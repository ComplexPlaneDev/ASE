package UnitTesting.doubles;

import java.util.HashMap;
import java.util.Map;

import UnitTesting.domain.Account;
import UnitTesting.ports.AccountRepository;

/**
 * FAKE.
 *
 * A Fake has a real, working implementation — here an in-memory Map instead of
 * a real database. It behaves correctly for the purposes of the test, but takes
 * a shortcut (no persistence, no transactions) that makes it unsuitable for production.
 */
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, Account> store = new HashMap<>();

    @Override
    public Account findById(String id) {
        return store.get(id);
    }

    @Override
    public void save(Account account) {
        store.put(account.id(), account);
    }

    /** Test-only convenience seeding method. */
    public void seed(Account account) {
        store.put(account.id(), account);
    }

    public int size() {
        return store.size();
    }
}
