/** Splits "1 + 2 * (3 - 4)" into tokens: numbers and single characters. */
public class Tokenizer {

    private final String input;
    private int position = 0;
    private String current;

    public Tokenizer(String input) {
        this.input = input;
        advance();
    }

    /** Current token without consuming it (null at the end). */
    public String peek() {
        return current;
    }

    /** Current token, then move on (null at the end). */
    public String next() {
        String token = current;
        advance();
        return token;
    }

    /** Consumes the current token only if it equals the expected one. */
    public boolean accept(String expected) {
        if (expected.equals(current)) {
            advance();
            return true;
        }
        return false;
    }

    public static boolean isNumber(String token) {
        char first = token.charAt(0);
        return Character.isDigit(first) || first == '.';
    }

    private void advance() {
        while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
            position++;
        }
        if (position >= input.length()) {
            current = null;
            return;
        }
        char c = input.charAt(position);
        if (Character.isDigit(c) || c == '.') {
            int start = position;
            while (position < input.length()
                    && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
                position++;
            }
            current = input.substring(start, position);
        } else {
            current = String.valueOf(c);
            position++;
        }
    }
}
