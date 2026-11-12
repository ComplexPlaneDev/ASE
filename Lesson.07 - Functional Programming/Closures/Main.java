import java.util.*;
import java.util.function.*;

/**
 * EXAMPLE 07 — Closures & variable capture ("effectively final").
 *
 * A lambda CLOSES OVER the variables of its enclosing scope — it captures them. Java
 * captures LOCALS BY VALUE and requires them to be final or "effectively final" (assigned
 * exactly once). This example shows:
 *   - capture producing distinct, independent closures,
 *   - why a mutable local cannot be captured (compile error, described here),
 *   - the legal-but-discouraged workarounds (array cell / AtomicInteger), and why they
 *     reintroduce shared mutable state (breaking purity),
 *   - the functional alternative: fold state through the pipeline instead.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // Returns a counter-like closure by capturing the immutable start; each call to make()
    // yields an INDEPENDENT function that remembers its own 'start'.
    static Function<Integer, Integer> shiftBy(int start) {
        return x -> x + start;    // 'start' is captured by value (effectively final)
    }

    public static void main(String[] args) {
        section("Each closure captures its OWN copy of the environment");
        Function<Integer, Integer> plus3 = shiftBy(3);
        Function<Integer, Integer> plus8 = shiftBy(8);
        note("plus3.apply(10) = " + plus3.apply(10) + "   captured start=3");
        note("plus8.apply(10) = " + plus8.apply(10) + "   captured start=8 (independent)");

        section("Why a MUTABLE local cannot be captured");
        note("This does NOT compile:");
        note("    int n = 0;");
        note("    Runnable r = () -> n++;   // ERROR: n must be final or effectively final");
        note("Reason: the lambda may outlive the stack frame; Java captures a COPY,");
        note("so mutating 'n' after capture would silently diverge from the copy.");

        section("Legal workarounds — but they REINTRODUCE shared mutable state");
        int[] cell = {0};                         // a heap array whose reference is effectively final
        Runnable bump = () -> cell[0]++;          // mutates through the captured reference
        bump.run(); bump.run(); bump.run();
        note("array-cell trick: cell[0] = " + cell[0] + "   (works, but no longer pure/thread-safe)");

        java.util.concurrent.atomic.AtomicInteger counter = new java.util.concurrent.atomic.AtomicInteger();
        List.of("a", "b", "c").forEach(s -> counter.incrementAndGet());
        note("AtomicInteger trick: counter = " + counter.get() + "   (thread-safe, still a side effect)");

        section("The FUNCTIONAL alternative: fold state through the pipeline");
        long count = List.of("a", "b", "c", "d").stream().filter(s -> !s.isEmpty()).count();
        int total = List.of(1, 2, 3, 4).stream().reduce(0, Integer::sum);
        note("count via stream (no captured mutable state) = " + count);
        note("sum   via reduce  (state carried as accumulator) = " + total);
        note("");
        note("Prefer reduce/collect over mutating a captured cell: it keeps lambdas pure,");
        note("which is exactly what makes a pipeline safe to run in parallel (Example 13).");
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
