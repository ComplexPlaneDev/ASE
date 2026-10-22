// >>> DEMO B: new operation = a brand-new class.
//
public class PostfixVisitor implements ExpressionVisitor {

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
        result = postfix(add.getLeft()) + " " + postfix(add.getRight()) + " +";
    }

    @Override
    public void visitSubtract(SubtractExpr subtract) {
        result = postfix(subtract.getLeft()) + " " + postfix(subtract.getRight()) + " -";
    }

    @Override
    public void visitMultiply(MultiplyExpr multiply) {
        result = postfix(multiply.getLeft()) + " " + postfix(multiply.getRight()) + " *";
    }

    @Override
    public void visitDivide(DivideExpr divide) {
        result = postfix(divide.getLeft()) + " " + postfix(divide.getRight()) + " /";
    }

    /*
    @Override
    public void visitModulo(ModuloExpr modulo) {
        result = postfix(modulo.getLeft()) + " " + postfix(modulo.getRight()) + " %";
    }
    */

    private String postfix(Expression expression) {
        expression.accept(this);
        return result;
    }
}
// <<< DEMO B
