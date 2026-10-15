/**
 * Demo driver for the State pattern
 */
public final class Main {

    public static void main(String[] args) {
        System.out.println("=== Traffic Light (interface variant) ===");
        TrafficLight light = new TrafficLight();
        for (int i = 0; i < 5; i++) {
            light.toggle();
        }
    }
}
