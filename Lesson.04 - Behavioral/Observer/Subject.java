import java.util.ArrayList;
import java.util.List;

/**
 * Subject (GoF participant) - abstract class.
 *
 * <p>Maintains the list of registered Observers and provides the
 * operations to attach, detach and notify them. Concrete subjects
 * extend this class and call {@link #notifyObservers()} whenever
 * their state changes.</p>
 */
public abstract class Subject {

    private final List<Observer> observers = new ArrayList<>();

    /** Registers an observer so that it receives change notifications. */
    public void attach(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /** Unregisters an observer; it will no longer receive notifications. */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies every registered observer.
     * Iterates over a copy so that observers may safely attach or
     * detach other observers while being notified.
     */
    protected void notifyObservers() {
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update();
        }
    }

    /** Number of currently registered observers (useful for demos/tests). */
    public int getObserverCount() {
        return observers.size();
    }
}
