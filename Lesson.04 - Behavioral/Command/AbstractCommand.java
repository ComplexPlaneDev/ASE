/**
 * AbstractCommand — <b>abstract class</b>.
 *
 * <p>Base class for commands that act on a {@link Receiver}. It applies the
 * Template Method pattern:</p>
 *
 * <ul>
 *   <li>it owns the reference to the receiver (injected through the
 *       constructor — this is the "binding" between command and receiver);</li>
 *   <li>it implements {@link #execute()} and {@link #undo()} as final
 *       template methods;</li>
 *   <li>subclasses only have to implement the protected hooks
 *       {@code doExecute()} and {@code doUndo()}.</li>
 * </ul>
 *
 * <p>The type parameter {@code R} makes the binding type-safe: a command
 * that acts on a {@code Light} can only be constructed with a
 * {@code Light}.</p>
 */
public abstract class AbstractCommand<R extends Receiver> implements Command {

    /** The object on which this command performs its work. */
    protected final R receiver;

    protected AbstractCommand(R receiver) {
        if (receiver == null) {
            throw new IllegalArgumentException("receiver must not be null");
        }
        this.receiver = receiver;
    }

    @Override
    public final void execute() {
        doExecute();
    }

    @Override
    public final void undo() {
        doUndo();
    }

    /** Performs the actual request on {@link #receiver}. */
    protected abstract void doExecute();

    /** Reverses the effect of {@link #doExecute()} on {@link #receiver}. */
    protected abstract void doUndo();
}
