import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * EXAMPLE 08 — Function composition & currying.
 *
 * COMPOSITION: build a big function from small ones.
 *   f.andThen(g)  ==  x -> g(f(x))     (do f, THEN g)
 *   f.compose(g)  ==  x -> f(g(x))     (do g first)
 *
 * CURRYING: turn a 2-arg function into a chain of 1-arg functions:
 *   (a, b) -> r      becomes      a -> (b -> r)
 * which enables PARTIAL APPLICATION: fix the first argument to get a specialised function.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // Curry a BiFunction into a Function returning a Function.
    static <A, B, R> Function<A, Function<B, R>> curry(BiFunction<A, B, R> f) {
        return a -> b -> f.apply(a, b);
    }

    public static void main(String[] args) {
        section("andThen vs compose (order matters)");
        Function<Integer, Integer> timesTwo = x -> x * 2;
        Function<Integer, Integer> plusTen  = x -> x + 10;

        Function<Integer, Integer> f1 = timesTwo.andThen(plusTen);   // (x*2)+10
        Function<Integer, Integer> f2 = timesTwo.compose(plusTen);   // (x+10)*2
        note("timesTwo.andThen(plusTen).apply(5) = " + f1.apply(5) + "   ((5*2)+10)");
        note("timesTwo.compose(plusTen).apply(5) = " + f2.apply(5) + "   ((5+10)*2)");

        section("Composing a longer pipeline from tiny verified pieces");
        Function<String, String> trim   = String::trim;
        Function<String, String> lower  = String::toLowerCase;
        Function<String, String> slugSp = s -> s.replace(' ', '-');
        Function<String, String> slugify = trim.andThen(lower).andThen(slugSp);
        note("slugify.apply(\"  Hello World  \") = \"" + slugify.apply("  Hello World  ") + "\"");

        section("Currying: (a,b) -> r   becomes   a -> (b -> r)");
        BiFunction<Integer, Integer, Integer> add = Integer::sum;
        Function<Integer, Function<Integer, Integer>> curriedAdd = curry(add);
        note("curriedAdd.apply(3).apply(4) = " + curriedAdd.apply(3).apply(4));

        section("Partial application: fix one argument to specialise");
        Function<Integer, Integer> add3 = curriedAdd.apply(3);   // specialise 'add' with a=3
        note("add3.apply(10) = " + add3.apply(10) + "   (reusable specialised function)");
        note("add3.apply(20) = " + add3.apply(20));
        note("");
        note("Composition + partial application let you assemble behaviour like data,");
        note("keeping each piece small, pure, and independently testable.");
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
