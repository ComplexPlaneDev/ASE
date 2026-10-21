/**
 * ORIGINATOR role (GoF).
 *
 * <p>The originator is the object whose state must be captured and restored.
 * It knows how to build a memento from its own internal state and how to
 * restore that state from a memento. No other role may inspect or modify the
 * state stored in a memento.</p>
 */
public interface Originator {

    /** Captures the current internal state into a new memento. */
    Memento createMemento();

    /** Restores the internal state from the given memento. */
    void restore(Memento memento);
}
