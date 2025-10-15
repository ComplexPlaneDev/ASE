public class OfficeComputerBuilder implements IComputerBuilder {
    private Computer computer = new Computer();

    @Override
    public void buildMotherboard() {
        System.out.println("Installing Basic Motherboard (B660 Chipset)...");
        computer.setMotherboard("Basic Motherboard (B660 Chipset)");
    }

    @Override
    public void buildCpu() {
        System.out.println("Installing Economy CPU (i5)...");
        computer.setCpu("Economy CPU (i5)");
    }

    @Override
    public void buildRam() {
        System.out.println("Installing 16GB DDR4 RAM...");
        computer.setRam("16GB DDR4 RAM");
    }

    @Override
    public void buildStorage() {
        System.out.println("Installing 512GB SATA SSD...");
        computer.setStorage("512GB SATA SSD");
    }

    @Override
    public void buildGraphicsCard() {
        System.out.println("Skipping dedicated graphics card (using integrated)...");
        computer.setGraphicsCard("Integrated Graphics");
    }

    @Override
    public void buildPowerSupply() {
        System.out.println("Installing 500W Bronze PSU...");
        computer.setPowerSupply("500W Bronze PSU");
    }

    @Override
    public void buildComputerCase() {
        System.out.println("Assembling in a Standard Micro-ATX Case...");
        computer.setComputerCase("Standard Micro-ATX Case");
    }

    @Override
    public void buildCoolingSystem() {
        System.out.println("Skipping advanced cooling (using stock cooler)...");
        computer.setCoolingSystem("Stock CPU Cooler");
    }

    @Override
    public Computer getComputer(){
        return this.computer;
    }
}
