/**
 * MEMENTO role (GoF).
 *
 * <p>A memento stores the internal state of an originator at a given point in
 * time. This interface is deliberately a <em>narrow interface</em>: it exposes
 * only metadata that is safe to share (here, the timestamp of the snapshot).
 * The actual state is kept private inside the concrete memento and can be read
 * back only by its originator.</p>
 */
public interface Memento {

    /** Metadata: when the snapshot was taken. Safe for any object to read. */
    long getTimestamp();
}
