/**
 * Recursive descent parser:
 *   expression = term { ("+" | "-") term }
 *   term       = factor { ("*" | "/") factor }
 *   factor     = number | "(" expression ")"
 */
public class ExpressionParser {

    private final Tokenizer tokenizer;

    public ExpressionParser(String input) {
        this.tokenizer = new Tokenizer(input);
    }

    public Expression parse() {
        Expression expression = parseExpression();
        if (tokenizer.peek() != null) {
            throw new IllegalArgumentException("Unexpected token: " + tokenizer.peek());
        }
        return expression;
    }

    private Expression parseExpression() {
        Expression left = parseTerm();
        while (true) {
            if (tokenizer.accept("+")) {
                left = new AddExpr(left, parseTerm());
            } else if (tokenizer.accept("-")) {
                left = new SubtractExpr(left, parseTerm());
            } else {
                return left;
            }
        }
    }

    private Expression parseTerm() {
        Expression left = parseFactor();
        while (true) {
            if (tokenizer.accept("*")) {
                left = new MultiplyExpr(left, parseFactor());
            } else if (tokenizer.accept("/")) {
                left = new DivideExpr(left, parseFactor());
// >>> DEMO A
//            } else if (tokenizer.accept("%")) {
//                left = new ModuloExpr(left, parseFactor());
// <<< DEMO A
            } else {
                return left;
            }
        }
    }

    private Expression parseFactor() {
        String token = tokenizer.next();
        if (token == null) {
            throw new IllegalArgumentException("Unexpected end of input");
        }
        if (Tokenizer.isNumber(token)) {
            return new NumberExpr(Double.parseDouble(token));
        }
        if (token.equals("(")) {
            Expression inner = parseExpression();
            if (!tokenizer.accept(")")) {
                throw new IllegalArgumentException("Expected ')'");
            }
            return inner;
        }
        throw new IllegalArgumentException("Unexpected token: " + token);
    }
}
