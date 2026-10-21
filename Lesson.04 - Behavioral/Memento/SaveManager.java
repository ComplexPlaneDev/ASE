import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Concrete CARETAKER for the game: a MAP of named save slots plus an
 * auto-save slot. Shows that the caretaker's storage policy is pluggable —
 * here it is not a stack but a key/value store, and {@link #undo()} is a
 * non-destructive peek at the last auto-save (unlike the destructive pop of a
 * stack-based caretaker).
 */
public final class SaveManager implements Caretaker {

    private static final String AUTO_SLOT = "auto";

    private final Map<String, Memento> slots = new LinkedHashMap<>();

    @Override
    public void save(Memento memento) {
        slots.put(AUTO_SLOT, memento);
    }

    /** Non-destructive: returns the last auto-save without removing it. */
    @Override
    public Memento undo() {
        return slots.get(AUTO_SLOT);
    }

    public void saveToSlot(String slot, Memento memento) {
        slots.put(slot, memento);
    }

    /** @return the snapshot stored in the slot, or {@code null} if the slot is empty. */
    public Memento loadFromSlot(String slot) {
        return slots.get(slot);
    }

    public Set<String> availableSlots() {
        return Collections.unmodifiableSet(slots.keySet());
    }
}
