/**
 * Concrete state: the light is RED (stop).
 * A button press switches the light to GREEN.
 */
public class RedState implements LightState {

    @Override
    public String color() {
        return "RED";
    }

    @Override
    public void on(TrafficLight light) {
        System.out.println("  -> switching to GREEN");
        light.setState(new GreenState());
    }
}
