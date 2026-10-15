/**
 * CargoAircraft - A freight plane (burns more fuel per operation).
 */
public class CargoAircraft extends Aircraft {

    public CargoAircraft(String callsign, String model, double fuelLevel) {
        super(callsign, model, fuelLevel);
    }

    @Override
    public double getFuelBurnPerOperation() {
        return 15.0; // heavier planes burn more per operation
    }
}
