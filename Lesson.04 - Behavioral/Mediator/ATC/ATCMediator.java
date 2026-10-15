/**
 * ATCMediator - The Mediator interface (Mediator pattern).
 *
 * Defines the interface through which all colleagues (Aircraft, ControlTower,
 * WeatherStation, EmergencyServices) communicate. No colleague talks to
 * another colleague directly; every message goes through the mediator, which
 * centralises the coordination logic (queueing, prioritisation, weather
 * holds, emergency handling).
 */
public interface ATCMediator {

    // ----- Aircraft registration ---------------------------------------

    /** Registers an aircraft so the ATC can manage it. */
    void registerAircraft(Aircraft aircraft);

    /** Unregisters an aircraft (it has departed or landed). */
    void deregisterAircraft(Aircraft aircraft);

    // ----- Events raised by aircraft ------------------------------------

    /** An aircraft requests clearance for takeoff. */
    void requestTakeoff(Aircraft aircraft);

    /** An aircraft requests clearance to land. */
    void requestLanding(Aircraft aircraft);

    /** An aircraft declares an emergency (jumps to the front of the queue). */
    void declareEmergency(Aircraft aircraft, String reason);

    // ----- Events raised by other colleagues ----------------------------

    /** The weather station reports a new weather condition. */
    void reportWeather(WeatherCondition condition);

    /** Emergency services report that an emergency has been resolved. */
    void reportEmergencyResolved(Aircraft aircraft);

    /** The tower reports a pilot's readback of a granted clearance. */
    void reportReadback(Aircraft aircraft, String clearance);

    // ----- Operations ----------------------------------------------------

    /** Processes the next (highest-priority) request in the flight queue. */
    void processNextInQueue();

    /** Prints the current state of the ATC system. */
    void printStatus();
}
