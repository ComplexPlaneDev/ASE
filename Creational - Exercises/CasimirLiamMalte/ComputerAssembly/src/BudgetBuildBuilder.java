public class BudgetBuildBuilder implements ComputerBuilder {
    final Computer computer;

    public BudgetBuildBuilder() {
        this.computer = new Computer();
    }

    @Override
    public void addMotherboard() {
        this.computer.setMotherboard("Motherboard");
    }

    @Override
    public void addCPU() {
        this.computer.setCpu("Entry-level CPU");
    }

    @Override
    public void addRAM() {
        this.computer.setRam("4GB Ram");
    }

    @Override
    public void addGPU() {
        this.computer.setGpu("Integrated Graphics");
    }

    @Override
    public void addPSU() {
        this.computer.setPsu("Basic PSU");
    }

    @Override
    public void addStorage() {
        this.computer.setStorage("HDD");
    }

    @Override
    public void addPcCase() {
        this.computer.setPccase("Case");
    }

    @Override
    public void addCoolingSystem() {
        this.computer.setCoolingSystem("");
    }

    @Override
    public Computer getComputer() {
        return this.computer;
    }
}
