package example3_database;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * [ConcreteImplementorB] — Simulated SQL data store.
 * Mimics the behaviour of a relational database (with SQL-like logging).
 */
public class SqlDataStore implements DataStore {

    private final Map<Integer, User> table = new LinkedHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final String connectionUrl;

    public SqlDataStore(String connectionUrl) {
        this.connectionUrl = connectionUrl;
        System.out.println("  [SQL] Connected to " + connectionUrl);
    }

    @Override
    public void insert(User user) {
        int id = idGenerator.incrementAndGet();
        table.put(id, new User(id, user.getName(), user.getEmail()));
        System.out.println("  [SQL] INSERT INTO users (name, email) VALUES (?, ?) → id=" + id);
    }

    @Override
    public Optional<User> findById(int id) {
        System.out.println("  [SQL] SELECT * FROM users WHERE id = " + id);
        return Optional.ofNullable(table.get(id));
    }

    @Override
    public List<User> findAll() {
        System.out.println("  [SQL] SELECT * FROM users ORDER BY id");
        return table.values().stream()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(int id) {
        User removed = table.remove(id);
        if (removed != null) {
            System.out.println("  [SQL] DELETE FROM users WHERE id = " + id);
            return true;
        }
        return false;
    }

    @Override
    public int count() {
        System.out.println("  [SQL] SELECT COUNT(*) FROM users");
        return table.size();
    }

    @Override
    public String getStoreName() {
        return "SQL (" + connectionUrl + ")";
    }
}
