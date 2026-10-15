/**
 * CommercialAircraft - A passenger airliner.
 */
public class CommercialAircraft extends Aircraft {

    public CommercialAircraft(String callsign, String model, double fuelLevel) {
        super(callsign, model, fuelLevel);
    }

    @Override
    public double getFuelBurnPerOperation() {
        return 10.0; // percent of fuel burned per takeoff/landing
    }
}
