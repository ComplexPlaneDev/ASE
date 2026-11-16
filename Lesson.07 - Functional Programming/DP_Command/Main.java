import java.util.ArrayList;
import java.util.List;

/**
 * EXAMPLE 17 — Design pattern, functionally: COMMAND.
 *
 * COMMAND: "encapsulate a request as an object" so it can be stored, queued, logged,
 * undone, and executed later.
 *
 * The OO form is a Command interface + one class per action + an invoker. A no-argument,
 * no-result request is exactly a Runnable (one that produces a value is a Supplier).
 * Capture the action — with its arguments — in a lambda (a closure over them, Section 9),
 * queue the thunks in a List<Runnable>, and the "invoker" is queue.forEach(Runnable::run).
 * A macro command is just a Runnable that calls others.
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    // A bank account whose operations we capture as Runnable thunks.
    static final class Account {
        private int balanceCents = 0;
        void deposit(int c)  { balanceCents += c; }
        void withdraw(int c) { balanceCents -= c; }
        int balance()        { return balanceCents; }
    }

    public static void main(String[] args) {
        section("COMMAND: a request is a captured lambda (Runnable), queued and run later");
        Account acct = new Account();

        // Each "command" is a Runnable closing over its arguments — no Command class needed.
        List<Runnable> queue = new ArrayList<>();
        queue.add(() -> acct.deposit(100_00));
        queue.add(() -> acct.withdraw(30_00));
        queue.add(() -> acct.deposit(5_00));

        note("OO form: interface Command{execute()} + Deposit/Withdraw classes + an invoker.");
        note("FP form: each command is a Runnable; the queue is just List<Runnable>.");
        note("balance before running the queue = " + money(acct.balance()));
        queue.forEach(Runnable::run);                 // the "invoker": run every queued command
        note("balance after  running the queue = " + money(acct.balance()) + "   (+100 -30 +5)");

        section("Because commands are values, the invoker gets macro commands for free");
        // A compound command is simply a Runnable that runs others in sequence.
        Runnable macro = () -> { acct.deposit(10_00); acct.deposit(10_00); };
        macro.run();
        note("ran a macro command (two deposits) -> balance = " + money(acct.balance()));
        note("");
        note("Command = a deferred call captured in a lambda (Sections 6, 9). The request is");
        note("a value you can store, queue, and run later — no per-action Command class.");
    }

    static String money(int cents) {
        return String.format("$%d.%02d", cents / 100, Math.abs(cents % 100));
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
