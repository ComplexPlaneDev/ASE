package UnitTesting.ports;

import UnitTesting.domain.Account;

/**
 * Repository port (a "Depended-On Component"). In production this talks to a database;
 * in tests we replace it with Fakes, Stubs, Mocks, etc.
 */
public interface AccountRepository {

    /** @return the account, or null if none exists with that id. */
    Account findById(String id);

    void save(Account account);
}
