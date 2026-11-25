import java.util.*;

/**
 * Transient failures (a brief blip, a 503, a reset connection) often succeed on retry. But
 * HOW you retry matters enormously:
 *   - NAIVE: retry immediately, in a tight loop, forever. Every client hammers a struggling
 *     dependency in lockstep → a "retry storm" that keeps it down (a self-inflicted DDoS).
 *   - RESILIENT: wait longer between each attempt (EXPONENTIAL backoff: 1s, 2s, 4s...), add
 *     randomness (JITTER) so clients don't retry in sync, and CAP the attempts (give up).
 *
 * Prerequisite: only retry IDEMPOTENT operations, or you may duplicate side effects.
 *
 * We simulate a dependency that fails its first 3 calls then succeeds (a transient outage),
 * and compare the two retry strategies. A seeded RNG keeps the jitter deterministic here.
 */
public class Main {

    // A dependency that is "down" for its first `failTimes` calls, then recovers.
    static final class FlakyDependency {
        private final int failTimes;
        private int calls = 0;
        FlakyDependency(int failTimes) { this.failTimes = failTimes; }
        String call() {
            calls++;
            if (calls <= failTimes) throw new RuntimeException("503 Service Unavailable (transient)");
            return "OK (recovered on call #" + calls + ")";
        }
        int totalCalls() { return calls; }
    }

    // NAIVE: immediate, unbounded retries — no delay, no cap. (We stop it artificially so the
    // demo terminates, but conceptually this loop is a retry storm with no backoff.)
    static String retryNaive(FlakyDependency dep, int safetyCap) {
        for (int attempt = 1; ; attempt++) {
            try {
                return dep.call();
            } catch (RuntimeException e) {
                note(String.format("   naive attempt %d failed -> retry IMMEDIATELY (no wait)", attempt));
                if (attempt >= safetyCap) return "gave up (naive, after " + attempt + " frantic tries)";
            }
        }
    }

    // RESILIENT: exponential backoff + jitter, bounded attempts.
    static String retryWithBackoff(FlakyDependency dep, int maxAttempts, long baseMillis,
                                   long maxMillis, Random rng) {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return dep.call();
            } catch (RuntimeException e) {
                if (attempt == maxAttempts) return "gave up after " + maxAttempts + " bounded attempts";
                long exp = Math.min(maxMillis, baseMillis * (1L << (attempt - 1)));   // 1x,2x,4x... capped
                long jitter = (long) (rng.nextDouble() * exp);                        // "full jitter"
                long delay = jitter;                                                  // random in [0, exp]
                note(String.format("   attempt %d failed -> backoff base=%dms cap=%dms -> sleep ~%dms (jittered)",
                        attempt, exp, maxMillis, delay));
                sleep(delay);
            }
        }
        return "unreachable";
    }

    public static void main(String[] args) {
        section("NAIVE retry: immediate, unbounded — a retry STORM against a sick service");
        FlakyDependency d1 = new FlakyDependency(3);
        String r1 = retryNaive(d1, 6);
        note("result: " + r1 + "   (made " + d1.totalCalls() + " calls, as fast as possible)");
        note("With many clients doing this, the dependency never gets room to recover.");

        section("RESILIENT retry: exponential backoff + jitter, capped attempts");
        FlakyDependency d2 = new FlakyDependency(3);
        Random seeded = new Random(7);   // seeded only to make this DEMO deterministic
        String r2 = retryWithBackoff(d2, 6, 100, 800, seeded);
        note("result: " + r2 + "   (made " + d2.totalCalls() + " calls, spaced out)");

        section("Guidance");
        note("- Retry ONLY transient failures, and ONLY idempotent operations (else duplicate effects).");
        note("- Exponential backoff gives the dependency time to recover; jitter de-synchronises clients.");
        note("- ALWAYS cap attempts and max delay — never retry forever.");
        note("- When retries are exhausted, fall back or trip a circuit breaker (Example 17), don't loop.");
        note("- In the demo we use base=100ms (not 1s) so it runs quickly; in production, tune to the SLA.");
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }

    // ---- tiny console helpers ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
