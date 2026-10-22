public class NumberExpr implements Expression {

    private final double value;

    public NumberExpr(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public String print() {
        return NumberFormatter.format(value);
    }

// >>> DEMO B
//    @Override
//    public String toPostfix() {
//        return NumberFormatter.format(value);
//    }
// <<< DEMO B
}
