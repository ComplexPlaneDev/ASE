/**
 * HundredExpression
 *
 * Handles the hundreds place:
 *   CM = 900,  D = 500,  CD = 400,  C = 100 (×n)
 */
public class HundredExpression extends RomanExpression {

    @Override
    public void interpret(RomanContext context) {
        int val = context.getInput();
        if      (val >= 900) { context.appendOutput("CM"); context.setInput(val - 900); }
        else if (val >= 500) { context.appendOutput("D");  context.setInput(val - 500); }
        else if (val >= 400) { context.appendOutput("CD"); context.setInput(val - 400); }
        else {
            int qty = context.getInput() / 100;
            context.appendOutput("C".repeat(qty));
            context.setInput(context.getInput() % 100);
        }
    }
}
