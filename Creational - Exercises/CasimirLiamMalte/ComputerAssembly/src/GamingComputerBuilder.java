public class GamingComputerBuilder implements ComputerBuilder {
    final Computer computer;

    public GamingComputerBuilder() {
        this.computer = new Computer();
    }

    @Override
    public void addMotherboard() {
        this.computer.setMotherboard("Motherboard");
    }

    @Override
    public void addCPU() {
        this.computer.setCpu("Gaming CPU");
    }

    @Override
    public void addRAM() {
        this.computer.setRam("16GB Ram");
    }

    @Override
    public void addGPU() {
        this.computer.setGpu("High-end Graphics");
    }

    @Override
    public void addPSU() {
        this.computer.setPsu("PSU");
    }

    @Override
    public void addStorage() {
        this.computer.setStorage("SSD");
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
