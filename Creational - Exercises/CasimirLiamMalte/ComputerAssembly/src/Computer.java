import java.util.ArrayList;
import java.util.List;

public class Computer {
    private String motherboard;
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String psu;
    private String pccase;
    private String coolingSystem;

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public void setPsu(String psu) {
        this.psu = psu;
    }

    public void setPccase(String pccase) {
        this.pccase = pccase;
    }

    public void setCoolingSystem(String coolingSystem) {
        this.coolingSystem = coolingSystem;
    }

    public List<String> giveMeSpecs() {
        final List<String> out = new ArrayList<>();
        out.add("Motherboard: " + this.motherboard);
        out.add("Cpu: " + this.cpu);
        out.add("Ram: " + this.ram);
        out.add("Storage: " + this.storage);
        out.add("Gpu: " + this.gpu);
        out.add("Psu: " + this.psu);
        out.add("Case: " + this.pccase);
        out.add("CoolingSystem: " + this.coolingSystem);
        return out;
    }
}
