public interface IComputerBuilder {
    void buildMotherboard();
    void buildCpu();
    void buildRam();
    void buildStorage();
    void buildGraphicsCard();
    void buildPowerSupply();
    void buildComputerCase();
    void buildCoolingSystem();
    Computer getComputer();
}
