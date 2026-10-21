/**
 * TenExpression
 *
 * Handles the tens place:
 *   XC = 90,  L = 50,  XL = 40,  X = 10 (×n)
 */
public class TenExpression extends RomanExpression {

    @Override
    public void interpret(RomanContext context) {
        int val = context.getInput();
        if      (val >= 90) { context.appendOutput("XC"); context.setInput(val - 90); }
        else if (val >= 50) { context.appendOutput("L");  context.setInput(val - 50); }
        else if (val >= 40) { context.appendOutput("XL"); context.setInput(val - 40); }
        else {
            int qty = context.getInput() / 10;
            context.appendOutput("X".repeat(qty));
            context.setInput(context.getInput() % 10);
        }
    }
}
