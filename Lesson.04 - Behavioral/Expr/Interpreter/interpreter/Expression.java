/**
 * Interpreter pattern: every node of the expression tree
 * knows how to interpret itself. The operations live INSIDE the node classes.
 */
public interface Expression {

    double evaluate();

    String print();

// >>> DEMO B
    String toPostfix();
// <<< DEMO B
}
