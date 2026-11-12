import java.util.*;
import java.util.stream.*;

/**
 * EXAMPLE 09 — Streams: a lazy functional pipeline.
 *
 * A Stream is a declarative pipeline: SOURCE -> intermediate ops (map/filter/...) ->
 * TERMINAL op (collect/reduce/...). Key properties demonstrated:
 *   - Declarative: you say WHAT, not HOW (no explicit loop/index).
 *   - Lazy: intermediate ops build a plan; nothing runs until the terminal op.
 *   - One-at-a-time flow: elements are pulled through the whole chain, enabling
 *     short-circuiting (limit) and INFINITE sources (iterate/generate).
 *   - Collectors: grouping/partitioning as data transformations.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    public static void main(String[] args) {
        section("Imperative loop vs declarative stream (same result)");
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int impSum = 0;
        for (int n : nums) if (n % 2 == 0) impSum += n * n;      // sum of squares of evens
        int strSum = nums.stream().filter(n -> n % 2 == 0).mapToInt(n -> n * n).sum();
        note("imperative sum-of-squares-of-evens = " + impSum);
        note("stream    sum-of-squares-of-evens = " + strSum + "   (no loop, no mutable accumulator)");

        section("LAZINESS: intermediate ops don't run until a terminal op");
        Stream<Integer> plan = nums.stream()
                .peek(n -> System.out.println("       map sees " + n))
                .map(n -> n * 10);
        note("built the pipeline — note NOTHING printed above yet (it is lazy).");
        note("now calling the terminal op findFirst():");
        Optional<Integer> first = plan.findFirst();
        note("findFirst() = " + first.get() + "   (only ONE element flowed — short-circuited)");

        section("Infinite source + limit (only possible because streams are lazy)");
        List<Integer> firstFivePowers = Stream.iterate(1, x -> x * 2)   // 1,2,4,8,... infinite
                .limit(5)
                .collect(Collectors.toList());
        note("first 5 powers of two = " + firstFivePowers);

        section("reduce: fold a stream into a single value");
        Optional<Integer> product = Stream.of(1, 2, 3, 4).reduce((a, b) -> a * b);
        note("product of 1..4 via reduce = " + product.get());

        section("Collectors: grouping as a data transformation");
        List<String> words = List.of("apple", "banana", "avocado", "cherry", "blueberry");
        Map<Character, List<String>> byFirst = words.stream()
                .collect(Collectors.groupingBy(w -> w.charAt(0)));
        note("groupingBy first letter = " + byFirst);
        Map<Boolean, List<String>> partitioned = words.stream()
                .collect(Collectors.partitioningBy(w -> w.length() > 5));
        note("partitioningBy length>5 = " + partitioned);

        section("Streams are CONSUMED ONCE");
        Stream<Integer> once = Stream.of(1, 2, 3);
        once.count();
        try {
            once.count();   // second terminal op on the same stream
        } catch (IllegalStateException e) {
            note("re-using a consumed stream threw IllegalStateException — build a new one.");
        }
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
