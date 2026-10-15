/**
 * Context of the traffic light example.
 *
 * <p>The context holds a reference to the current {@link LightState} and
 * delegates every request to it. It contains no state-dependent logic:
 * all behavior that depends on the current color lives in the concrete
 * state classes.</p>
 */
public class TrafficLight {

    private LightState state;

    public TrafficLight() {
        this.state = new RedState(); // initial state
    }

    /** Delegates the button press to the current state. */
    public void toggle() {
        System.out.println("  Light is " + state.color() + " -> button pressed");
        state.on(this);
    }

    public LightState getState() {
        return state;
    }

    /** Called by concrete states to change the current state. */
    public void setState(LightState state) {
        this.state = state;
    }
}
