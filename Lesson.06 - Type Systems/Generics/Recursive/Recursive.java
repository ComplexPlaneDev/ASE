// F-bounded polymorphism ("curiously recurring" generic pattern):
//   1. <T extends Comparable<T>> so max() only accepts self-comparable elements.
//   2. A self-typed fluent builder where chained calls keep the SUBCLASS type.

import java.util.List;

public class Recursive {

    // F-bounded: "T must be Comparable to its own kind". This rejects, at COMPILE
    // time, any element type that cannot be compared to itself.
    static <T extends Comparable<T>> T max(List<T> xs) {
        T best = xs.get(0);
        for (T x : xs) if (x.compareTo(best) > 0) best = x;
        return best;
    }

    // Self-type pattern: Builder<SELF> lets subclass methods return SELF so the
    // fluent chain never "loses" the concrete type.
    abstract static class Builder<SELF extends Builder<SELF>> {
        private final StringBuilder log = new StringBuilder();
        @SuppressWarnings("unchecked")
        protected SELF self() { return (SELF) this; }
        SELF step(String s) { log.append(s).append(' '); return self(); }
        String build() { return log.toString().trim(); }
    }

    static final class PizzaBuilder extends Builder<PizzaBuilder> {
        PizzaBuilder cheese() { return step("cheese"); }   // returns PizzaBuilder, not Builder
        PizzaBuilder basil()  { return step("basil"); }
    }

    public static void main(String[] args) {

        System.out.println("=== F-bounded max<T extends Comparable<T>> ===");
        System.out.println("max([3,1,4,1,5,9,2,6]) = " + max(List.of(3, 1, 4, 1, 5, 9, 2, 6)));
        System.out.println("max([\"pear\",\"apple\",\"kiwi\"]) = "
                + max(List.of("pear", "apple", "kiwi")));

        System.out.println("\n=== Self-typed fluent builder ===");
        // Thanks to SELF, .cheese() after .step(...) still returns PizzaBuilder,
        // so subclass-specific methods remain chainable.
        PizzaBuilder pb = new PizzaBuilder();
        String pizza = pb.step("dough").cheese().step("sauce").basil().build();
        System.out.println("built: \"" + pizza + "\"");
        System.out.println("Chained .step() returned PizzaBuilder (not Builder), so .cheese()/.basil() stayed available.");
    }
}
