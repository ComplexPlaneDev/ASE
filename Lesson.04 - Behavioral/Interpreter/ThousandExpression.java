/**
 * ThousandExpression
 *
 * Handles the thousands place: M (1000)
 * Consumes all thousands from the input.
 */
public class ThousandExpression extends RomanExpression {

    @Override
    public void interpret(RomanContext context) {
        int qty = context.getInput() / 1000;
        context.appendOutput("M".repeat(qty));
        context.setInput(context.getInput() % 1000);
    }
}
