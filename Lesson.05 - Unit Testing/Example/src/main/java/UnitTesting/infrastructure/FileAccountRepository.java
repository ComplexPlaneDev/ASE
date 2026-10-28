package UnitTesting.infrastructure;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.ports.AccountRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * REAL implementation of {@link AccountRepository} — the kind of class you would
 * ship to production. It persists accounts to a file on disk, so data SURVIVES
 * across program runs.
 *
 * Contrast this with the test double {@code InMemoryAccountRepository} (a Fake):
 *   - This class does real I/O: slow, stateful, can fail (disk full, permissions).
 *   - It is exactly those properties that make it a poor choice INSIDE unit tests,
 *     and exactly why we substitute the Fake there.
 *
 * File format is a trivial line-based "id=cents" to keep the lecture dependency-free
 * (no JSON library needed).
 */
public class FileAccountRepository implements AccountRepository {

    private final Path file;

    public FileAccountRepository(Path file) {
        this.file = file;
    }

    @Override
    public Account findById(String id) {
        return load().get(id);
    }

    @Override
    public void save(Account account) {
        Map<String, Account> all = load();
        all.put(account.id(), account);
        persist(all);
    }

    // --- real file I/O below: the "expensive, side-effecting" part ---

    private Map<String, Account> load() {
        Map<String, Account> result = new HashMap<>();
        if (!Files.exists(file)) {
            return result;
        }
        try {
            for (String line : Files.readAllLines(file)) {
                if (line.isBlank()) continue;
                String[] parts = line.split("=", 2);
                String id = parts[0].trim();
                long cents = Long.parseLong(parts[1].trim());
                result.put(id, new Account(id, Money.ofCents(cents)));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read accounts file " + file, e);
        }
        return result;
    }

    private void persist(Map<String, Account> all) {
        StringBuilder sb = new StringBuilder();
        all.forEach((id, acc) -> sb.append(id).append('=').append(acc.balance().asCents()).append('\n'));
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            Files.writeString(file, sb.toString());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not write accounts file " + file, e);
        }
    }
}
