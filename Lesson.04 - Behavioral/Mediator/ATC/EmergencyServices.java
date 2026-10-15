/**
 * EmergencyServices - A Colleague in the Mediator pattern. Fire/medical
 * response teams. The mediator (ATC) activates them when an emergency is
 * declared or when an emergency landing is being executed.
 */
public class EmergencyServices {

    private ATCMediator mediator;

    public void setMediator(ATCMediator mediator) {
        this.mediator = mediator;
    }

    /** Called by the mediator as soon as an emergency is declared. */
    public void respondToEmergency(Aircraft aircraft, String reason) {
        System.out.println("[Rescue] EMERGENCY RESPONSE: units dispatched for "
                + aircraft.getCallsign() + " - " + reason);
        System.out.println("[Rescue] Fire trucks rolling, medical team on standby.");
    }

    /** Called by the mediator when an emergency landing is being executed. */
    public void prepareForEmergencyLanding(Aircraft aircraft, Runway runway) {
        System.out.println("[Rescue] Preparing for emergency landing of "
                + aircraft.getCallsign() + " on runway " + runway.getId());
        System.out.println("[Rescue] Runway " + runway.getId() + " cleared, crash crew in position.");
    }

    /** Called by the mediator after an emergency landing is complete. */
    public void postLandingAssistance(Aircraft aircraft) {
        System.out.println("[Rescue] " + aircraft.getCallsign() + " landed safely. "
                + "Passengers being evacuated, aircraft inspected.");
    }

    /**
     * Called by the mediator once assistance is done. This is an event the
     * colleague INITIATES: it reports the resolution through the mediator
     * (rather than touching the ATC's state directly), which then clears
     * the aircraft and notifies the tower.
     */
    public void completeAssistance(Aircraft aircraft) {
        mediator.reportEmergencyResolved(aircraft);
    }
}
