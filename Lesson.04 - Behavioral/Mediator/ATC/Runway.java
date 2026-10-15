/**
 * Runway - A physical resource managed by the ATC mediator.
 * A runway can be occupied by at most one aircraft at a time.
 */
public class Runway {

    private final String id;
    private boolean available = true;

    public Runway(String id) {
        this.id = id;
    }

    public void occupy()  { available = false; }
    public void release() { available = true; }

    public boolean isAvailable() { return available; }
    public String getId()        { return id; }

    public void handleLanding(Aircraft aircraft) {
        System.out.println("[Runway " + id + "] " + aircraft.getCallsign()
                + " touching down ... taxiing to gate. Runway cleared.");
        release();
    }

    public void handleTakeoff(Aircraft aircraft) {
        System.out.println("[Runway " + id + "] " + aircraft.getCallsign()
                + " cleared for takeoff ... rotated, airborne. Runway cleared.");
        release();
    }
}
