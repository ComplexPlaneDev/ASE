package MathPlot.Parsers;

public class AOS {
    public class Parts {
        final String main;
        final String left;
        final String right;

        public Parts(String m, String l, String r) {
            this.main = m;
            this.left = l;
            this.right = r;
        }
    }

    public Parts parse(String input) throws Exception {
        String sExpr = input.trim();

        // Filter redundant ( )
        while (sExpr.charAt(0) == '(' && sExpr.charAt(sExpr.length() - 1) == ')') {
            sExpr = sExpr.substring(1, sExpr.length() - 1).trim();
        }

        sExpr = sExpr.trim();

        int paren = 0;

        char lastOp = '\0';
        int lastOpPos = -1;

        // Search for an operator
        for (int i = 0; i < sExpr.length(); ++i) {
            char c = sExpr.charAt(i);
            if (c == '(') {
                ++paren;
            }
            if (c == ')') {
                --paren;
            }

            // If we are in parens, ignore it
            if (paren > 0) {
                continue;
            }

            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
                // Store an operator if:
                // - it has never been encountered before
                // - last one found was a power, but we found a different one (power is lowest prio)
                // - last one was a power, a multiplication or a division, but we found an
                // addition or a subtraction (+ and - have highest prio)
                //
                // The higher is the prio, the most-at-the-top of the tree the operator should be
                if ((lastOpPos == -1) || (lastOp == '^' && c != '^')
                        || ((lastOp == '*' || lastOp == '/' || lastOp == '^') && (c == '+' || c == '-'))) {
                    lastOp = c;
                    lastOpPos = i;
                }
            }
        }

        if (paren != 0) {
            throw new Exception("Unbalanced paren");
        }

        if (lastOpPos == 0) {
            return new Parts(String.valueOf(lastOp), "0", sExpr.substring(lastOpPos + 1, sExpr.length()));
        }

        if (lastOpPos != -1) {
            return new Parts(String.valueOf(sExpr.charAt(lastOpPos)), sExpr.substring(0, lastOpPos),
                    sExpr.substring(lastOpPos + 1, sExpr.length()));
        }

        if (sExpr.equalsIgnoreCase("x")) {
            return new Parts("x", null, null);
        }

        if (sExpr.matches("[a-z,A-Z]+\s*\\(.*\\)")) {
            int openParen = sExpr.indexOf("(");
            return new Parts(sExpr.substring(0, openParen).toLowerCase(),
                    sExpr.substring(openParen + 1, sExpr.length() - 1), null);
        }

        return new Parts(sExpr, null, null);
    }
}
