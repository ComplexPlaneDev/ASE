import java.util.*;

/**
 * EXAMPLE 10 — Optional: functional handling of absence.
 *
 * Optional<T> is a container that is either PRESENT (holds a value) or EMPTY. It replaces
 * null as a RETURN type for "maybe no result", turning NullPointerException risk into a
 * typed, composable value. We show map / flatMap / filter / orElseGet / orElseThrow and
 * the anti-pattern of calling get() blindly.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // A tiny repository whose lookup MAY fail — modelled with Optional, not null.
    static final class UserRepo {
        private final Map<Integer, String> byId = Map.of(1, "alice", 2, "bob");
        Optional<String> findName(int id) { return Optional.ofNullable(byId.get(id)); }
    }

    public static void main(String[] args) {
        UserRepo repo = new UserRepo();

        section("Present vs empty (no nulls in sight)");
        note("findName(1) = " + repo.findName(1));
        note("findName(9) = " + repo.findName(9) + "   (empty, NOT null)");

        section("map: transform the value IF present, stay empty otherwise");
        Optional<Integer> len1 = repo.findName(1).map(String::length);
        Optional<Integer> len9 = repo.findName(9).map(String::length);
        note("findName(1).map(length) = " + len1);
        note("findName(9).map(length) = " + len9 + "   (map skipped — still empty)");

        section("flatMap: chain another Optional-returning call without nesting");
        // Suppose we look up the user, then look up their (optional) manager.
        Optional<String> manager = repo.findName(1)
                .flatMap(name -> name.equals("alice") ? Optional.of("carol") : Optional.empty());
        note("alice's manager = " + manager + "   (flatMap avoids Optional<Optional<..>>)");

        section("filter: keep the value only if a predicate holds");
        note("findName(2).filter(startsWith b) = " + repo.findName(2).filter(n -> n.startsWith("b")));
        note("findName(1).filter(startsWith b) = " + repo.findName(1).filter(n -> n.startsWith("b")));

        section("Supplying a default: orElse / orElseGet / orElseThrow");
        String name9 = repo.findName(9).orElse("<unknown>");
        note("findName(9).orElse(\"<unknown>\") = " + name9);
        String lazyDefault = repo.findName(9).orElseGet(() -> "computed-default");  // supplier only runs if empty
        note("findName(9).orElseGet(supplier) = " + lazyDefault);
        try {
            repo.findName(9).orElseThrow(() -> new NoSuchElementException("no user 9"));
        } catch (NoSuchElementException e) {
            note("findName(9).orElseThrow() threw: " + e.getMessage());
        }

        section("ifPresentOrElse: act in both branches, functionally");
        repo.findName(2).ifPresentOrElse(
                n -> note("present -> " + n),
                () -> note("absent  -> nothing to do"));

        section("Anti-pattern: get() without checking");
        note("findName(9).get() would throw NoSuchElementException — never call get() blindly.");
        note("Use Optional as a RETURN type only; not for fields, params, or collections.");
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
