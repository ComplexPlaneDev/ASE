import java.util.StringJoiner;

public class Computer {
private String motherboard;
private String cpu;
private String ram;
private String storage;
private String graphicsCard;
private String powerSupply;
private String computerCase;
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
public void setGraphicsCard(String graphicsCard) {
    this.graphicsCard = graphicsCard;
}
public void setPowerSupply(String powerSupply) {
    this.powerSupply = powerSupply;
}
public void setComputerCase(String computerCase) {
    this.computerCase = computerCase;
}
public void setCoolingSystem(String coolingSystem) {
    this.coolingSystem = coolingSystem;
}
@Override
public String toString() {
    StringJoiner sj = new StringJoiner("\n - ", "Computer Configuration:\n - ", "");
    if (motherboard != null) sj.add("Motherboard: " + motherboard);
    if (cpu != null) sj.add("CPU: " + cpu);
    if (ram != null) sj.add("RAM: " + ram);
    if (storage != null) sj.add("Storage: " + storage);
    if (graphicsCard != null) sj.add("Graphics Card: " + graphicsCard);
    if (powerSupply != null) sj.add("Power Supply: " + powerSupply);
    if (computerCase != null) sj.add("Case: " + computerCase);
    if (coolingSystem != null) sj.add("Cooling System: " + coolingSystem);
    return sj.toString();
    }
}
