import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * EXAMPLE 12 — Functor & monad pattern (map / flatMap).
 *
 * A monad is a "box" type you can chain operations through without unpacking and repacking
 * by hand. We build a tiny Result<T> box that is EITHER a success (Ok) OR a failure (Err),
 * then wire several fallible steps into one pipeline with flatMap:
 *
 *     parse -> validate positive -> look up price -> compute total
 *
 * Each step returns a Result. flatMap propagates an Ok forward to the next step and
 * short-circuits on the first Err, carrying that error (with WHERE it happened) all the way
 * to the end. The pipeline therefore returns EITHER the final value OR the first error.
 *
 *   - of / unit : T -> Result<T>              (wrap a value: ok)
 *   - map       : (T -> U)        -> Result<U>   (transform contents; makes it a FUNCTOR)
 *   - flatMap   : (T -> Result<U>)-> Result<U>   (chain fallible steps; makes it a MONAD)
 *
 * Java has no Monad interface, but the idiom is everywhere (Optional, Stream, ...).
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // A minimal Result<T> monad: Ok carries a value, Err carries an error message.
    // 'sealed ... permits' means a Result is EITHER an Ok OR an Err — nothing else.
    sealed interface Result<T> permits Ok, Err {

        // of / unit: put a plain value into the box.
        static <T> Result<T> ok(T value)   { return new Ok<>(value); }
        static <T> Result<T> err(String e) { return new Err<>(e); }

        // FUNCTOR map: transform the value IF Ok; pass an Err straight through unchanged.
        default <U> Result<U> map(Function<T, U> f) {
            return switch (this) {
                case Ok<T> ok   -> new Ok<>(f.apply(ok.value()));
                case Err<T> err -> new Err<>(err.error());
            };
        }

        // MONAD flatMap (bind): the step itself returns a Result. On Ok, run it and keep the
        // result FLAT (no Result<Result<..>>); on Err, short-circuit and carry the error.
        default <U> Result<U> flatMap(Function<T, Result<U>> f) {
            return switch (this) {
                case Ok<T> ok   -> f.apply(ok.value());
                case Err<T> err -> new Err<>(err.error());
            };
        }

        // Terminal: collapse the box into a plain value — the final result OR a fallback
        // computed from the error. This is how the pipeline hands you "value or error".
        default T orElse(Function<String, T> onError) {
            return switch (this) {
                case Ok<T> ok   -> ok.value();
                case Err<T> err -> onError.apply(err.error());
            };
        }
    }
    record Ok<T>(T value)       implements Result<T> {}
    record Err<T>(String error) implements Result<T> {}

    // ----- the fallible pipeline steps: each returns a Result<...> -----

    // Step 1: parse the quantity text into an int.
    static Result<Integer> parseQty(String s) {
        try { return Result.ok(Integer.parseInt(s.trim())); }
        catch (NumberFormatException e) { return Result.err("parseQty: '" + s + "' is not a number"); }
    }

    // Step 2: a quantity must be strictly positive.
    static Result<Integer> requirePositive(int qty) {
        return qty > 0 ? Result.ok(qty) : Result.err("requirePositive: quantity must be > 0, got " + qty);
    }

    // Step 3: look up the unit price (in cents) of a product; may be unknown.
    static final Map<String, Integer> PRICES = Map.of("widget", 1299, "gadget", 4999);
    static Result<Integer> priceOf(String product) {
        Integer cents = PRICES.get(product);
        return cents != null ? Result.ok(cents) : Result.err("priceOf: unknown product '" + product + "'");
    }

    // Step 4 is a pure arithmetic combine; we keep it as a plain function and use map.

    /**
     * The whole pipeline for ONE order line, as a single chain. Read top to bottom: each
     * flatMap runs only if everything before it was Ok. The first Err anywhere falls through
     * to the end untouched — so this returns EITHER the final total OR the first error.
     */
    static Result<Integer> lineTotalCents(String product, String qtyText) {
        return parseQty(qtyText)                          // Result<Integer>
                .flatMap(Main::requirePositive)           // still Result<Integer>, or Err
                .flatMap(qty -> priceOf(product)          // chain a second fallible lookup...
                        .map(unit -> unit * qty));        // ...and combine (map: can't fail)
    }

    public static void main(String[] args) {
        section("A chain of fallible steps: parse -> validate -> price -> total");
        note("Each step returns a Result; flatMap threads Ok forward, short-circuits on Err.");
        note("");
        printLine("widget", "3");     // all steps succeed
        printLine("gadget", "2");     // all steps succeed
        printLine("widget", "oops");  // fails at step 1 (parse)
        printLine("widget", "-5");    // fails at step 2 (validate)
        printLine("gizmo",  "4");     // fails at step 3 (price lookup)

        section("The first Err wins and is carried to the end (no exceptions thrown)");
        Result<Integer> r = lineTotalCents("gizmo", "0");  // two things are wrong (qty<=0 AND unknown)
        note("lineTotalCents(\"gizmo\", \"0\") = " + r);
        note("-> stops at the FIRST failing step (validate), never reaches the price lookup.");

        section("Collapsing the box: get the final value OR handle the error");
        int total = lineTotalCents("widget", "3").orElse(msg -> {
            note("  (recovering from: " + msg + ")"); return 0;
        });
        note("widget x3 total cents = " + total);
        int fallback = lineTotalCents("gizmo", "4").orElse(msg -> -1);
        note("gizmo  x4 -> orElse(-1) = " + fallback + "   (error turned into a safe default)");

        section("map vs flatMap: flatMap keeps the box FLAT");
        Result<Result<Integer>> nested = parseQty("7").map(Main::requirePositive);  // Result<Result<..>>
        Result<Integer>        flat   = parseQty("7").flatMap(Main::requirePositive); // Result<..>
        note("map  (step returns a Result) -> " + nested + "   <- a box inside a box (awkward)");
        note("flatMap                       -> " + flat   + "   <- flattened (the point of a monad)");

        section("Same shape elsewhere in the JDK: Optional and Stream");
        Optional<Integer> opt = Optional.of("42").map(Integer::parseInt);           // functor map
        List<Integer> flatten = Stream.of(List.of(1, 2), List.of(3, 4))
                .flatMap(List::stream).toList();                                    // monadic flatMap
        note("Optional.map   : " + opt + "   (box = present/absent)");
        note("Stream.flatMap : " + flatten + "   (box = zero-or-many; sub-lists concatenated)");

        section("Monad law — left identity:  ok(a).flatMap(f)  ==  f(a)");
        Function<Integer, Result<Integer>> f = Main::requirePositive;
        note("ok(5).flatMap(f) = " + Result.ok(5).flatMap(f));
        note("f(5)             = " + f.apply(5));
        note("law holds ? " + Result.ok(5).flatMap(f).equals(f.apply(5)));
    }

    // Run the pipeline for one line and print the outcome (Ok(total) or the located Err).
    static void printLine(String product, String qtyText) {
        Result<Integer> result = lineTotalCents(product, qtyText);
        note(String.format("%-8s x %-5s -> %s", product, qtyText, result));
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
