/**
 * Observer (GoF participant).
 *
 * <p>Defines the {@code update()} method for objects that should be
 * notified when the state of a Subject changes. Concrete observers
 * implement this interface and are registered with a Subject.</p>
 */
public interface Observer {

    /**
     * Called by the Subject after its state has changed.
     * The observer typically reads (pulls) the new state from the
     * Subject, or uses data that was pushed to it.
     */
    void update();
}
