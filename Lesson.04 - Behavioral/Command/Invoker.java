import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Invoker — <b>concrete class</b> (GoF participant "Invoker").
 *
 * <p>The invoker is the object that <em>triggers</em> a command, e.g. a UI
 * button, a menu entry or an event handler. It knows nothing about the
 * concrete request: it only calls {@link Command#execute()} on whatever
 * command it has been given.</p>
 *
 * <p>This particular invoker also keeps an undo/redo history, which shows
 * one of the classic benefits of the pattern: because a request is an
 * object, it can be pushed onto a stack and re-executed or reversed later.
 * (A plain invoker would only have {@code execute(Command)}.)</p>
 */
public class Invoker {

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    /** Executes the given command and records it in the undo history. */
    public void execute(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("command must not be null");
        }
        command.execute();
        undoStack.push(command);
        redoStack.clear();          // a new action invalidates the redo branch
    }

    /** Reverses the most recently executed command. */
    public void undo() {
        if (undoStack.isEmpty()) {
            return;
        }
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    /** Re-executes a command that was previously undone. */
    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        }
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }
}
