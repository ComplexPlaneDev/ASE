/**
 * Concrete state: the light is YELLOW (caution).
 * A button press switches the light back to RED.
 */
public class YellowState implements LightState {

    @Override
    public String color() {
        return "YELLOW";
    }

    @Override
    public void on(TrafficLight light) {
        System.out.println("  -> switching to RED");
        light.setState(new RedState());
    }
}
