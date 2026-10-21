import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Abstract class implementing the most common caretaker policy: an LIFO
 * history (a stack of snapshots), as needed for undo support.
 *
 * <p>It shows how an abstract class can sit between the role interface and
 * the concrete classes: it provides the default storage behaviour and exposes
 * protected hooks ({@link #trimHistory()}, {@link #historySize()},
 * {@link #evictOldest()}) so that concrete caretakers only customise the
 * policy they care about (e.g. bounding the history size).</p>
 */
public abstract class AbstractCaretaker implements Caretaker {

    private final Deque<Memento> history = new ArrayDeque<>();

    @Override
    public void save(Memento memento) {
        Objects.requireNonNull(memento, "memento must not be null");
        history.push(memento);
        trimHistory();
    }

    @Override
    public Memento undo() {
        return history.isEmpty() ? null : history.pop();
    }

    /** @return {@code true} if at least one snapshot is available. */
    public boolean canUndo() {
        return !history.isEmpty();
    }

    /** Hook: called after every save; the default policy keeps an unbounded history. */
    protected void trimHistory() {
        // no-op by default
    }

    /** Number of snapshots currently stored (helper for {@link #trimHistory()}). */
    protected int historySize() {
        return history.size();
    }

    /** Removes and returns the oldest snapshot (helper for {@link #trimHistory()}). */
    protected Memento evictOldest() {
        return history.removeLast();
    }
}
