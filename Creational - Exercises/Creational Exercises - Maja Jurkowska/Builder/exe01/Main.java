package builder.exe01;

//the main method where program starts
public class Main 
{
    public static void main(String[] arguments)
    {
        //making a director object in order to use it 
        ComputerAssemblyDirector director = new ComputerAssemblyDirector();
        
        ComputerBuilder officeBuilder = new OfficeComputerBuilder();
        //making a Computer object called officeComputer using the director and the builder 
        Computer officeComputer = director.buildOfficeComputer(officeBuilder);
        officeComputer.showConfig();

        ComputerBuilder budgetBuilder = new BudgetBuildBuilder();
        Computer budgetBuild = director.buildBudgetBuild(budgetBuilder);
        budgetBuild.showConfig();
    }
}