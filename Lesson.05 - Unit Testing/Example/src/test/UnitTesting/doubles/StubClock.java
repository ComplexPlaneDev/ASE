package UnitTesting.doubles;

import UnitTesting.ports.AuditClock;

/**
 * STUB.
 *
 * A Stub provides canned answers to calls made during the test. It contains no
 * verification logic — it just returns whatever the test told it to return,
 * turning a non-deterministic dependency (the wall clock) into a deterministic one.
 */
public class StubClock implements AuditClock {

    private final long fixedTime;

    public StubClock(long fixedTime) {
        this.fixedTime = fixedTime;
    }

    @Override
    public long nowEpochMillis() {
        return fixedTime;   // canned answer
    }
}
