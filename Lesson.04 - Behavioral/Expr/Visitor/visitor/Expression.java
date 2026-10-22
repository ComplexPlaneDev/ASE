/**
 * Visitor pattern: the nodes only know how to "accept" a visitor.
 * The operations live OUTSIDE the node classes, in visitor classes.
 */
public interface Expression {

    void accept(ExpressionVisitor visitor);
}
