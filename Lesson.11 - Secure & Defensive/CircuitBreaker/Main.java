import java.util.function.Supplier;

/**
 * Retrying a dependency that is truly DOWN just wastes time and piles on load. A CIRCUIT
 * BREAKER wraps the call and tracks failures:
 *   - CLOSED    : calls pass through; count consecutive failures.
 *   - OPEN      : once failures cross a threshold, the breaker "trips" and SHORT-CIRCUITS —
 *                 calls fail FAST (return/throw instantly, no call made) for a cooldown period.
 *                 This frees your threads AND gives the dependency room to recover.
 *   - HALF-OPEN : after the cooldown, let ONE probe call through. Success → CLOSED (recovered);
 *                 failure → OPEN again (keep backing off).
 *
 * We implement a minimal breaker from scratch (no libraries) and drive a dependency that is
 * down, then recovers, so you can watch the state transitions. Time is simulated via a
 * manual clock so the demo is fast and deterministic.
 */
public class Main {

    enum State { CLOSED, OPEN, HALF_OPEN }

    static final class CircuitBreaker {
        private final int failureThreshold;    // consecutive failures to trip
        private final long cooldownMillis;     // how long to stay OPEN before probing
        private State state = State.CLOSED;
        private int consecutiveFailures = 0;
        private long openedAt = 0;

        CircuitBreaker(int failureThreshold, long cooldownMillis) {
            this.failureThreshold = failureThreshold;
            this.cooldownMillis = cooldownMillis;
        }

        State state() { return state; }

        // 'now' is injected so the demo can drive a deterministic clock.
        <T> T call(long now, Supplier<T> action, Supplier<T> fallback) {
            if (state == State.OPEN) {
                if (now - openedAt >= cooldownMillis) {
                    state = State.HALF_OPEN;                 // time to probe
                } else {
                    return fallback.get();                   // SHORT-CIRCUIT: fail fast, no call
                }
            }
            try {
                T result = action.get();                     // the real (protected) call
                onSuccess();
                return result;
            } catch (RuntimeException e) {
                onFailure(now);
                return fallback.get();
            }
        }

        private void onSuccess() {
            consecutiveFailures = 0;
            state = State.CLOSED;                            // recovered (or stayed healthy)
        }

        private void onFailure(long now) {
            consecutiveFailures++;
            if (state == State.HALF_OPEN || consecutiveFailures >= failureThreshold) {
                state = State.OPEN;                          // trip (or re-trip after a failed probe)
                openedAt = now;
            }
        }
    }

    public static void main(String[] args) {
        // Dependency: DOWN until simulated time >= 500ms, healthy afterwards.
        final long recoversAt = 500;
        java.util.function.LongFunction<Supplier<String>> dependencyAt = now -> () -> {
            if (now < recoversAt) throw new RuntimeException("dependency DOWN");
            return "OK";
        };
        Supplier<String> fallback = () -> "[fallback: cached/default value]";

        CircuitBreaker cb = new CircuitBreaker(/*threshold*/ 3, /*cooldown*/ 300);

        section("Drive the breaker across a simulated timeline (threshold=3, cooldown=300ms)");
        long[] clock = {0, 100, 200, 300, 450, 600, 750};   // simulated 'now' at each call
        for (long now : clock) {
            State before = cb.state();
            String result = cb.call(now, dependencyAt.apply(now), fallback);
            note(String.format("t=%4dms  state(before)=%-9s -> result=%-32s state(after)=%s",
                    now, before, "\"" + result + "\"", cb.state()));
        }

        section("What you just saw");
        note("- Calls 1-3 fail while CLOSED; on the 3rd consecutive failure the breaker TRIPS to OPEN.");
        note("- While OPEN (within cooldown), calls SHORT-CIRCUIT to the fallback instantly (no call).");
        note("- After the cooldown, the next call goes HALF-OPEN (a probe). The dependency has");
        note("  recovered, so the probe SUCCEEDS and the breaker resets to CLOSED.");

        section("Guidance");
        note("- A breaker protects BOTH sides: your threads don't block on a dead dependency, and the");
        note("  dependency isn't buried under retries while it tries to recover.");
        note("- Combine with timeouts (Example 15) + bounded retries (Example 16): timeout detects the");
        note("  failure fast, retry handles blips, the breaker handles a sustained outage.");
        note("- Real breakers trip on a FAILURE RATE over a rolling window, expose metrics, and often");
        note("  pair with a bulkhead + fallback (Example 18). Libraries: Resilience4j, Failsafe.");
    }

    // ---- tiny console helpers ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
