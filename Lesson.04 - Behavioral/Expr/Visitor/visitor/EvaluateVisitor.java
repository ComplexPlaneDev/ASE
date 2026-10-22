/** Operation 1: evaluates an expression. */
public class EvaluateVisitor implements ExpressionVisitor {

    private double result;

    public double getResult() {
        return result;
    }

    @Override
    public void visitNumber(NumberExpr number) {
        result = number.getValue();
    }

    @Override
    public void visitAdd(AddExpr add) {
        result = evaluate(add.getLeft()) + evaluate(add.getRight());
    }

    @Override
    public void visitSubtract(SubtractExpr subtract) {
        result = evaluate(subtract.getLeft()) - evaluate(subtract.getRight());
    }

    @Override
    public void visitMultiply(MultiplyExpr multiply) {
        result = evaluate(multiply.getLeft()) * evaluate(multiply.getRight());
    }

    @Override
    public void visitDivide(DivideExpr divide) {
        result = evaluate(divide.getLeft()) / evaluate(divide.getRight());
    }

// >>> DEMO A
//    @Override
//    public void visitModulo(ModuloExpr modulo) {
//        result = evaluate(modulo.getLeft()) % evaluate(modulo.getRight());
//    }
// <<< DEMO A

    private double evaluate(Expression expression) {
        expression.accept(this);
        return result;
    }
}
