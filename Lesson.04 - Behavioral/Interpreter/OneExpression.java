/**
 * OneExpression
 *
 * Handles the ones place:
 *   IX = 9,  V = 5,  IV = 4,  I = 1 (×n)
 */
public class OneExpression extends RomanExpression {

    @Override
    public void interpret(RomanContext context) {
        int val = context.getInput();
        if      (val >= 9) { context.appendOutput("IX"); context.setInput(val - 9); }
        else if (val >= 5) { context.appendOutput("V");  context.setInput(val - 5); }
        else if (val >= 4) { context.appendOutput("IV"); context.setInput(val - 4); }
        else {
            context.appendOutput("I".repeat(val));
            context.setInput(0);
        }
    }
}
