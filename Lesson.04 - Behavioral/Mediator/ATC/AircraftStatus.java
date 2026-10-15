/**
 * AircraftStatus - Lifecycle states of an aircraft under ATC management.
 */
public enum AircraftStatus {
    GROUND,      // on the ground, not yet in the active queue
    HOLDING,     // airborne, circling while waiting for clearance
    APPROACH,    // landing clearance granted, on final approach
    TAKEOFF,     // takeoff clearance granted, rolling
    AIRBORNE,    // has taken off, no longer managed by this ATC
    LANDED,      // has landed, no longer managed by this ATC
    EMERGENCY    // emergency declared, highest priority
}
