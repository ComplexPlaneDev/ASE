/**
 * COLLEAGUE role (Gang of Four, GoF).
 *
 * <p>A colleague is a participant in the interaction. It holds a reference
 * to its mediator and knows <em>only</em> the {@link Mediator} interface —
 * never the concrete mediator, and never any other colleague. That single
 * dependency is what keeps the whole system loosely coupled.
 *
 * <p>Declared as an abstract class (not an interface) because every
 * colleague shares the same state ({@code mediator}) and the same sending
 * behaviour ({@link #notify(Object)}); only the reaction to events
 * ({@link #onEvent(Object)}) is left to concrete colleagues.
 */
public abstract class Colleague {

    private Mediator mediator;

    /** Injects the mediator (done by the client or the mediator at wiring time). */
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Sends a request to the mediator. The mediator decides which other
     * colleagues react and how — the sender never addresses them directly.
     *
     * @param event the event payload (use-case specific)
     */
    public final void notify(Object event) {
        if (mediator == null) {
            throw new IllegalStateException("No mediator attached to this colleague");
        }
        mediator.createEvent(this, event);
    }

    /**
     * Reacts to an event propagated by the mediator.
     * Implemented by each concrete colleague.
     */
    public abstract void onEvent(Object event);
}
