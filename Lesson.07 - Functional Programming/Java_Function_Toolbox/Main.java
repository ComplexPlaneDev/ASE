import java.util.*;
import java.util.function.*;

/**
 * EXAMPLE 05 — The java.util.function toolbox.
 *
 * The JDK ships a family of ready-made functional interfaces so you rarely need to write
 * your own. The core five and their shapes:
 *   Function<T,R>   R apply(T)      transform
 *   Predicate<T>    boolean test(T) test / filter
 *   Consumer<T>     void accept(T)  side effect
 *   Supplier<T>     T get()         produce
 *   BiFunction<T,U,R>, UnaryOperator<T>, BinaryOperator<T> — variations.
 * Plus primitive specialisations (IntUnaryOperator, ...) that avoid boxing.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    public static void main(String[] args) {
        section("Function<T,R> — transform, and compose with andThen");
        Function<String, Integer> length = String::length;
        Function<Integer, Integer> twice = n -> n * 2;
        Function<String, Integer> lenThenTwice = length.andThen(twice);
        note("length.andThen(twice).apply(\"hello\") = " + lenThenTwice.apply("hello") + "  (5 -> 10)");

        section("Predicate<T> — test, and combine with and/or/negate");
        Predicate<Integer> even = n -> n % 2 == 0;
        Predicate<Integer> big  = n -> n > 100;
        note("even.negate().test(3)   = " + even.negate().test(3));
        note("even.or(big).test(101)  = " + even.or(big).test(101));

        section("Consumer<T> — perform a side effect, chain with andThen");
        Consumer<String> print = msg -> System.out.println("       [consumed] " + msg);
        Consumer<String> printTwice = print.andThen(print);
        printTwice.accept("tick");

        section("Supplier<T> — lazily PRODUCE a value (no input)");
        Supplier<Double> roll = () -> Math.floor(Math.random() * 6) + 1;
        note("a supplier defers work: roll.get() = " + roll.get());

        section("BinaryOperator<T> — fold two Ts into one (used by reduce)");
        BinaryOperator<Integer> sum = Integer::sum;
        note("List.of(1..5) reduced with Integer::sum = "
                + List.of(1, 2, 3, 4, 5).stream().reduce(0, sum));

        section("Primitive specialisations avoid boxing");
        IntUnaryOperator inc = x -> x + 1;          // no Integer boxing on the hot path
        int[] result = {5};
        result[0] = inc.applyAsInt(result[0]);
        note("IntUnaryOperator inc applied to 5 = " + result[0] + "  (operates on primitive int)");
        note("");
        note("Reach for these before writing a custom @FunctionalInterface; they");
        note("already carry the composition (andThen/compose/and/or/negate) methods.");
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
