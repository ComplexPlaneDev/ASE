/**
 * FlightRequest - A single event in the flight queue (takeoff, landing,
 * low-fuel landing or emergency). Requests are comparable so the queue can
 * always serve the highest-priority one first.
 *
 * Priority order (lower ordinal = higher priority):
 *   1. EMERGENCY          - life or death, always first
 *   2. LOW_FUEL_LANDING   - aircraft with < 25% fuel
 *   3. LANDING            - normal landings
 *   4. TAKEOFF            - normal departures
 * Ties are broken by arrival time (FIFO).
 */
public class FlightRequest implements Comparable<FlightRequest> {

    public enum Type {
        EMERGENCY,
        LOW_FUEL_LANDING,
        LANDING,
        TAKEOFF
    }

    private final Type type;
    private final Aircraft aircraft;
    private final String description;
    private final long timestamp;
    private static long clock = 0;   // simple monotonic ordering

    public FlightRequest(Type type, Aircraft aircraft, String description) {
        this.type = type;
        this.aircraft = aircraft;
        this.description = description;
        this.timestamp = clock++;
    }

    @Override
    public int compareTo(FlightRequest other) {
        if (this.type != other.type) {
            return Integer.compare(this.type.ordinal(), other.type.ordinal());
        }
        return Long.compare(this.timestamp, other.timestamp);
    }

    public Type getType()      { return type; }
    public Aircraft getAircraft() { return aircraft; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return type + " for " + aircraft.getCallsign() + " - " + description;
    }
}
