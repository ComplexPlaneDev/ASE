package example3_database;

import java.util.List;
import java.util.Optional;

/**
 * [RefinedAbstraction] — Concrete class extending Repository.
 * Adds user-specific query methods on top of the generic repository.
 */
public class UserRepository extends Repository {

    public UserRepository(DataStore dataStore) {
        super(dataStore);
    }

    @Override
    public String describeRepository() {
        return "UserRepository backed by [" + getDataStore().getStoreName() + "]";
    }

    /** Domain-specific: find a user by email. */
    public Optional<User> findByEmail(String email) {
        List<User> all = listAll();
        return all.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /** Domain-specific: count active users (all in this example). */
    public int countActiveUsers() {
        return totalRecords();
    }
}
