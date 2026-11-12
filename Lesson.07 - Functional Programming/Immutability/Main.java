import java.util.List;

/**
 * EXAMPLE 02 — Immutability & structural sharing.
 *
 * Functional programming avoids shared mutable state. Instead of MUTATING a value, you
 * produce a NEW value; old versions stay valid ("persistent data structures").
 *
 * We show three things:
 *   1. A record is a shallowly-immutable value type; a "change" is a withX copy.
 *   2. A tiny persistent cons-list whose 'prepend' SHARES the existing tail (no copy).
 *   3. Why immutability makes values trivially safe to share (no defensive copies).
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // An immutable value type. Fields are final; "modification" returns a new instance.
    record Point(int x, int y) {
        Point withX(int nx) { return new Point(nx, y); }   // returns a NEW Point
    }

    // A persistent singly-linked list. 'prepend' reuses the whole existing list as tail.
    sealed interface Lst permits Nil, Cons {}
    record Nil() implements Lst {}
    record Cons(int head, Lst tail) implements Lst {}

    static Lst prepend(int h, Lst tail) { return new Cons(h, tail); }  // O(1), shares tail

    static String show(Lst l) {
        var sb = new StringBuilder("[");
        for (Lst c = l; c instanceof Cons cons; c = cons.tail()) {
            sb.append(cons.head());
            if (cons.tail() instanceof Cons) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        section("Immutable record: a 'change' is a copy");
        Point p1 = new Point(1, 2);
        Point p2 = p1.withX(9);
        note("p1 = " + p1 + "  (unchanged)");
        note("p2 = " + p2 + "  (a new value)");
        note("p1 == p2 ? " + (p1 == p2) + "   p1 unchanged after producing p2");

        section("Persistent list with STRUCTURAL SHARING");
        Lst v1 = prepend(2, prepend(3, new Nil()));   // [2, 3]
        Lst v2 = prepend(1, v1);                       // [1, 2, 3] — reuses v1 as its tail
        note("v1 = " + show(v1) + "   (still valid)");
        note("v2 = " + show(v2) + "   (prepend of 1 onto v1)");
        note("v2.tail == v1 ? " + (((Cons) v2).tail() == v1) + "   tail is SHARED, not copied");

        section("Immutability = safe sharing (no defensive copies, no locks)");
        List<Integer> immutable = List.of(1, 2, 3);   // java.util.List.of is unmodifiable
        note("List.of(1,2,3) can be shared across threads with zero synchronisation.");
        try {
            immutable.add(4);
        } catch (UnsupportedOperationException e) {
            note("attempt to mutate threw UnsupportedOperationException — the value is frozen.");
        }
        note("");
        note("Because old versions never change, an immutable value is its own snapshot;");
        note("that is why functional data is easy to reason about and to parallelise.");
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
