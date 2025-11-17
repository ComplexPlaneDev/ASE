package MathPlot.Parsers;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RPN {
    final String input;
    final List<String> operators;
    final List<String> functions;
    int ops = 0;

    public RPN(String input) {
        this.input = input;
        this.operators = Arrays.asList("+", "-", "*", "/", "^");
        this.functions = Arrays.asList("sin", "cos", "exp", "log");
    }

    public Stack<String> parse() throws Exception {
        final List<String> tokens = Stream.of(input.split(" ")).filter(item -> !item.isEmpty())
                .map(item -> item.toLowerCase()).collect(Collectors.toList());

        for (int i = 0; i < tokens.size(); ++i) {
            final String token = tokens.get(i);
            final boolean leaf = isALeaf(token);

            if (i == 0 && !leaf) {
                throw new Exception("Illegal token found");
            }

            if (leaf) {
                ++ops;
                continue;
            }

            final boolean isOperator = operators.contains(token);
            if (isOperator || functions.contains(token)) {
                if (isOperator) {
                    --ops;
                }
            } else {
                throw new Exception("Illegal token found");
            }
        }

        if (ops != 1) {
            throw new Exception("Invalid RPN sequence");
        }

        final Stack<String> out = new Stack<>();
        out.addAll(tokens);
        return out;
    }

    private static boolean isANumber(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (Exception _) {
            return false;
        }
    }

    private static boolean isALeaf(String s) {
        return s.equals("x") || isANumber(s);
    }
}
