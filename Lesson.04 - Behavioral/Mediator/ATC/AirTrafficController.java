import java.util.LinkedHashMap;
import java.util.Map;

/**
 * AirTrafficController - The Concrete Mediator in the Mediator pattern.
 *
 * It is the only object that knows all the colleagues (aircraft, tower,
 * weather station, emergency services, runways). All cross-colleague
 * communication goes through it, which:
 *
 *   - keeps the colleagues loosely coupled (an aircraft does not know the
 *     tower, the rescue team or the runways exist),
 *   - centralises the coordination policy (priority queue, weather holds,
 *     emergency handling) in a single, easy-to-test class.
 */
public class AirTrafficController implements ATCMediator {

    /** Aircraft with less than this fuel percentage get an expedited landing. */
    private static final double LOW_FUEL_THRESHOLD = 25.0;

    private final Map<String, Aircraft> aircraftRegistry = new LinkedHashMap<>();
    private final FlightQueue flightQueue = new FlightQueue();
    private final Runway[] runways;
    private final WeatherStation weatherStation;
    private final EmergencyServices emergencyServices;
    private final ControlTower controlTower;

    private WeatherCondition currentWeather = WeatherCondition.CLEAR;

    public AirTrafficController(ControlTower controlTower,
                                WeatherStation weatherStation,
                                EmergencyServices emergencyServices,
                                Runway... runways) {
        this.controlTower = controlTower;
        this.weatherStation = weatherStation;
        this.emergencyServices = emergencyServices;
        this.runways = runways;

        // Wire the colleagues to this mediator.
        this.controlTower.setMediator(this);
        this.weatherStation.setMediator(this);
        this.emergencyServices.setMediator(this);

        log("Air Traffic Controller online. " + runways.length
                + " runway(s) operational. Weather: " + currentWeather);
    }

    // ------------------------------------------------------------------
    // Registration
    // ------------------------------------------------------------------

    @Override
    public void registerAircraft(Aircraft aircraft) {
        aircraftRegistry.put(aircraft.getCallsign(), aircraft);
        aircraft.setMediator(this);
        log("Aircraft " + aircraft.getCallsign() + " (" + aircraft.getModel()
                + ", fuel " + String.format("%.0f%%", aircraft.getFuelLevel())
                + ") registered.");
    }

    @Override
    public void deregisterAircraft(Aircraft aircraft) {
        aircraftRegistry.remove(aircraft.getCallsign());
        log("Aircraft " + aircraft.getCallsign() + " no longer under our control.");
    }

    // ------------------------------------------------------------------
    // Events raised by aircraft
    // ------------------------------------------------------------------

    @Override
    public void requestTakeoff(Aircraft aircraft) {
        FlightRequest request = new FlightRequest(
                FlightRequest.Type.TAKEOFF, aircraft, "Takeoff request");
        flightQueue.enqueue(request);
        controlTower.announce(aircraft.getCallsign()
                + " requests takeoff - you are in the departure queue.");
    }

    @Override
    public void requestLanding(Aircraft aircraft) {
        FlightRequest.Type type = aircraft.getFuelLevel() < LOW_FUEL_THRESHOLD
                ? FlightRequest.Type.LOW_FUEL_LANDING
                : FlightRequest.Type.LANDING;
        FlightRequest request = new FlightRequest(type, aircraft,
                "Landing request" + (type == FlightRequest.Type.LOW_FUEL_LANDING
                        ? " [LOW FUEL - expedite]" : ""));
        flightQueue.enqueue(request);
        controlTower.announce(aircraft.getCallsign()
                + " requests landing - you are in the arrival queue.");
    }

    @Override
    public void declareEmergency(Aircraft aircraft, String reason) {
        log("EMERGENCY declared by " + aircraft.getCallsign() + ": " + reason);
        aircraft.setEmergency(true);

        // An emergency supersedes any previous request of this aircraft:
        // remove old requests and re-queue with top priority.
        flightQueue.removeFor(aircraft);
        FlightRequest request = new FlightRequest(
                FlightRequest.Type.EMERGENCY, aircraft, "EMERGENCY: " + reason);
        flightQueue.enqueue(request);

        emergencyServices.respondToEmergency(aircraft, reason);
        controlTower.announce("EMERGENCY: " + aircraft.getCallsign()
                + " has declared an emergency. All traffic, give way.");
    }

    // ------------------------------------------------------------------
    // Events raised by other colleagues
    // ------------------------------------------------------------------

    @Override
    public void reportWeather(WeatherCondition condition) {
        this.currentWeather = condition;
        log("Weather update: " + condition);
        controlTower.announce("Weather update: " + condition + ".");

        if (condition == WeatherCondition.STORM) {
            // Put every airborne aircraft on hold; the queue keeps them
            // in place and they will be re-processed once weather clears.
            for (Aircraft aircraft : aircraftRegistry.values()) {
                if (aircraft.isInFlight()) {
                    aircraft.hold();
                }
            }
            controlTower.announce("Storm in progress: all landings and takeoffs ON HOLD.");
        } else {
            controlTower.announce("Operations may resume.");
        }
    }

    @Override
    public void reportEmergencyResolved(Aircraft aircraft) {
        log("Emergency for " + aircraft.getCallsign() + " resolved - "
                + "aircraft cleared, all units returning to base.");
        controlTower.announce(aircraft.getCallsign()
                + ": emergency resolved. Normal operations continue.");
    }

    @Override
    public void reportReadback(Aircraft aircraft, String clearance) {
        log(aircraft.getCallsign() + " readback confirmed: \"" + clearance + "\"");
    }

    // ------------------------------------------------------------------
    // Queue processing
    // ------------------------------------------------------------------

    @Override
    public void processNextInQueue() {
        FlightRequest request = flightQueue.dequeue();
        if (request == null) {
            log("Queue is empty - nothing to process.");
            return;
        }

        Aircraft aircraft = request.getAircraft();
        Runway runway = assignRunway();

        switch (request.getType()) {
            case EMERGENCY:
                emergencyServices.prepareForEmergencyLanding(aircraft, runway);
                aircraft.grantLandingClearance();
                controlTower.confirmReadback(aircraft,
                        "emergency landing runway " + runway.getId());
                runway.handleLanding(aircraft);
                aircraft.consumeFuel(aircraft.getFuelBurnPerOperation());
                emergencyServices.postLandingAssistance(aircraft);
                // The rescue team reports back THROUGH the mediator (a
                // colleague can also initiate events, not just receive them).
                emergencyServices.completeAssistance(aircraft);
                aircraft.completeLanding();
                break;

            case LOW_FUEL_LANDING:
                log("Low fuel - expediting landing of " + aircraft.getCallsign()
                        + " (fuel " + String.format("%.0f%%", aircraft.getFuelLevel()) + ").");
                aircraft.grantLandingClearance();
                controlTower.confirmReadback(aircraft,
                        "cleared to land runway " + runway.getId());
                runway.handleLanding(aircraft);
                aircraft.consumeFuel(aircraft.getFuelBurnPerOperation());
                aircraft.completeLanding();
                break;

            case LANDING:
                if (currentWeather == WeatherCondition.STORM) {
                    aircraft.hold();
                    flightQueue.enqueue(request);   // re-queue, keep position
                    controlTower.announce(aircraft.getCallsign()
                            + " placed on HOLD - storm in progress.");
                    return;
                }
                aircraft.grantLandingClearance();
                controlTower.confirmReadback(aircraft,
                        "cleared to land runway " + runway.getId());
                runway.handleLanding(aircraft);
                aircraft.consumeFuel(aircraft.getFuelBurnPerOperation());
                aircraft.completeLanding();
                break;

            case TAKEOFF:
                if (currentWeather == WeatherCondition.STORM) {
                    aircraft.hold();
                    flightQueue.enqueue(request);   // re-queue, keep position
                    controlTower.announce(aircraft.getCallsign()
                            + " departure DELAYED - storm in progress.");
                    return;
                }
                aircraft.grantTakeoffClearance();
                controlTower.confirmReadback(aircraft,
                        "cleared for takeoff runway " + runway.getId());
                runway.handleTakeoff(aircraft);
                aircraft.consumeFuel(aircraft.getFuelBurnPerOperation());
                aircraft.completeTakeoff();
                break;
        }
    }

    /** Assigns the first available runway (all runways are equivalent here). */
    private Runway assignRunway() {
        for (Runway runway : runways) {
            if (runway.isAvailable()) {
                runway.occupy();
                return runway;
            }
        }
        // Fallback (should not happen in normal operation): reuse first runway.
        log("WARNING: all runways occupied - reusing runway " + runways[0].getId());
        runways[0].occupy();
        return runways[0];
    }

    // ------------------------------------------------------------------
    // Diagnostics
    // ------------------------------------------------------------------

    @Override
    public void printStatus() {
        System.out.println("[ATC]    ===== ATC STATUS =====");
        System.out.println("[ATC]    Weather: " + currentWeather);
        if (aircraftRegistry.isEmpty()) {
            System.out.println("[ATC]    No aircraft under control.");
        } else {
            for (Aircraft aircraft : aircraftRegistry.values()) {
                System.out.println("[ATC]    - " + aircraft.getCallsign()
                        + " " + aircraft.getModel()
                        + " | status: " + aircraft.getStatus()
                        + " | fuel: " + String.format("%.0f%%", aircraft.getFuelLevel())
                        + (aircraft.isEmergency() ? " | ** EMERGENCY **" : ""));
            }
        }
        flightQueue.print();
        System.out.println("[ATC]    ======================");
    }

    private void log(String message) {
        System.out.println("[ATC]    " + message);
    }
}
