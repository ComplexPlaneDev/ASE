public class NumberExpr implements Expression {

    private final double value;

    public NumberExpr(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visitNumber(this);
    }
}
