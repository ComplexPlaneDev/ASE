package UnitTesting;

import java.nio.file.Path;
import java.time.Instant;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.infrastructure.ConsoleEmailNotificationService;
import UnitTesting.infrastructure.FileAccountRepository;
import UnitTesting.infrastructure.RuleBasedFraudPolicy;
import UnitTesting.infrastructure.SystemClock;
import UnitTesting.ports.AccountRepository;
import UnitTesting.ports.AuditClock;
import UnitTesting.ports.FraudPolicy;
import UnitTesting.ports.NotificationService;
import UnitTesting.service.TransferService;

/**
 * LIVE DEMO — the application running for real.
 *
 * This wires {@link TransferService} with its REAL, production collaborators:
 *   - FileAccountRepository          : persists accounts to disk (survives across runs)
 *   - ConsoleEmailNotificationService: "sends" a confirmation e-mail (a real side effect)
 *   - SystemClock                    : reads the live wall-clock
 *   - RuleBasedFraudPolicy           : evaluates a real fraud rule
 *
 * There are NO test doubles here — this is what the program does in production.
 * The test doubles (Dummy / Stub / Spy / Mock / Fake) live where they belong:
 * in the unit tests under src/test/java (Chapters 0–6).
 *
 * Run it with:  mvn -q compile exec:java
 * Run it TWICE to see persistence: the second run opens with the balances the
 * first run left on disk.
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println(" TransferService — LIVE (real production collaborators)");
        System.out.println("============================================================\n");

        Path dbFile = Path.of("target", "demo-accounts.txt");

        AccountRepository repo = new FileAccountRepository(dbFile);
        NotificationService notifier = new ConsoleEmailNotificationService();
        AuditClock clock = new SystemClock();
        FraudPolicy fraud = new RuleBasedFraudPolicy();

        // Seed accounts ONLY on the first ever run. On later runs the data is
        // loaded straight from disk — the real repository persists state.
        boolean firstRun = repo.findById("ALICE") == null;
        if (firstRun) {
            repo.save(new Account("ALICE", Money.euros(500)));
            repo.save(new Account("BOB", Money.euros(0)));
            System.out.println("State  : no file found — seeded ALICE=500, BOB=0 (first run).");
        } else {
            System.out.println("State  : loaded existing balances from disk (a PREVIOUS run left them).");
        }

        TransferService service = new TransferService(repo, notifier, clock, fraud);

        System.out.println("Before : ALICE=" + repo.findById("ALICE").balance()
                + ", BOB=" + repo.findById("BOB").balance());

        service.transfer("ALICE", "BOB", Money.euros(100));

        System.out.println("After  : ALICE=" + repo.findById("ALICE").balance()
                + ", BOB=" + repo.findById("BOB").balance());
        System.out.println("Clock  : lastTransferTimestamp = " + service.lastTransferTimestamp()
                + "  (" + Instant.ofEpochMilli(service.lastTransferTimestamp()) + ")");
        System.out.println("Disk   : accounts persisted to " + dbFile.toAbsolutePath());
        System.out.println("         -> re-running the program will find this data still there.");

        System.out.println("\n============================================================");
        System.out.println(" This is the real application. For how each collaborator is");
        System.out.println(" replaced by a test double in a unit test, see the Chapter");
        System.out.println(" tests under src/test/java.");
        System.out.println("============================================================");
    }
}
