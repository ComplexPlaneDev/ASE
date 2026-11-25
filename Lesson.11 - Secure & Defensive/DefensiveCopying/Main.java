import java.util.*;

/**
 * If a constructor stores a caller's MUTABLE object directly (or a getter returns the
 * internal one), the caller can mutate it AFTERWARDS and break your object's invariants from
 * the outside. This is a correctness AND security problem (e.g. bypassing a validated range,
 * or a validated access-control list). Defenses:
 *   - Copy mutable inputs IN (in the constructor), and copy mutable state OUT (in getters).
 *   - Copy BEFORE validating, to close the TOCTOU gap (check the copy you will keep).
 *   - Best of all: use IMMUTABLE types so there is nothing to copy.
 *
 * We use a classic Period(start,end) with a mutable Date, show the invariant being violated
 * from outside, then the copy-in/copy-out fix, then the immutable (java.time) version.
 */
public class Main {

    // VULNERABLE: stores and returns the caller's mutable Date references directly.
    static final class LeakyPeriod {
        private final Date start, end;
        LeakyPeriod(Date start, Date end) {
            if (start.after(end)) throw new IllegalArgumentException("start after end");
            this.start = start;   // stored AS-IS: caller still holds a reference
            this.end = end;
        }
        Date getStart() { return start; }   // hands the internal reference back out
    }

    // SAFE: copy on the way in (before the check) and on the way out.
    static final class SafePeriod {
        private final Date start, end;
        SafePeriod(Date start, Date end) {
            this.start = new Date(start.getTime());   // COPY first...
            this.end   = new Date(end.getTime());
            if (this.start.after(this.end))           // ...then validate the COPY we keep
                throw new IllegalArgumentException("start after end");
        }
        Date getStart() { return new Date(start.getTime()); }   // return a fresh copy
    }

    // BEST: immutable value type — nothing mutable escapes, no copying needed.
    record ImmutablePeriod(java.time.Instant start, java.time.Instant end) {
        ImmutablePeriod {
            if (start.isAfter(end)) throw new IllegalArgumentException("start after end");
        }
    }

    public static void main(String[] args) {
        section("VULNERABLE: an outside mutation breaks the object's invariant");
        Date s = new Date(1_000_000L), e = new Date(2_000_000L);
        LeakyPeriod leaky = new LeakyPeriod(s, e);
        note("before: start = " + leaky.getStart().getTime());
        s.setTime(9_999_999L);                 // mutate the SAME Date we passed in
        note("after s.setTime(): start = " + leaky.getStart().getTime() + "   <-- changed from outside!");
        leaky.getStart().setTime(0);           // mutate via the returned reference
        note("after getStart().setTime(0): start = " + leaky.getStart().getTime() + "   <-- mutated via getter!");

        section("SAFE: copy-in / copy-out seals the object off");
        Date s2 = new Date(1_000_000L), e2 = new Date(2_000_000L);
        SafePeriod safe = new SafePeriod(s2, e2);
        long before = safe.getStart().getTime();
        s2.setTime(9_999_999L);                // mutate the caller's Date...
        safe.getStart().setTime(0);            // ...and the returned copy
        note("start before meddling : " + before);
        note("start after  meddling : " + safe.getStart().getTime() + "   (unchanged — we kept private copies)");

        section("BEST: immutable types — nothing to copy, nothing to leak");
        ImmutablePeriod imm = new ImmutablePeriod(
                java.time.Instant.ofEpochSecond(1), java.time.Instant.ofEpochSecond(2));
        note("ImmutablePeriod = " + imm + "   (Instant is immutable; invariant can't be broken later)");

        section("Guidance");
        note("- Copy mutable constructor args BEFORE validating (close the TOCTOU window).");
        note("- Never return a reference to internal mutable state (Date, array, Collection) —");
        note("  return a copy, or an unmodifiable view (List.copyOf / Collections.unmodifiableList).");
        note("- Prefer immutable types (java.time.*, records, String) to avoid the problem entirely.");
    }

    // ---- tiny console helpers ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
