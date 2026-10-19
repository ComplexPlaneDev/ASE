/**
 * Command — <b>interface</b> (GoF participant "Command").
 *
 * <p>A {@code Command} object encapsulates everything that is needed to
 * perform a request as a first-class object: the method to invoke
 * ({@link #execute()}) and, optionally, how to reverse it
 * ({@link #undo()}).</p>
 *
 * <p>Because a command is an object, it can be stored in a field, put into
 * a collection, queued, logged or passed around — exactly like any other
 * object. This is what makes the pattern powerful.</p>
 */
public interface Command {

    /**
     * Carries out the request this command represents.
     */
    void execute();

    /**
     * Reverses the effect of {@link #execute()}.
     *
     * <p>Undo is an <em>optional</em> capability: commands that cannot be
     * reversed simply rely on this default implementation, which signals
     * the error at the moment undo is attempted.</p>
     */
    default void undo() {
        throw new UnsupportedOperationException(
                getClass().getSimpleName() + " does not support undo");
    }
}
