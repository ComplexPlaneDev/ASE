import java.util.List;

/**
 * RomanNumeralDemo
 *
 * Demonstrates the Interpreter pattern applied to Roman numeral conversion.
 *
 * The "grammar" for Roman numerals is:
 *   number    ::= thousands hundreds tens ones
 *   thousands ::= M*
 *   hundreds  ::= CM | D | CD | C* | (empty)
 *   tens      ::= XC | L | XL | X* | (empty)
 *   ones      ::= IX | V | IV | I* | (empty)
 *
 * Each grammar rule is a separate RomanExpression subclass.
 * They are applied sequentially; each one consumes its part of the input.
 */
public class RomanNumeralDemo {

    /** The ordered list of expressions — one per grammar rule. */
    private static final List<RomanExpression> GRAMMAR = List.of(
            new ThousandExpression(),
            new HundredExpression(),
            new TenExpression(),
            new OneExpression()
    );

    public static String toRoman(int number) {
        RomanContext context = new RomanContext(number);
        for (RomanExpression expr : GRAMMAR) {
            expr.interpret(context);
        }
        return context.getOutput();
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   Roman Numeral Interpreter                      ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        int[] numbers = {1, 4, 9, 14, 40, 44, 49, 90, 99, 400, 444,
                         900, 999, 1000, 1994, 2024, 3999};

        System.out.printf("  %-6s  →  %s%n", "Input", "Roman");
        System.out.println("  " + "─".repeat(22));
        for (int n : numbers) {
            System.out.printf("  %-6d  →  %s%n", n, toRoman(n));
        }

        System.out.println("\n── Step-by-step trace for 2024 ───────────────────");
        traceConversion(2024);
    }

    private static void traceConversion(int number) {
        RomanContext context = new RomanContext(number);
        List<String> ruleNames = List.of("ThousandExpression",
                                          "HundredExpression",
                                          "TenExpression",
                                          "OneExpression");
        List<RomanExpression> grammar = List.of(
                new ThousandExpression(),
                new HundredExpression(),
                new TenExpression(),
                new OneExpression());

        System.out.printf("  Input: %d%n", number);
        for (int i = 0; i < grammar.size(); i++) {
            int before = context.getInput();
            grammar.get(i).interpret(context);
            int consumed = before - context.getInput();
            System.out.printf("  %-20s consumed %4d  →  output so far: \"%s\"%n",
                    ruleNames.get(i), consumed, context.getOutput());
        }
        System.out.printf("  Result: %d = %s%n", number, context.getOutput());
    }
}
