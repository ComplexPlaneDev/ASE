// >>> DEMO A: new expression type.
//
public class ModuloExpr implements Expression {

    private final Expression left;
    private final Expression right;

    public ModuloExpr(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visitModulo(this);
    }
}
// <<< DEMO A
