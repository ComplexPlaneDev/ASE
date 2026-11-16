import java.util.function.Function;

/**
 * EXAMPLE 16 — Design pattern, functionally: DECORATOR.
 *
 * DECORATOR: "add responsibilities to an object dynamically by wrapping it in
 * same-interface wrappers."
 *
 * The OO form is a tower of wrapper classes: new StarDecorator(new BorderDecorator(base)).
 * Functionally, wrapping is just COMPOSITION (Section 10). Each decoration is a
 * Function<T,T> (a UnaryOperator<T>), and base.andThen(withBorder).andThen(withStars)
 * stacks them — order explicit in the composition, no wrapper hierarchy. (This is also how
 * servlet filters and HTTP middleware are modelled functionally.)
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    public static void main(String[] args) {
        section("DECORATOR: wrapping = composition (Function.andThen), not wrapper classes");
        // Base behaviour + optional "decorations", each a String -> String transform.
        Function<String, String> render     = s -> s;                  // the component
        Function<String, String> withBorder = s -> "[ " + s + " ]";    // a decorator
        Function<String, String> withStars  = s -> "*** " + s + " ***"; // another
        Function<String, String> shout      = String::toUpperCase;     // another

        Function<String, String> decorated = render.andThen(withBorder)
                                                    .andThen(withStars)
                                                    .andThen(shout);
        note("OO form: new StarDecorator(new BorderDecorator(new Text(..))) — a wrapper tower.");
        note("FP form: render.andThen(withBorder).andThen(withStars).andThen(shout).");
        note("decorated.apply(\"hello\") = " + decorated.apply("hello"));

        section("Order of decoration matters — just reorder the composition");
        Function<String, String> other = render.andThen(shout).andThen(withBorder);
        note("border, stars, THEN shout -> " + decorated.apply("hello"));
        note("shout THEN border         -> " + other.apply("hello"));

        section("Add or drop a responsibility by adding or removing a step");
        Function<String, String> plain = render.andThen(withBorder);   // one decoration only
        note("just a border -> " + plain.apply("hello"));
        note("");
        note("Decorator = composition (Section 10). Each decoration is a UnaryOperator<String>;");
        note("andThen builds the wrapper tower as a value — no same-interface wrapper classes.");
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
