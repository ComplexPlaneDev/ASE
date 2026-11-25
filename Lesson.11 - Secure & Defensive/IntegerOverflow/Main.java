/**
 * Java integer arithmetic WRAPS AROUND silently on overflow (no exception). An attacker who
 * controls a size, count, price, or index can exploit this: a huge value wraps to a small or
 * NEGATIVE one, defeating a bounds check, under-allocating a buffer, or flipping a total.
 *
 * Defenses:
 *   - Use Math.*Exact (addExact/multiplyExact/...) which THROW on overflow.
 *   - Validate ranges BEFORE arithmetic (reject absurd sizes/counts up front).
 *   - Use a wider type (long) or BigInteger when the domain can be large.
 *
 * We show a classic "allocate quantity * price" and "capacity check" overflow, then the
 * checked versions that reject the attack.
 */
public class Main {

    // VULNERABLE: silent wrap — a large quantity*price can turn POSITIVE huge into negative.
    static int totalCostVulnerable(int quantity, int unitPriceCents) {
        return quantity * unitPriceCents;   // may overflow and wrap, no warning
    }

    // SAFE: multiplyExact throws ArithmeticException on overflow; validate inputs too.
    static long totalCostSafe(int quantity, int unitPriceCents) {
        if (quantity < 0 || unitPriceCents < 0)
            throw new IllegalArgumentException("negative quantity/price");
        if (quantity > 10_000)
            throw new IllegalArgumentException("quantity exceeds sane limit");
        return Math.multiplyExact((long) quantity, unitPriceCents);   // widen + checked
    }

    // VULNERABLE: "remaining capacity" check that an overflowing addition defeats.
    static boolean fitsVulnerable(int currentSize, int addCount, int capacity) {
        return currentSize + addCount <= capacity;   // currentSize+addCount can wrap negative → "fits"
    }

    // SAFE: checked addition; overflow is treated as "does not fit" (fail closed).
    static boolean fitsSafe(int currentSize, int addCount, int capacity) {
        try {
            return Math.addExact(currentSize, addCount) <= capacity;
        } catch (ArithmeticException overflow) {
            return false;   // cannot possibly fit — reject
        }
    }

    public static void main(String[] args) {
        section("Multiplication overflow: a 'total cost' silently goes NEGATIVE");
        int qty = 100_000, price = 50_000;   // 5,000,000,000 > Integer.MAX_VALUE
        note("quantity=" + qty + ", unitPrice=" + price);
        note("vulnerable total = " + totalCostVulnerable(qty, price) + "   <-- wrapped (negative!)");
        try {
            totalCostSafe(qty, price);
        } catch (RuntimeException e) {
            note("safe total       = REJECTED (" + e.getMessage() + ")");
        }
        note("safe total (sane)  = " + totalCostSafe(3, price) + " cents");

        section("Addition overflow defeats a capacity check");
        int current = Integer.MAX_VALUE - 5, add = 100, cap = 1_000;
        note("current=" + current + ", add=" + add + ", capacity=" + cap);
        note("vulnerable fits? " + fitsVulnerable(current, add, cap) + "   <-- WRONG: overflow said 'fits'");
        note("safe       fits? " + fitsSafe(current, add, cap) + "   (overflow → does not fit)");

        section("Guidance");
        note("- Java int/long arithmetic WRAPS silently; only Math.*Exact signal overflow.");
        note("- Validate sizes/counts/indices against sane bounds BEFORE computing with them.");
        note("- Prefer long for money-in-cents and counters; BigInteger for unbounded domains.");
        note("- Treat any overflow on a security-relevant path as a failure (reject), not a wrap.");
    }

    // ---- tiny console helpers ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
