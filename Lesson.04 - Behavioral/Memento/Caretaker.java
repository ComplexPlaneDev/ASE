/**
 * CARETAKER role (GoF).
 *
 * <p>The caretaker is responsible for keeping mementos safely. It stores and
 * retrieves them, but it never inspects or modifies the state they contain —
 * that knowledge stays inside the originator. The caretaker is completely
 * decoupled from the originator: the client wires the two together.</p>
 */
public interface Caretaker {

    /** Stores a memento (a snapshot taken by the originator). */
    void save(Memento memento);

    /**
     * Returns the memento to restore next, or {@code null} if there is none.
     * The client passes the result back to the originator's {@code restore}.
     */
    Memento undo();
}
