/**
 * State interface for the traffic light example.
 *
 * <p>Each concrete state knows how to react when the button is pressed
 * ({@code on}) and which color it represents.</p>
 */
public interface LightState {

    /** Returns the human-readable color of this state. */
    String color();

    /**
     * Handles a button press while the light is in this state.
     * The implementation performs the state-specific behavior and,
     * if a transition is defined, replaces the context's current state.
     *
     * @param light the context whose state may be changed
     */
    void on(TrafficLight light);
}
