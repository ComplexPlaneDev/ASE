import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * FlightQueue - A priority queue that handles departures, landings and
 * emergencies. The mediator enqueues FlightRequest objects and dequeues
 * them in priority order (emergency > low-fuel landing > landing > takeoff,
 * FIFO within the same priority).
 */
public class FlightQueue {

    private final PriorityQueue<FlightRequest> queue = new PriorityQueue<>();

    public void enqueue(FlightRequest request) {
        queue.add(request);
        System.out.println("[Queue]  Enqueued: " + request
                + "  (position " + positionOf(request.getAircraft()) + ")");
    }

    /** Returns and removes the highest-priority request, or null if empty. */
    public FlightRequest dequeue() {
        FlightRequest request = queue.poll();
        if (request != null) {
            System.out.println("[Queue]  Dequeued: " + request);
        }
        return request;
    }

    /** Removes all pending requests of a given aircraft (e.g. before an emergency re-queues it). */
    public boolean removeFor(Aircraft aircraft) {
        return queue.removeIf(r -> r.getAircraft() == aircraft);
    }

    /** 1-based position of an aircraft's next request, or -1 if absent. */
    public int positionOf(Aircraft aircraft) {
        List<FlightRequest> ordered = orderedSnapshot();
        for (int i = 0; i < ordered.size(); i++) {
            if (ordered.get(i).getAircraft() == aircraft) {
                return i + 1;
            }
        }
        return -1;
    }

    public boolean isEmpty() { return queue.isEmpty(); }
    public int size()        { return queue.size(); }

    public void print() {
        List<FlightRequest> ordered = orderedSnapshot();
        System.out.println("[Queue]  --- Current flight queue (" + ordered.size() + " pending) ---");
        for (int i = 0; i < ordered.size(); i++) {
            System.out.println("[Queue]    " + (i + 1) + ". " + ordered.get(i));
        }
        if (ordered.isEmpty()) {
            System.out.println("[Queue]    <empty>");
        }
        System.out.println("[Queue]  ---------------------------------------------------");
    }

    /** A copy of the queue in true priority order (heap iteration is unsorted). */
    private List<FlightRequest> orderedSnapshot() {
        List<FlightRequest> snapshot = new ArrayList<>(queue);
        snapshot.sort(FlightRequest::compareTo);
        return snapshot;
    }
}
