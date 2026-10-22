public class Main {

    public static void main(String[] args) {
        String[] inputs = {
                "1 + 2 * 3",
                "(1 + 2) * 3",
                "10 / 4 - 1",
// >>> DEMO A
//                "17 % 5 + 2",
// <<< DEMO A
        };

        for (String input : inputs) {
            Expression expression = new ExpressionParser(input).parse();

            PrintVisitor printer = new PrintVisitor();
            expression.accept(printer);

            EvaluateVisitor evaluator = new EvaluateVisitor();
            expression.accept(evaluator);

            System.out.println("Input:   " + input);
            System.out.println("Printed: " + printer.getResult());
            System.out.println("Value:   " + NumberFormatter.format(evaluator.getResult()));
// >>> DEMO B
            PostfixVisitor postfix = new PostfixVisitor();
            expression.accept(postfix);
            System.out.println("Postfix: " + postfix.getResult());
// <<< DEMO B
            System.out.println();
        }
    }
}
