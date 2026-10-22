/**
 * One visit method per expression type.
 * The result is kept inside the concrete visitor (no generics needed).
 */
public interface ExpressionVisitor {

    void visitNumber(NumberExpr number);

    void visitAdd(AddExpr add);

    void visitSubtract(SubtractExpr subtract);

    void visitMultiply(MultiplyExpr multiply);

    void visitDivide(DivideExpr divide);

// >>> DEMO A
    void visitModulo(ModuloExpr modulo);
// <<< DEMO A
}
