import java.lang.ref.WeakReference;

/**
 * EXAMPLE 07 — Cyclic references.
 *
 * A pure REFERENCE-COUNTING collector (CPython's fallback, Swift ARC, C++ shared_ptr)
 * cannot reclaim a cycle: each object in the cycle has a non-zero count because another
 * cycle member points at it, even when the whole cycle is unreachable from the program.
 *
 * Java uses a TRACING collector: it starts from GC roots and keeps only what is
 * reachable. An unreachable cycle is simply not reached, so it is collected like any
 * other garbage. We prove it: build a cycle, drop the external references, and watch a
 * WeakReference probe get cleared.
 *
 * Run (JDK 21+):  java App.java
 */
public class App {

    static final class Parent {
        Child child;
        final String tag;
        Parent(String tag) { this.tag = tag; }
    }

    static final class Child {
        Parent parent;             // back-reference -> forms a cycle with Parent
        final String tag;
        Child(String tag) { this.tag = tag; }
    }

    public static void main(String[] args) {
        of("Build a Parent <-> Child cycle");
        Parent parent = new Parent("P");
        Child child = new Child("C");
        parent.child = child;
        child.parent = parent;     // the cycle: P -> C -> P -> ...

        // Probes that do NOT keep the objects alive.
        WeakReference<Parent> parentProbe = new WeakReference<>(parent);
        WeakReference<Child> childProbe = new WeakReference<>(child);

        note("cycle built: P.child.parent == P ? " + (parent.child.parent == parent));
        suggestGc();
        note("while strongly reachable — parent: "
                + (parentProbe.get() != null ? "ALIVE" : "cleared")
                + ", child: " + (childProbe.get() != null ? "ALIVE" : "cleared"));

        of("Drop BOTH external references — the cycle becomes an unreachable island");
        parent = null;
        child = null;
        suggestGc();

        boolean pGone = parentProbe.get() == null;
        boolean cGone = childProbe.get() == null;
        note("after dropping refs + GC — parent: " + (pGone ? "COLLECTED" : "still alive")
                + ", child: " + (cGone ? "COLLECTED" : "still alive"));

        note("");
        if (pGone && cGone) {
            note("Both collected despite referencing EACH OTHER.");
            note("Tracing GC reclaims unreachable cycles; reference counting could NOT.");
        } else {
            note("GC timing: the cycle is eligible; a later GC will reclaim it.");
        }

        note("");
        note("Caveat: a cycle where ONE member is still reachable from a root keeps");
        note("the WHOLE cycle alive — that is the mechanism behind listener leaks.");
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
