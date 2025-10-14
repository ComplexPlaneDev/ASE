public class ComputerAssambler {
    public static void assamblyPc(ComputerBuilder builder) {
        builder.addMotherboard();
        builder.addCPU();
        builder.addRAM();
        builder.addStorage();
        builder.addGPU();
        builder.addPSU();
        builder.addPcCase();
        builder.addCoolingSystem();
    }
}
