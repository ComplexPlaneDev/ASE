import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * EXAMPLE 18 — Design pattern, functionally: OBSERVER.
 *
 * OBSERVER: "notify dependent objects when a subject's state changes."
 *
 * The OO form is a Subject holding a List<Observer> of listener classes with an
 * addObserver / update protocol. Functionally, a listener is a Consumer<Event>
 * (Section 7). The subject keeps a List<Consumer<Event>> and publishes with
 * listeners.forEach(c -> c.accept(event)) — no Observer interface, no concrete listener
 * classes. (This is the shape under reactive libraries' subscribe(callback).)
 *
 * Run in VSCode: open this file and press the "Run" button above main(), or:
 *     javac Main.java && java Main
 */
public class Main {

    public static void main(String[] args) {
        section("OBSERVER: listeners are Consumer<Event>, the subject holds a list of them");
        // The "subject": anyone interested registers a Consumer; publish() notifies all.
        List<Consumer<String>> listeners = new ArrayList<>();
        List<String> log = new ArrayList<>();         // one observer records into a log

        listeners.add(evt -> log.add("audit: " + evt));
        listeners.add(evt -> note("  [listener] email sent for event: " + evt));
        listeners.add(evt -> note("  [listener] metric incremented for: " + evt));

        note("OO form: interface Observer + concrete classes + subject.addObserver(...).");
        note("FP form: List<Consumer<String>>; publish = listeners.forEach(c -> c.accept(evt)).");
        publish(listeners, "ORDER_PLACED");
        publish(listeners, "ORDER_SHIPPED");
        note("audit log after publishing = " + log);

        section("Registering a new observer is just adding a Consumer to the list");
        listeners.add(evt -> note("  [listener] NEW observer reacting to: " + evt));
        publish(listeners, "ORDER_DELIVERED");
        note("");
        note("Observer = a list of Consumers (Section 7). No listener interfaces required;");
        note("this is exactly what a reactive subscribe(callback) does under the hood.");
    }

    // The subject's notify step: hand the event to every registered listener.
    static <E> void publish(List<Consumer<E>> listeners, E event) {
        listeners.forEach(listener -> listener.accept(event));
    }

    // ---- tiny console helpers (replace the old shared Section class) ----
    static void section(String title) {
        System.out.println();
        System.out.println("  -> " + title);
        System.out.println("  " + "-".repeat(Math.min(76, title.length() + 3)));
    }
    static void note(String line) { System.out.println("     " + line); }
}
