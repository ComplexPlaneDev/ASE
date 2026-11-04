// OBSERVER, generified. The subject publishes events of a specific type E, and
// observers are typed Listener<E>. Generics remove the classic Observer pain:
// no Object payloads, no instanceof/casts in update(), and PECS lets any
// supertype listener subscribe.

import java.util.ArrayList;
import java.util.List;

public class DP_Observer {

    // A typed observer.
    interface Listener<E> {
        void onEvent(E event);
    }

    // The subject/observable, generic over the event type E it emits.
    static final class Observable<E> {
        private final List<Listener<E>> listeners = new ArrayList<>();

        void subscribe(Listener<E> l) { listeners.add(l); }
        void unsubscribe(Listener<E> l) { listeners.remove(l); }

        void publish(E event) {
            for (Listener<E> l : listeners) l.onEvent(event);
        }
    }

    // A couple of concrete event types.
    record UserLoggedIn(String user) { }
    record TemperatureReading(double celsius) { }

    public static void main(String[] args) {
        System.out.println("=== Observer pattern, generified over event type E ===\n");

        // 1. A strongly-typed event stream. onEvent receives UserLoggedIn directly.
        Observable<UserLoggedIn> logins = new Observable<>();
        logins.subscribe(e -> System.out.println("  audit : " + e.user() + " logged in"));
        logins.subscribe(e -> System.out.println("  welcome, " + e.user() + "!"));

        logins.publish(new UserLoggedIn("ada"));
        System.out.println();
        logins.publish(new UserLoggedIn("grace"));

        // 3. A completely different event type — its own type-safe channel.
        System.out.println();
        Observable<TemperatureReading> sensors = new Observable<>();
        sensors.subscribe(r -> {
            if (r.celsius() > 30) System.out.println("  ALERT: hot! " + r.celsius() + "C");
            else System.out.println("  temp ok: " + r.celsius() + "C");
        });
        sensors.publish(new TemperatureReading(22.5));
        sensors.publish(new TemperatureReading(34.1));
    }
}
