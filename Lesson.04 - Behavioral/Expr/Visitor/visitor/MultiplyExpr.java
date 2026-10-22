public class MultiplyExpr implements Expression {

    private final Expression left;
    private final Expression right;

    public MultiplyExpr(Expression left, Expression right) {
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
        visitor.visitMultiply(this);
    }
}
