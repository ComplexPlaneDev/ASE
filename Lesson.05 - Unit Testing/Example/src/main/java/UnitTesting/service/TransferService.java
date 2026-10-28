package UnitTesting.service;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.ports.AccountRepository;
import UnitTesting.ports.AuditClock;
import UnitTesting.ports.FraudPolicy;
import UnitTesting.ports.NotificationService;

/**
 * The System Under Test (SUT) used throughout the lecture.
 *
 * It coordinates several Depended-On Components (DOCs):
 *   - AccountRepository   : load/persist accounts        (Fake / Stub / Mock)
 *   - NotificationService : tell the user about transfers (Spy / Mock)
 *   - AuditClock          : timestamp the operation       (Stub)
 *   - FraudPolicy         : optional check                (Dummy when unused)
 *
 * Because every collaborator is injected through the constructor
 * (Dependency Injection), each one can be swapped for a test double.
 */
public class TransferService {

    private final AccountRepository repository;
    private final NotificationService notifier;
    private final AuditClock clock;
    private final FraudPolicy fraudPolicy;

    private long lastTransferTimestamp;

    public TransferService(AccountRepository repository,
                           NotificationService notifier,
                           AuditClock clock,
                           FraudPolicy fraudPolicy) {
        this.repository = repository;
        this.notifier = notifier;
        this.clock = clock;
        this.fraudPolicy = fraudPolicy;
    }

    /**
     * Moves {@code amount} from one account to another.
     *
     * Note: on the "happy path" the {@link FraudPolicy} is NOT consulted
     * (only large transfers are screened). That is what lets us pass a Dummy
     * for it in the basic tests.
     */
    public void transfer(String fromId, String toId, Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Transfer amount cannot be negative");
        }

        Account from = repository.findById(fromId);
        Account to = repository.findById(toId);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Unknown account");
        }

        // Only "large" transfers are screened for fraud.
        if (amount.isGreaterThan(Money.euros(10_000))
                && fraudPolicy.isSuspicious(fromId, toId, amount.asCents())) {
            throw new IllegalStateException("Transfer blocked by fraud policy");
        }

        from.withdraw(amount);   // may throw InsufficientFundsException
        to.deposit(amount);

        repository.save(from);
        repository.save(to);

        lastTransferTimestamp = clock.nowEpochMillis();

        notifier.notifyTransfer(from, to, amount);
    }

    public long lastTransferTimestamp() {
        return lastTransferTimestamp;
    }
}
