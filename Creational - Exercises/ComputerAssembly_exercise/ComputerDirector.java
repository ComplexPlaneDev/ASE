public class ComputerDirector {
    public void constructComputer(IComputerBuilder builder) {
        builder.buildMotherboard();
        builder.buildCpu();
        builder.buildRam();
        builder.buildStorage();
        builder.buildGraphicsCard();
        builder.buildPowerSupply();
        builder.buildComputerCase();
        builder.buildCoolingSystem();
    }
}