package builder.exe01; //grouping all exe01 files into one package

//class of the computer product, the final object Builder patter builds 
public class Computer 
{
    //private components, data that makes up "Computer"
    private String motherboard;
    private String cpu;
    private int ramGB;
    private String storage;
    private String graphicsCard;
    private String powerSupply;
    private String computerCase;
    private String coolingSystem;

    //setter methods for setting values of the components
    public void setMotherboard(String motherboard) { this.motherboard = motherboard; }
    public void setCPU(String cpu) { this.cpu = cpu; }
    public void setRamGB(int ramGB) { this.ramGB = ramGB; }
    public void setStorage(String storage) { this.storage = storage; }
    public void setGraphicsCard(String graphicsCard) { this.graphicsCard = graphicsCard; }
    public void setPowerSupply(String powerSupply) { this.powerSupply = powerSupply; }
    public void setComputerCase(String computerCase) { this.computerCase = computerCase; }
    public void setCoolingSystem(String coolingSystem) { this.coolingSystem = coolingSystem; }

    //method for printing out the computer's configuration (all set data)
    public void showConfig() 
    {
        System.out.println("Computer configuration:");
        System.out.println("Motherboard:" + motherboard);
        System.out.println("CPU:" + cpu);
        System.out.println("RAM (GB):" + ramGB);
        System.out.println("Storage:" + storage);
        System.out.println("Graphics Card:" + (graphicsCard != null ? graphicsCard : "None/Integrated")); //if graphicsCard is not null then use the given value otherwise default to "None/Integrated"
        System.out.println("Power Supply:" + powerSupply);
        System.out.println("Case:" + computerCase);
        System.out.println("Cooling:" + (coolingSystem != null ? coolingSystem : "Standard"));
    }
}