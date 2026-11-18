/**
 * EXAMPLE 09 — RAII in Java: try-with-resources and AutoCloseable.
 *
 * Memory is reclaimed by the GC, but RESOURCES (files, sockets, locks, native handles)
 * must be released DETERMINISTICALLY and promptly. Java's answer to C++ RAII is the
 * try-with-resources statement: any object implementing AutoCloseable declared in the
 * try header is close()d automatically on block exit — normal OR exceptional — and in
 * REVERSE order of acquisition.
 *
 * We show:
 *   • close() runs on the happy path,
 *   • close() still runs when the body throws,
 *   • multiple resources close in reverse order,
 *   • the contrast with the error-prone manual try/finally.
 *
 * Run (JDK 21+):  java App.java
 */
public class App {

    /** A fake resource that logs its acquisition and release so ordering is visible. */
    static final class TracedResource implements AutoCloseable {
        private final String name;
        TracedResource(String name) { this.name = name; note("  OPEN  " + name); }
        void use() { note("  use   " + name); }
        void fail() { throw new IllegalStateException("boom in " + name); }
        @Override public void close() { note("  CLOSE " + name); }
    }

    public static void main(String[] args) {
        of("Happy path: close() runs automatically at block exit");
        try (TracedResource r = new TracedResource("A")) {
            r.use();
        }
        note("(exited normally; A was closed for us)");

        of("Exceptional path: close() STILL runs when the body throws");
        try {
            try (TracedResource r = new TracedResource("B")) {
                r.use();
                r.fail();                 // throws — yet B is still closed
            }
        } catch (RuntimeException e) {
            note("caught: " + e.getMessage() + "  (note B was closed BEFORE we got here)");
        }

        of("Multiple resources close in REVERSE order of acquisition");
        try (TracedResource a = new TracedResource("first");
             TracedResource b = new TracedResource("second");
             TracedResource c = new TracedResource("third")) {
            a.use(); b.use(); c.use();
        }
        note("(closed third, then second, then first — LIFO, like C++ stack unwinding)");

        of("The error-prone manual equivalent this REPLACES");
        note("  TracedResource r = new TracedResource(\"M\");");
        note("  try { r.use(); } finally { r.close(); }   // easy to forget / get wrong");
        note("  ...and nesting two resources by hand needs nested try/finally,");
        note("  with careful null checks and suppressed-exception handling.");
        note("try-with-resources generates all of that correctly for you.");
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
}
