public class MultiplyExpr implements Expression {

    private final Expression left;
    private final Expression right;

    public MultiplyExpr(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return left.evaluate() * right.evaluate();
    }

    @Override
    public String print() {
        return "(" + left.print() + " * " + right.print() + ")";
    }

// >>> DEMO B
//    @Override
//    public String toPostfix() {
//        return left.toPostfix() + " " + right.toPostfix() + " *";
//    }
// <<< DEMO B
}
