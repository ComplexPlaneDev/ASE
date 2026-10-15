/**
 * ControlTower - A Colleague in the Mediator pattern. The tower relays
 * announcements to all pilots. The mediator uses it to broadcast decisions
 * (clearances, holds, weather warnings) to the "outside world".
 */
public class ControlTower {

    private final String name;
    private ATCMediator mediator;

    public ControlTower(String name) {
        this.name = name;
    }

    public void setMediator(ATCMediator mediator) {
        this.mediator = mediator;
    }

    /** Broadcasts an announcement to all aircraft. */
    public void announce(String message) {
        System.out.println("[Tower]  " + name + ": " + message);
    }

    /**
     * The pilot has read back a clearance; the tower reports it THROUGH the
     * mediator (an event the colleague initiates). The ATC only executes
     * the runway operation once the readback is confirmed.
     */
    public void confirmReadback(Aircraft aircraft, String clearance) {
        mediator.reportReadback(aircraft, clearance);
    }
}
