/** Operation 2: prints an expression as text (fully parenthesized). */
public class PrintVisitor implements ExpressionVisitor {

    private String result;

    public String getResult() {
        return result;
    }

    @Override
    public void visitNumber(NumberExpr number) {
        result = NumberFormatter.format(number.getValue());
    }

    @Override
    public void visitAdd(AddExpr add) {
        result = "(" + print(add.getLeft()) + " + " + print(add.getRight()) + ")";
    }

    @Override
    public void visitSubtract(SubtractExpr subtract) {
        result = "(" + print(subtract.getLeft()) + " - " + print(subtract.getRight()) + ")";
    }

    @Override
    public void visitMultiply(MultiplyExpr multiply) {
        result = "(" + print(multiply.getLeft()) + " * " + print(multiply.getRight()) + ")";
    }

    @Override
    public void visitDivide(DivideExpr divide) {
        result = "(" + print(divide.getLeft()) + " / " + print(divide.getRight()) + ")";
    }

// >>> DEMO A
//    @Override
//    public void visitModulo(ModuloExpr modulo) {
//        result = "(" + print(modulo.getLeft()) + " % " + print(modulo.getRight()) + ")";
//    }
// <<< DEMO A

    private String print(Expression expression) {
        expression.accept(this);
        return result;
    }
}
