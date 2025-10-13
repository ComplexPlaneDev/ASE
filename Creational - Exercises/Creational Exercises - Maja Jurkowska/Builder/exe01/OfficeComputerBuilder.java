package builder.exe01;

//builder for the office computer that implements ComputerBuilder interface
public class OfficeComputerBuilder implements ComputerBuilder
{
    private Computer computer;

    public OfficeComputerBuilder()
    {
        this.computer = new Computer();
    }

    @Override //implementing and overriding ComputerBuilder methods in order to set values specific to this builder
    public void addMotherboard() 
    {
        computer.setMotherboard("Motherboard");
    }

    @Override
    public void addCPU()
    {
        computer.setCPU("Basic CPU");
    }

    @Override
    public void addRAM()
    {
        computer.setRamGB(8);
    }

    @Override
    public void addStorage()
    {
        computer.setStorage("SSD");
    }

    @Override
    public void addGraphicsCard()
    {
        computer.setGraphicsCard(null);
    }

    @Override
    public void addPowerSupply()
    {
        computer.setPowerSupply("PSU");
    }

    @Override
    public void addComputerCase()
    {
        computer.setComputerCase("Case");
    }

    @Override
    public void addCoolingSystem()
    {
        computer.setCoolingSystem(null);
    }

    @Override
    public Computer build() 
    {
        return this.computer;
    }
}