import java.util.function.Function;

/**
 * EXAMPLE 01 — Pure functions & referential transparency.
 *
 * A PURE function (a) depends only on its arguments and (b) has no observable side
 * effects. Two consequences we demonstrate:
 *   - Determinism: same input -> same output, always.
 *   - Referential transparency: the CALL can be replaced by its VALUE without changing
 *     the program's meaning. That is what makes memoization / caching legal.
 *
 * We contrast this with an impure counter whose result depends on hidden mutable state.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // PURE: output determined entirely by the input.
    static int square(int x) { return x * x; }

    // IMPURE: reads & mutates hidden state; same input, different output each call.
    private int calls = 0;
    int impureNextId(int ignored) { return ++calls; }

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        section("Determinism of a pure function");
        note("square(5) = " + square(5));
        note("square(5) = " + square(5) + "   (identical — no hidden state)");

        section("Impurity: result depends on hidden mutable state");
        note("impureNextId(0) = " + impureNextId(0));
        note("impureNextId(0) = " + impureNextId(0) + "   (differs — NOT referentially transparent)");

        section("Referential transparency legalises MEMOIZATION");
        Function<Integer, Integer> slowSquare = x -> { sleep(); return x * x; };
        Function<Integer, Integer> memoSquare = memoize(slowSquare);

        long t1 = time(() -> memoSquare.apply(9));   // computed once
        long t2 = time(() -> memoSquare.apply(9));   // served from cache
        note("first  memoSquare(9) took ~" + t1 + " ms (computed)");
        note("second memoSquare(9) took ~" + t2 + " ms (cached — legal ONLY because square is pure)");
        note("");
        note("Caching an IMPURE function would be a bug: you would hide its side effects");
        note("or return a stale value. Purity is the precondition for this optimisation.");
    }

    /** A memoizer is a higher-order function: it wraps a pure function with a cache. */
    static <A, B> Function<A, B> memoize(Function<A, B> f) {
        var cache = new java.util.HashMap<A, B>();
        return a -> cache.computeIfAbsent(a, f);
    }

    private static void sleep() { try { Thread.sleep(40); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }
    private static long time(Runnable r) { long s = System.nanoTime(); r.run(); return (System.nanoTime() - s) / 1_000_000; }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
