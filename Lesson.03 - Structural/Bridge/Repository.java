package example3_database;

import java.util.List;
import java.util.Optional;

/**
 * [Abstraction] — Abstract class
 *
 * Defines the high-level repository interface that application code
 * interacts with. Holds a reference to a DataStore (the bridge) and
 * delegates all persistence operations to it.
 *
 * Application code depends on this abstraction, NOT on any specific
 * storage technology.
 */
public abstract class Repository {

    /** The bridge: reference to the Implementor. */
    private final DataStore dataStore;

    protected Repository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    protected DataStore getDataStore() {
        return dataStore;
    }

    // ── High-level operations (delegated to the store) ──

    public void save(User user) {
        dataStore.insert(user);
    }

    public Optional<User> find(int id) {
        return dataStore.findById(id);
    }

    public List<User> listAll() {
        return dataStore.findAll();
    }

    public boolean remove(int id) {
        return dataStore.deleteById(id);
    }

    public int totalRecords() {
        return dataStore.count();
    }

    /** Subclasses may override to add domain-specific behaviour. */
    public abstract String describeRepository();
}
