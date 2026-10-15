import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base for concrete mediators.
 *
 * <p>Implements the part of a mediator that is common to every use case:
 * the <em>colleague registry</em>. A concrete mediator only has to implement
 * {@link #createEvent(Colleague, Object)} with its own coordination logic
 * (broadcast, matching, validation, ...) and can reuse the protected helpers
 * below.
 *
 * <p>This is the same "skeleton + hook" idea used for {@code AbstractCaretaker}
 * in the Memento lecture: shared behaviour lives once in the abstract base,
 * each concrete mediator customises only its policy.
 */
public abstract class AbstractMediator implements Mediator {

    private final List<Colleague> colleagues = new ArrayList<>();

    /** Registers a colleague so that it can take part in the interaction. */
    public void addColleague(Colleague colleague) {
        if (colleague == null) {
            throw new IllegalArgumentException("colleague must not be null");
        }
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
        }
    }

    /** @return an unmodifiable view of the registered colleagues. */
    public List<Colleague> getColleagues() {
        return Collections.unmodifiableList(colleagues);
    }

    /** @return the number of registered colleagues. */
    public int colleagueCount() {
        return colleagues.size();
    }

    /** All registered colleagues except {@code sender}. */
    protected List<Colleague> otherColleagues(Colleague sender) {
        List<Colleague> others = new ArrayList<>(colleagues);
        others.remove(sender);
        return others;
    }

    /** Convenience helper: propagates {@code event} to every colleague except the sender. */
    protected void broadcast(Colleague sender, Object event) {
        for (Colleague colleague : otherColleagues(sender)) {
            colleague.onEvent(event);
        }
    }
}
