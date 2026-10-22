public class AddExpr implements Expression {

    private final Expression left;
    private final Expression right;

    public AddExpr(Expression left, Expression right) {
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
        visitor.visitAdd(this);
    }
}
