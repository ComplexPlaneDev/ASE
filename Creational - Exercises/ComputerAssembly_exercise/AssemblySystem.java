public class AssemblySystem {
    public static void main(String[] args) {
        ComputerDirector director = new ComputerDirector();
        System.out.println("---Building a Gaming Computer ---");

        // Building a Gaming PC
        IComputerBuilder gamingBuilder = new GamingComputerBuilder();
        director.constructComputer(gamingBuilder);
        Computer gamingPC = gamingBuilder.getComputer();
        System.out.println(gamingPC);

        System.out.println("\n-----------------------\n");

        // Building an Office PC
        IComputerBuilder officeBuilder = new OfficeComputerBuilder();
        director.constructComputer(officeBuilder);
        Computer officePC = officeBuilder.getComputer();
        System.out.println(officePC);
    }
}