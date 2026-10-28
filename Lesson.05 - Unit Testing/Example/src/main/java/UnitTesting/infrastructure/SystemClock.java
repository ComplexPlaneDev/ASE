package UnitTesting.infrastructure;

import UnitTesting.ports.AuditClock;

/**
 * REAL implementation of {@link AuditClock}: reads the actual system clock.
 *
 * This is the textbook example of a NON-DETERMINISTIC dependency: every call
 * returns a different value, so a test that used it could never assert an exact
 * timestamp. That is precisely why tests use {@code StubClock} instead.
 */
public class SystemClock implements AuditClock {

    @Override
    public long nowEpochMillis() {
        return System.currentTimeMillis();
    }
}
