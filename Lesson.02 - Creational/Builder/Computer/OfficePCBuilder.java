/**
 * Concrete Builder: OfficePCBuilder
 *
 * Knows how to assemble a cost-effective office workstation.
 * Each build step selects business-appropriate components.
 */
public class OfficePCBuilder implements ComputerBuilder {

    private Computer computer;

    public OfficePCBuilder() {
        this.computer = new Computer();
    }

    @Override
    public void buildCpu() {
        computer.setCpu("Intel Core i5-14500 (14 cores, 5.0 GHz)");
    }

    @Override
    public void buildMotherboard() {
        computer.setMotherboard("ASUS ProArt B760-Creator");
    }

    @Override
    public void buildRam() {
        computer.setRamGB(16);
    }

    @Override
    public void buildGpu() {
        // Office PC uses integrated graphics — no discrete GPU
        computer.setGpu(null);
    }

    @Override
    public void buildStorage() {
        computer.setStorage("512 GB NVMe SSD + 2 TB HDD");
    }

    @Override
    public void buildCooling() {
        computer.setCoolingSystem("Stock Air Cooler (quiet fan)");
    }

    @Override
    public void buildPowerSupply() {
        computer.setPowerSupply("450W 80+ Bronze");
    }

    @Override
    public void buildCase() {
        computer.setCaseType("Mini Tower (compact, noise-dampened)");
    }

    @Override
    public void buildPeripherals() {
        computer.addPeripheral("Ergonomic Wireless Keyboard");
        computer.addPeripheral("Wireless Mouse");
        computer.addPeripheral("24\" IPS Monitor");
    }

    @Override
    public Computer getResult() {
        Computer result = this.computer;
        this.computer = new Computer(); // reset for next build
        return result;
    }
}
