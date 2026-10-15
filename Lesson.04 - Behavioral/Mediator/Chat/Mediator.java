/**
 * MEDIATOR role (Gang of Four, GoF).
 *
 * <p>Defines the interface for communicating with colleagues. A colleague
 * never talks to another colleague directly: it sends a request to its
 * mediator, and the mediator decides which other colleagues react — and how.
 *
 * <p>Programmed as an {@code interface} so that every concrete mediator is
 * pluggable behind the same contract.
 */
public interface Mediator {

    /**
     * Coordinates the interaction between colleagues after {@code colleague}
     * has raised {@code event}.
     *
     * @param colleague the colleague that raised the event (the "sender")
     * @param event     the event payload (use-case specific)
     */
    void createEvent(Colleague colleague, Object event);
}
