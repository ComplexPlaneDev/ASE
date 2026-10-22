public class Main {

    public static void main(String[] args) {
        String[] inputs = {
                "1 + 2 * 3",
                "(1 + 2) * 3",
                "10 / 4 - 1",
// >>> DEMO A
                "17 % 5 + 2",
// <<< DEMO A
        };

        for (String input : inputs) {
            Expression expression = new ExpressionParser(input).parse();

            System.out.println("Input:   " + input);
            System.out.println("Printed: " + expression.print());
            System.out.println("Value:   " + NumberFormatter.format(expression.evaluate()));
// >>> DEMO B
//            System.out.println("Postfix: " + expression.toPostfix());
// <<< DEMO B
            System.out.println();
        }
    }
}
