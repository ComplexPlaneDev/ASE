package builder.exe01;

//builder for the budget build that implements ComputerBuilder interface
public class BudgetBuildBuilder implements ComputerBuilder
{
    private Computer computer;

    public BudgetBuildBuilder()
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
        computer.setCPU("Entry-level CPU");
    }

    @Override
    public void addRAM()
    {
        computer.setRamGB(4);
    }

    @Override
    public void addStorage()
    {
        computer.setStorage("HDD");
    }

    @Override
    public void addGraphicsCard()
    {
        computer.setGraphicsCard(null);
    }

    @Override
    public void addPowerSupply()
    {
        computer.setPowerSupply("Basic PSU");
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