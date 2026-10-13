package example3_database;

import java.util.List;
import java.util.Optional;

/**
 * Client — demonstrates the Bridge pattern with a Database Repository.
 *
 * The application code (this class) depends only on UserRepository
 * (the Abstraction). It does NOT know or care whether data is stored
 * in memory or in a SQL database. Swapping the backend requires
 * changing ONE line: the DataStore passed to the constructor.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Example 3: Database Repository (Bridge Pattern) ===\n");

        // ── Scenario A: In-Memory store ──
        System.out.println("--- Using In-Memory DataStore ---");
        UserRepository repoA = new UserRepository(new InMemoryDataStore());
        runScenario(repoA);

        System.out.println();

        // ── Scenario B: SQL store (same code, different backend) ──
        System.out.println("--- Using SQL DataStore ---");
        UserRepository repoB = new UserRepository(
                new SqlDataStore("jdbc:h2:mem:testdb"));
        runScenario(repoB);

        System.out.println("\n=== End of Example 3 ===");
    }

    /** Shared test scenario — works with ANY DataStore. */
    private static void runScenario(UserRepository repo) {
        System.out.println("Repository: " + repo.describeRepository());

        // Insert
        repo.save(new User(0, "Alice", "alice@example.com"));
        repo.save(new User(0, "Bob", "bob@example.com"));
        repo.save(new User(0, "Charlie", "charlie@example.com"));

        // Query by ID
        System.out.println("Find user id=2:");
        Optional<User> bob = repo.find(2);
        bob.ifPresentOrElse(
            u -> System.out.println("  Found: " + u),
            () -> System.out.println("  Not found")
        );

        // Query by email (domain-specific, RefinedAbstraction)
        System.out.println("Find by email 'alice@example.com':");
        Optional<User> alice = repo.findByEmail("alice@example.com");
        alice.ifPresentOrElse(
            u -> System.out.println("  Found: " + u),
            () -> System.out.println("  Not found")
        );

        // List all
        System.out.println("All users:");
        for (User u : repo.listAll()) {
            System.out.println("  " + u);
        }

        // Delete
        System.out.println("Delete user id=1:");
        repo.remove(1);

        // Count
        System.out.println("Total records: " + repo.countActiveUsers());
    }
}
