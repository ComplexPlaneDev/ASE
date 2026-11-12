import java.util.function.Function;
import java.util.function.Predicate;

/**
 * EXAMPLE 03 — First-class & higher-order functions.
 *
 * "First-class" means functions are ordinary VALUES: you can store them in variables,
 * pass them as arguments, and RETURN them from other functions.
 *
 * A HIGHER-ORDER function is one that takes and/or returns a function. We build three:
 *   - applyTwice : takes a function, applies it twice.
 *   - adderFactory : returns a function (a function producing functions).
 *   - a combinator that builds a new predicate from two predicates.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // HOF #1: takes a function value as a parameter.
    static <T> T applyTwice(Function<T, T> f, T seed) { return f.apply(f.apply(seed)); }

    // HOF #2: RETURNS a function value (a closure over 'n' — see Example 07).
    static Function<Integer, Integer> adderFactory(int n) { return x -> x + n; }

    public static void main(String[] args) {
        section("Functions as VALUES stored in variables");
        Function<Integer, Integer> increment = x -> x + 1;
        Predicate<Integer>          isEven    = x -> x % 2 == 0;
        note("increment is a value of type Function<Integer,Integer>: increment.apply(4) = " + increment.apply(4));
        note("isEven is a value of type Predicate<Integer>: isEven.test(4) = " + isEven.test(4));

        section("Passing a function AS AN ARGUMENT (higher-order)");
        note("applyTwice(increment, 10) = " + applyTwice(increment, 10) + "   (10 -> 11 -> 12)");
        note("applyTwice(x -> x*x, 3)   = " + applyTwice(x -> x * x, 3) + "   (3 -> 9 -> 81)");

        section("RETURNING a function (a factory of functions)");
        Function<Integer, Integer> add10 = adderFactory(10);
        Function<Integer, Integer> add100 = adderFactory(100);
        note("add10.apply(5)  = " + add10.apply(5));
        note("add100.apply(5) = " + add100.apply(5));

        section("Combinators: build new behaviour from function values");
        Predicate<Integer> isPositive = x -> x > 0;
        Predicate<Integer> positiveAndEven = isPositive.and(isEven);   // default method on Predicate
        note("positiveAndEven.test(4)  = " + positiveAndEven.test(4));
        note("positiveAndEven.test(-4) = " + positiveAndEven.test(-4));
        note("positiveAndEven.test(3)  = " + positiveAndEven.test(3));
        note("");
        note("Treating behaviour as data is the foundation of the whole functional style.");
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
