/**
 * Aircraft - Abstract base class for all aircraft (a Colleague in the
 * Mediator pattern).
 *
 * An aircraft never talks to the tower, weather station or emergency
 * services directly. It only knows its mediator and raises events through
 * it (requestTakeoff, requestLanding, declareEmergency). In return, the
 * mediator calls back on the aircraft to execute commands (grant clearances,
 * hold, complete takeoff/landing).
 */
public abstract class Aircraft {

    private final String callsign;
    private final String model;
    private double fuelLevel;              // percentage, 0..100
    private AircraftStatus status = AircraftStatus.GROUND;
    private boolean emergency = false;
    private ATCMediator mediator;          // the mediator that coordinates us

    protected Aircraft(String callsign, String model, double fuelLevel) {
        this.callsign = callsign;
        this.model = model;
        this.fuelLevel = fuelLevel;
    }

    /** Called by the mediator when the aircraft is registered. */
    public void setMediator(ATCMediator mediator) {
        this.mediator = mediator;
    }

    // ------------------------------------------------------------------
    // Events the aircraft can raise (all delegated to the mediator)
    // ------------------------------------------------------------------

    public void requestTakeoff() {
        mediator.requestTakeoff(this);
    }

    public void requestLanding() {
        mediator.requestLanding(this);
    }

    public void declareEmergency(String reason) {
        mediator.declareEmergency(this, reason);
    }

    // ------------------------------------------------------------------
    // Commands the mediator executes on the aircraft
    // ------------------------------------------------------------------

    public void grantTakeoffClearance() {
        status = AircraftStatus.TAKEOFF;
    }

    public void grantLandingClearance() {
        status = AircraftStatus.APPROACH;
    }

    public void hold() {
        status = AircraftStatus.HOLDING;
    }

    public void completeTakeoff() {
        status = AircraftStatus.AIRBORNE;
        mediator.deregisterAircraft(this);
    }

    public void completeLanding() {
        status = AircraftStatus.LANDED;
        mediator.deregisterAircraft(this);
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
        if (emergency) {
            status = AircraftStatus.EMERGENCY;
        }
    }

    /** Fuel (in percent) burned by a takeoff or landing operation. */
    public abstract double getFuelBurnPerOperation();

    public void consumeFuel(double amount) {
        fuelLevel = Math.max(0, fuelLevel - amount);
    }

    public boolean isInFlight() {
        return status == AircraftStatus.HOLDING
            || status == AircraftStatus.APPROACH
            || status == AircraftStatus.EMERGENCY;
    }

    // ------------------------------------------------------------------
    // Accessors
    // ------------------------------------------------------------------

    public String getCallsign() { return callsign; }
    public String getModel()    { return model; }
    public double getFuelLevel() { return fuelLevel; }
    public AircraftStatus getStatus() { return status; }
    public boolean isEmergency() { return emergency; }

    @Override
    public String toString() {
        return callsign + " (" + model + ")";
    }
}
