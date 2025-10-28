
public class Main {
    public static void main(String[] args) {
        ComputerBuilder officeComputerBuilder = new OfficeComputerBuilder();
        ComputerAssambler.assamblyPc(officeComputerBuilder);
        System.out.println(String.join("\n", officeComputerBuilder.getComputer().giveMeSpecs()) + "\n");

        ComputerBuilder gamingComputerBuilder = new GamingComputerBuilder();
        ComputerAssambler.assamblyPc(gamingComputerBuilder);
        System.out.println(String.join("\n", gamingComputerBuilder.getComputer().giveMeSpecs()) + "\n");

        ComputerBuilder workstationComputerBuilder = new WorkstationBuilder();
        ComputerAssambler.assamblyPc(workstationComputerBuilder);
        System.out.println(String.join("\n", workstationComputerBuilder.getComputer().giveMeSpecs()) + "\n");

        ComputerBuilder budgetBuildBuilder = new BudgetBuildBuilder();
        ComputerAssambler.assamblyPc(budgetBuildBuilder);
        System.out.println(String.join("\n", budgetBuildBuilder.getComputer().giveMeSpecs()) + "\n");
    }

}