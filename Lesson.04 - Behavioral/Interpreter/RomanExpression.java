/**
 * RomanExpression  (AbstractExpression)
 *
 * Each subclass handles one "place value" group of Roman numeral symbols.
 * Expressions are applied sequentially (not recursively) — each one
 * consumes as much of the remaining input as it can, then yields control.
 *
 * This is a linear interpretation chain rather than a recursive tree,
 * showing that the Interpreter pattern can model sequential grammars too.
 */
public abstract class RomanExpression {
    public abstract void interpret(RomanContext context);
}
