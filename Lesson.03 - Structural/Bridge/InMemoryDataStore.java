package example3_database;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * [ConcreteImplementorA] — In-memory data store.
 * Uses a ConcurrentHashMap for thread-safe storage.
 */
public class InMemoryDataStore implements DataStore {

    private final Map<Integer, User> store = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public void insert(User user) {
        int id = idGenerator.incrementAndGet();
        store.put(id, new User(id, user.getName(), user.getEmail()));
        System.out.println("  [InMemory] INSERT user id=" + id);
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<User> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(int id) {
        User removed = store.remove(id);
        if (removed != null) {
            System.out.println("  [InMemory] DELETE user id=" + id);
            return true;
        }
        return false;
    }

    @Override
    public int count() {
        return store.size();
    }

    @Override
    public String getStoreName() {
        return "In-Memory (ConcurrentHashMap)";
    }
}
