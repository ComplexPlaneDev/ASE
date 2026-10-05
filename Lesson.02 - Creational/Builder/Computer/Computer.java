import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Product: Computer
 *
 * A complex object with many parts. The Builder constructs it step by step.
 * Note: the product does NOT know about builders — it's just a data holder.
 */
public class Computer {

    private String cpu;
    private String motherboard;
    private int ramGB;
    private String gpu;
    private String storage;
    private String coolingSystem;
    private String powerSupply;
    private String caseType;
    private List<String> peripherals;

    public Computer() {
        this.peripherals = new ArrayList<>();
    }

    // --- Setters (package-private, used only by builders) ---

    void setCpu(String cpu) { this.cpu = cpu; }
    void setMotherboard(String motherboard) { this.motherboard = motherboard; }
    void setRamGB(int ramGB) { this.ramGB = ramGB; }
    void setGpu(String gpu) { this.gpu = gpu; }
    void setStorage(String storage) { this.storage = storage; }
    void setCoolingSystem(String coolingSystem) { this.coolingSystem = coolingSystem; }
    void setPowerSupply(String powerSupply) { this.powerSupply = powerSupply; }
    void setCaseType(String caseType) { this.caseType = caseType; }
    void addPeripheral(String peripheral) { this.peripherals.add(peripheral); }

    // --- Getters ---

    public String getCpu() { return cpu; }
    public String getMotherboard() { return motherboard; }
    public int getRamGB() { return ramGB; }
    public String getGpu() { return gpu; }
    public String getStorage() { return storage; }
    public String getCoolingSystem() { return coolingSystem; }
    public String getPowerSupply() { return powerSupply; }
    public String getCaseType() { return caseType; }
    public List<String> getPeripherals() { return Collections.unmodifiableList(peripherals); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("┌─── Computer Configuration ───────────────────┐\n");
        sb.append(String.format("│ CPU:          %-30s │%n", cpu));
        sb.append(String.format("│ Motherboard:  %-30s │%n", motherboard));
        sb.append(String.format("│ RAM:          %-30s │%n", ramGB + " GB"));
        sb.append(String.format("│ GPU:          %-30s │%n", gpu != null ? gpu : "Integrated"));
        sb.append(String.format("│ Storage:      %-30s │%n", storage));
        sb.append(String.format("│ Cooling:      %-30s │%n", coolingSystem));
        sb.append(String.format("│ PSU:          %-30s │%n", powerSupply));
        sb.append(String.format("│ Case:         %-30s │%n", caseType));
        if (!peripherals.isEmpty()) {
            sb.append("│ Peripherals:                                  │\n");
            for (String p : peripherals) {
                sb.append(String.format("│   • %-40s │%n", p));
            }
        }
        sb.append("└───────────────────────────────────────────────┘");
        return sb.toString();
    }
}
