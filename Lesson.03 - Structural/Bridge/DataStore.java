package example3_database;

import java.util.List;
import java.util.Optional;

/**
 * [Implementor] — Interface
 *
 * Defines the low-level data-access operations. The Abstraction
 * (Repository) composes these primitives into higher-level queries.
 *
 * This interface is technology-agnostic: it does not mention SQL,
 * in-memory maps, or any specific storage engine.
 */
public interface DataStore {

    void insert(User user);
    Optional<User> findById(int id);
    List<User> findAll();
    boolean deleteById(int id);
    int count();
    String getStoreName();
}
