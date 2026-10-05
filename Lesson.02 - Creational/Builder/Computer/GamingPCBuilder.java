/**
 * Concrete Builder: GamingPCBuilder
 *
 * Knows how to assemble a high-end gaming PC.
 * Each build step selects gaming-grade components.
 */
public class GamingPCBuilder implements ComputerBuilder {

    private Computer computer;

    public GamingPCBuilder() {
        this.computer = new Computer();
    }

    @Override
    public void buildCpu() {
        computer.setCpu("Intel Core i9-14900K (24 cores, 6.0 GHz)");
    }

    @Override
    public void buildMotherboard() {
        computer.setMotherboard("ASUS ROG Maximus Z790 Hero");
    }

    @Override
    public void buildRam() {
        computer.setRamGB(64);
    }

    @Override
    public void buildGpu() {
        computer.setGpu("NVIDIA GeForce RTX 4090 (24 GB VRAM)");
    }

    @Override
    public void buildStorage() {
        computer.setStorage("2 TB NVMe SSD (PCIe 5.0)");
    }

    @Override
    public void buildCooling() {
        computer.setCoolingSystem("360mm Liquid Cooling (AIO)");
    }

    @Override
    public void buildPowerSupply() {
        computer.setPowerSupply("1000W 80+ Platinum");
    }

    @Override
    public void buildCase() {
        computer.setCaseType("Full Tower (tempered glass, RGB)");
    }

    @Override
    public void buildPeripherals() {
        computer.addPeripheral("Mechanical RGB Keyboard");
        computer.addPeripheral("Gaming Mouse (25,000 DPI)");
        computer.addPeripheral("27\" 4K 144Hz Monitor");
        computer.addPeripheral("7.1 Surround Headset");
    }

    @Override
    public Computer getResult() {
        Computer result = this.computer;
        this.computer = new Computer(); // reset for next build
        return result;
    }
}
