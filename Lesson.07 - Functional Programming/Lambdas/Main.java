import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * EXAMPLE 04 — Lambdas & @FunctionalInterface (the SAM idea).
 *
 * A lambda is an instance of a FUNCTIONAL INTERFACE: an interface with exactly one
 * abstract method (a "Single Abstract Method", SAM). The compiler infers WHICH
 * functional interface from the target type (assignment / parameter). We show:
 *   - the same lambda body being an anonymous-class replacement,
 *   - defining our OWN @FunctionalInterface,
 *   - target typing: identical syntax, different inferred type,
 *   - that lambdas are NOT inner classes ('this' means the enclosing instance).
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // Our own single-abstract-method interface. @FunctionalInterface makes the compiler
    // REJECT it if someone later adds a second abstract method.
    @FunctionalInterface
    interface Transformer {
        String transform(String input);
        // default methods are allowed and do NOT count against the "single abstract" rule
        default Transformer then(Transformer next) { return s -> next.transform(this.transform(s)); }
    }

    private final String tag = "E04";   // instance field, to show lambda 'this' semantics

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        section("A lambda replaces an anonymous class implementing a SAM interface");
        // Old style (verbose):
        Comparator<String> byLenAnon = new Comparator<>() {
            @Override public int compare(String a, String b) { return a.length() - b.length(); }
        };
        // Lambda style (same SAM, inferred as Comparator<String>):
        Comparator<String> byLenLambda = (a, b) -> a.length() - b.length();
        note("anon.compare(\"aa\",\"b\")   = " + byLenAnon.compare("aa", "b"));
        note("lambda.compare(\"aa\",\"b\") = " + byLenLambda.compare("aa", "b") + "   (same behaviour, less noise)");

        section("Our own @FunctionalInterface + a default combinator");
        Transformer upper = s -> s.toUpperCase();
        Transformer excite = s -> s + "!";
        Transformer shout = upper.then(excite);          // compose via default method
        note("shout.transform(\"hello\") = " + shout.transform("hello"));

        section("TARGET TYPING: same tokens, different inferred interface");
        Runnable r = () -> note("  (a Runnable ran)");
        java.util.function.Supplier<String> s = () -> "a Supplier produced this";
        r.run();
        note("supplier.get() = " + s.get());
        note("=> '() -> ...' has no fixed type; the TARGET decides (Runnable vs Supplier).");

        section("A lambda is NOT an inner class: 'this' is the ENCLOSING instance");
        Runnable showThis = () -> note("inside lambda, this.tag = " + this.tag + " (the Main instance)");
        showThis.run();
        note("An anonymous class would have its OWN 'this'; a lambda does not.");
        note("");
        note("Under the hood the compiler emits an 'invokedynamic' bootstrapped by");
        note("LambdaMetafactory — non-capturing lambdas become reusable singletons.");
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
