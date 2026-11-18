/**
 * EXAMPLE 03 — Observing Garbage Collection.
 *
 * We deliberately create allocation pressure (lots of short-lived arrays) and watch
 * the heap grow and then shrink after a GC. This demonstrates the weak generational
 * hypothesis in action: the vast majority of these objects die young.
 *
 * Run (JDK 21+):  java App.java
 * Best viewed with GC logging:
 *     java -Xlog:gc -Xmx128m App.java
 */
public class App {

    public static void main(String[] args) {
        of("Baseline heap");
        heap("start");

        of("Allocate ~200 MiB of SHORT-LIVED objects (each dies immediately)");
        long checksum = 0;
        for (int i = 0; i < 200; i++) {
            byte[] block = new byte[1 << 20];   // 1 MiB, becomes garbage next iteration
            block[0] = (byte) i;                // touch it so JIT can't optimise it away
            checksum += block[0];
            if (i % 50 == 0) heap("after " + i + " MiB");
        }
        note("checksum (prevents dead-code elimination): " + checksum);

        of("Suggest a GC and observe reclamation");
        heap("before gc");
        suggestGc();
        heap("after gc");

        note("");
        note("Observation: 'used' rises during allocation, then drops after GC.");
        note("Short-lived objects are collected cheaply by minor (young-gen) GCs.");
        note("Run with -Xlog:gc to see each pause and how little time it costs.");

        of("Long-lived objects behave differently — they get PROMOTED");
        // Keep references so these survive; they'd be tenured into the old generation.
        byte[][] survivors = new byte[16][];
        for (int i = 0; i < survivors.length; i++) survivors[i] = new byte[1 << 20];
        suggestGc();
        heap("with 16 MiB retained");
        note("Retained bytes cannot be reclaimed: they are still reachable = not garbage.");
        // touch to keep 'survivors' reachable to this point
        note("survivors kept: " + survivors.length + " blocks");
    }

    // ---- tiny console helpers (self-contained; no external dependencies) ----

    /** Prints a labelled sub-section header. */
    static void of(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }

    /** Prints an indented bullet line (for empirical results / observations). */
    static void note(String line) {
        System.out.println("     " + line);
    }

    /** Reports the current heap usage (used / total / max) in MiB — handy for GC demos. */
    static void heap(String when) {
        Runtime rt = Runtime.getRuntime();
        long used = rt.totalMemory() - rt.freeMemory();
        note(String.format("heap @ %-18s used=%5d MiB  total=%5d MiB  max=%5d MiB",
                when, used >> 20, rt.totalMemory() >> 20, rt.maxMemory() >> 20));
    }

    /**
     * A best-effort GC "suggestion". System.gc() is only a hint; we add a short pause so
     * the reference-processing / cleaner threads have a chance to run before we observe.
     * For teaching only — never call System.gc() in production code.
     */
    static void suggestGc() {
        System.gc();
        try { Thread.sleep(80); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
