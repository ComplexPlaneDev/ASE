public interface ComputerBuilder {
    void addMotherboard();
    void addCPU();
    void addRAM();
    void addStorage();
    void addGPU();
    void addPSU();
    void addPcCase();
    void addCoolingSystem();

    Computer getComputer();
}
