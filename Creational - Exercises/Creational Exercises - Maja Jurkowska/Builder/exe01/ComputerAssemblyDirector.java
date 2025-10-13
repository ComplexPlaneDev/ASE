package builder.exe01;

//director in the builder pattern, the director dictates the order in which the product is assembled 
public class ComputerAssemblyDirector 
{
    //this method receives a ComputerBuilder object, then calls a sequence of build steps on that builder 
    public Computer buildOfficeComputer(ComputerBuilder builder) 
    {
        builder.addMotherboard();
        builder.addCPU();
        builder.addRAM();
        builder.addStorage();
        builder.addGraphicsCard();
        builder.addPowerSupply();
        builder.addComputerCase();
        builder.addCoolingSystem();
        return builder.build(); //returns the finished product
    }

    public Computer buildBudgetBuild(ComputerBuilder builder) 
    {
        builder.addMotherboard();
        builder.addCPU();
        builder.addRAM();
        builder.addStorage();
        builder.addGraphicsCard();
        builder.addPowerSupply();
        builder.addComputerCase();
        builder.addCoolingSystem();
        return builder.build();
    }
}
