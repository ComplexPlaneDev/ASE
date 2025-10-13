public class WorkstationBuilder implements ComputerBuilder {
    final Computer computer;

    public WorkstationBuilder() {
        this.computer = new Computer();
    }

    @Override
    public void addMotherboard() {
        this.computer.setMotherboard("Motherboard");
    }

    @Override
    public void addCPU() {
        this.computer.setCpu("Professional CPU");
    }

    @Override
    public void addRAM() {
        this.computer.setRam("32GB Ram");
    }

    @Override
    public void addGPU() {
        this.computer.setGpu("Professional Graphics");
    }

    @Override
    public void addPSU() {
        this.computer.setPsu("High-wattage PSU");
    }

    @Override
    public void addStorage() {
        this.computer.setStorage("Multiple Storage");
    }

    @Override
    public void addPcCase() {
        this.computer.setPccase("Case");
    }

    @Override
    public void addCoolingSystem() {
        this.computer.setCoolingSystem("Advanced Cooling");
    }

    @Override
    public Computer getComputer() {
        return this.computer;
    }
}
