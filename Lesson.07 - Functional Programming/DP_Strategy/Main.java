import java.util.List;
import java.util.function.Function;

/**
 * EXAMPLE 15 — Design pattern, functionally: STRATEGY.
 *
 * STRATEGY: "encapsulate interchangeable algorithms and select one at runtime."
 *
 * The OO form is a Strategy interface plus one class per algorithm, held by a "context"
 * object. Once functions are first-class, a strategy simply IS a function value: pass a
 * Function<Order,Integer> shipping rule to a method and you have the Strategy pattern with
 * no interface and no classes. Strategies become ordinary data you can store in a List or
 * Map and pick by key, instead of a switch over subtypes.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    record Order(String item, int weightGrams, int subtotalCents) {}

    // --- three interchangeable shipping-cost algorithms, each a plain function ---
    static final Function<Order, Integer> FREE_SHIPPING = o -> 0;
    static final Function<Order, Integer> FLAT_RATE     = o -> 4_99;
    static final Function<Order, Integer> BY_WEIGHT     = o -> 2_00 + o.weightGrams() / 100 * 10;

    // A HOF that uses whichever strategy it is given — the "context" in GoF terms.
    static int totalWithShipping(Order o, Function<Order, Integer> shipping) {
        return o.subtotalCents() + shipping.apply(o);
    }

    public static void main(String[] args) {
        Order order = new Order("Anvil", 3_200, 59_99);   // 3.2 kg, $59.99

        section("STRATEGY: the algorithm is a function value, selected at runtime");
        note("OO form: interface ShippingStrategy + FreeShipping/FlatRate/ByWeight classes.");
        note("FP form: a Function<Order,Integer> per rule — pass the one you want.");
        note("free   -> total = " + money(totalWithShipping(order, FREE_SHIPPING)));
        note("flat   -> total = " + money(totalWithShipping(order, FLAT_RATE)));
        note("weight -> total = " + money(totalWithShipping(order, BY_WEIGHT)) + "   (2.00 + 32*0.10)");

        section("Strategies are ordinary data: pick from a table, no switch over types");
        List<Function<Order, Integer>> rules = List.of(FREE_SHIPPING, FLAT_RATE, BY_WEIGHT);
        for (int i = 0; i < rules.size(); i++) {
            note("rules[" + i + "].apply(order) = " + money(rules.get(i).apply(order)));
        }
        note("");
        note("Strategy = behaviour as a value (Section 5). The class hierarchy vanishes");
        note("once functions are first-class: the 'strategy' is just a parameter.");
    }

    // local money formatter (cents -> $d.dd)
    static String money(int cents) {
        return String.format("$%d.%02d", cents / 100, Math.abs(cents % 100));
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
