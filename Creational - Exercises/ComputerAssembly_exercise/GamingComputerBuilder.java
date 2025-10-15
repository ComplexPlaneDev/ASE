public class GamingComputerBuilder implements IComputerBuilder {
    private Computer computer = new Computer();

    @Override
    public void buildMotherboard() {
        System.out.println("Building high-end motherboard for gaming computer.");
        computer.setMotherboard("ASUS ROG Strix Z790-E Gaming WiFi");
    }
    @Override
    public void buildCpu() {
        System.out.println("Building powerful CPU for gaming computer.");
        computer.setCpu("Intel Core i9-13900K");
    }
    @Override
    public void buildRam() {
        System.out.println("Building high-capacity RAM for gaming computer.");
        computer.setRam("32GB DDR5 6000MHz");
    }
    @Override
    public void buildStorage() {
        System.out.println("Building fast storage for gaming computer.");
        computer.setStorage("2TB NVMe SSD");
    }
    @Override
    public void buildGraphicsCard() {
        System.out.println("Building top-tier graphics card for gaming computer.");
        computer.setGraphicsCard("NVIDIA GeForce RTX 4090");
    }
    @Override
    public void buildPowerSupply() {
        System.out.println("Building robust power supply for gaming computer.");
        computer.setPowerSupply("850W 80+ Gold Certified");
    }
    @Override
    public void buildComputerCase() {
        System.out.println("Building stylish case for gaming computer.");
        computer.setComputerCase("Lian Li PC-O11 Dynamic");
    }
    @Override
    public void buildCoolingSystem() {
        System.out.println("Building advanced cooling system for gaming computer.");
        computer.setCoolingSystem("Corsair iCUE H150i Elite Capellix Liquid CPU Cooler");
    }
    @Override
    public Computer getComputer(){
        return this.computer;
    }

}
