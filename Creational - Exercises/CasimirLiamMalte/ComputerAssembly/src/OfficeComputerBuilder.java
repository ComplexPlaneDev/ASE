public class OfficeComputerBuilder implements ComputerBuilder {
    final Computer computer;

    public OfficeComputerBuilder() {
        this.computer = new Computer();
    }

    @Override
    public void addMotherboard() {
        this.computer.setMotherboard("Motherboard");
    }

    @Override
    public void addCPU() {
        this.computer.setCpu("Basic CPU");
    }

    @Override
    public void addRAM() {
        this.computer.setRam("8GB Ram");
    }

    @Override
    public void addGPU() {
        this.computer.setGpu("Integrated Graphics");
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
        this.computer.setCoolingSystem("");
    }

    @Override
    public Computer getComputer() {
        return this.computer;
    }
}
