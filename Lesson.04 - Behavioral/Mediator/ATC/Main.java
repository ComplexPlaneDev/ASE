/**
 * Main - Demonstrates the Mediator pattern applied to an Air Traffic
 * Controller.
 *
 * Scenario:
 *   1. Three aircraft register: a takeoff, a low-fuel landing, a normal landing.
 *   2. The ATC processes the queue (low-fuel landing is prioritised).
 *   3. More traffic arrives; a storm hits and operations are held.
 *   4. The storm clears and the queue resumes.
 *   5. A cargo plane declares an engine failure - it jumps to the front of
 *      the queue and emergency services are activated.
 */
public class Main {

    public static void main(String[] args) {
        header("SETUP");
        ControlTower tower = new ControlTower("Tower");
        WeatherStation weather = new WeatherStation();
        EmergencyServices rescue = new EmergencyServices();
        Runway runway1 = new Runway("09L");
        Runway runway2 = new Runway("27R");

        // The mediator wires all colleagues together.
        AirTrafficController atc = new AirTrafficController(
                tower, weather, rescue, runway1, runway2);

        header("PHASE 1 - First traffic: takeoff, low-fuel landing, normal landing");
        CommercialAircraft dal123 = new CommercialAircraft("DAL123", "Boeing 737", 85);
        CommercialAircraft baw456 = new CommercialAircraft("BAW456", "Airbus A320", 20); // low fuel!
        CargoAircraft fdx900 = new CargoAircraft("FDX900", "Boeing 767", 90);

        atc.registerAircraft(dal123);
        atc.registerAircraft(baw456);
        atc.registerAircraft(fdx900);

        dal123.requestTakeoff();
        baw456.requestLanding();
        fdx900.requestLanding();

        atc.printStatus();

        // BAW456 (low fuel) must land before FDX900, and landings before takeoffs.
        atc.processNextInQueue();
        atc.processNextInQueue();
        atc.processNextInQueue();

        header("PHASE 2 - New traffic arrives, then a storm hits");
        CommercialAircraft uae789 = new CommercialAircraft("UAE789", "Airbus A380", 60);
        CargoAircraft ups42 = new CargoAircraft("UPS42", "MD-11", 45);
        CommercialAircraft lhr321 = new CommercialAircraft("LHR321", "Airbus A350", 70);

        atc.registerAircraft(uae789);
        atc.registerAircraft(ups42);
        atc.registerAircraft(lhr321);

        uae789.requestLanding();
        ups42.requestLanding();
        lhr321.requestTakeoff();

        atc.printStatus();

        weather.reportWeather(WeatherCondition.STORM);
        atc.processNextInQueue();   // held due to storm, re-queued
        atc.printStatus();

        header("PHASE 3 - Storm clears, queue resumes");
        weather.reportWeather(WeatherCondition.CLEAR);
        atc.processNextInQueue();   // UAE789 lands

        header("PHASE 4 - Emergency: UPS42 engine failure (still in the queue)");
        ups42.declareEmergency("Engine failure, lost hydraulic system");
        atc.printStatus();          // UPS42 has jumped to the front of the queue

        atc.processNextInQueue();   // emergency landing (before LHR321's takeoff)
        atc.processNextInQueue();   // remaining traffic
        atc.processNextInQueue();

        header("FINAL STATE");
        atc.printStatus();
    }

    private static void header(String title) {
        System.out.println();
        System.out.println("================== " + title + " ==================");
    }
}
