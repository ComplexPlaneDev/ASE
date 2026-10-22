/** Prints 3.0 as "3" and 1.5 as "1.5". */
public class NumberFormatter {

    public static String format(double value) {
        if (value == Math.rint(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
