package builder.exe01;

//defines a list of methods, any builder that inherits from it, must implement
public interface ComputerBuilder 
{
    void addMotherboard();
    void addCPU();
    void addRAM();
    void addStorage();
    void addGraphicsCard();
    void addPowerSupply();
    void addComputerCase();
    void addCoolingSystem();
    Computer build(); //returns a Computer object 
}