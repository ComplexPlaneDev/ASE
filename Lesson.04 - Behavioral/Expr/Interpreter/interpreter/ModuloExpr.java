// >>> DEMO A: new expression type.
public class ModuloExpr implements Expression {

    private final Expression left;
    private final Expression right;

    public ModuloExpr(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return left.evaluate() % right.evaluate();
    }

    @Override
    public String print() {
        return "(" + left.print() + " % " + right.print() + ")";
    }

// >>> DEMO B: uncomment these lines when you do DEMO B
//    @Override
//    public String toPostfix() {
//        return left.toPostfix() + " " + right.toPostfix() + " %";
//    }
// <<< DEMO B

}
// <<< DEMO A