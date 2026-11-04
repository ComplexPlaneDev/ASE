// VISITOR, generified. You already built Visitor with concrete types (where
// every visit* method returned void or a fixed type). Adding a RESULT type
// parameter R turns the visitor into a type-safe "fold" that can return any
// type — different R per traversal — with no casts and no shared mutable state.

public class DP_Visitor {

    // ---------- The element hierarchy (a tiny expression tree) ----------
    interface Expr {
        // The classic double-dispatch accept(), now parameterized by the
        // visitor's RESULT type R. accept returns whatever the visitor computes.
        <R> R accept(Visitor<R> v);
    }

    record Num(double value) implements Expr {
        public <R> R accept(Visitor<R> v) { return v.visitNum(this); }
    }
    record Add(Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> v) { return v.visitAdd(this); }
    }
    record Mul(Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> v) { return v.visitMul(this); }
    }

    // ---------- The generic visitor: R is the type each traversal produces ----------
    interface Visitor<R> {
        R visitNum(Num n);
        R visitAdd(Add a);
        R visitMul(Mul m);
    }

    // Visitor #1: produces a Double (evaluate the expression).
    static final class EvalVisitor implements Visitor<Double> {
        public Double visitNum(Num n) { return n.value(); }
        public Double visitAdd(Add a) { return a.left().accept(this) + a.right().accept(this); }
        public Double visitMul(Mul m) { return m.left().accept(this) * m.right().accept(this); }
    }

    // Visitor #2: produces a String (pretty-print). SAME elements, different R.
    static final class PrintVisitor implements Visitor<String> {
        public String visitNum(Num n) { return String.valueOf(n.value()); }
        public String visitAdd(Add a) { return "(" + a.left().accept(this) + " + " + a.right().accept(this) + ")"; }
        public String visitMul(Mul m) { return "(" + m.left().accept(this) + " * " + m.right().accept(this) + ")"; }
    }

    // Visitor #3: produces an Integer (count the leaf numbers).
    static final class CountVisitor implements Visitor<Integer> {
        public Integer visitNum(Num n) { return 1; }
        public Integer visitAdd(Add a) { return a.left().accept(this) + a.right().accept(this); }
        public Integer visitMul(Mul m) { return m.left().accept(this) + m.right().accept(this); }
    }

    public static void main(String[] args) {
        System.out.println("=== Visitor pattern, generified over result type R ===\n");

        // (2 + 3) * 4
        Expr e = new Mul(new Add(new Num(2), new Num(3)), new Num(4));

        double value   = e.accept(new EvalVisitor());   // R = Double
        String printed = e.accept(new PrintVisitor());  // R = String
        int    leaves  = e.accept(new CountVisitor());  // R = Integer

        System.out.println("expression : " + printed);
        System.out.println("evaluate   : " + value);
        System.out.println("leaf count : " + leaves);
    }
}
