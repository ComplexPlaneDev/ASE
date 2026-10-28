package UnitTesting.ports;

/**
 * Produces audit-log timestamps. A dependency whose real implementation is
 * non-deterministic (the wall clock), so tests often Stub it.
 */
public interface AuditClock {

    /** @return current time as epoch milliseconds. */
    long nowEpochMillis();
}
