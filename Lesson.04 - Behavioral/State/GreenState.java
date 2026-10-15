/**
 * Concrete state: the light is GREEN (go).
 * A button press switches the light to YELLOW.
 */
public class GreenState implements LightState {

    @Override
    public String color() {
        return "GREEN";
    }

    @Override
    public void on(TrafficLight light) {
        System.out.println("  -> switching to YELLOW");
        light.setState(new YellowState());
    }
}
