/**
 * Client / Demo
 *
 * Shows three usage modes:
 *  1. Director + Builder (classic GoF)
 *  2. Director with a different builder (same recipe, different product)
 *  3. Builder without Director (client drives steps manually)
 */
public class BuilderDemo {

    public static void main(String[] args) {
        System.out.println("========= Builder Pattern Demo: PC Assembly =========\n");

        // ─── 1. Director builds a full Gaming PC ───────────────────────
        System.out.println(">>> 1. Director builds a FULL Gaming PC\n");

        GamingPCBuilder gamingBuilder = new GamingPCBuilder();
        ITDirector director = new ITDirector(gamingBuilder);

        director.buildFullComputer();
        Computer gamingPC = gamingBuilder.getResult();
        System.out.println(gamingPC);

        // ─── 2. Same Director, different builder → Office PC ───────────
        System.out.println("\n>>> 2. Director builds a FULL Office PC\n");

        OfficePCBuilder officeBuilder = new OfficePCBuilder();
        director.setBuilder(officeBuilder);

        director.buildFullComputer();
        Computer officePC = officeBuilder.getResult();
        System.out.println(officePC);

        // ─── 3. Director builds a barebones machine ────────────────────
        System.out.println("\n>>> 3. Director builds a BAREBONES Gaming PC (no GPU, no peripherals)\n");

        director.setBuilder(gamingBuilder);
        director.buildBarebonesComputer();
        Computer barebones = gamingBuilder.getResult();
        System.out.println(barebones);

        // ─── 4. Client drives the builder directly (no Director) ───────
        System.out.println("\n>>> 4. Client drives builder directly (custom config)\n");

        GamingPCBuilder customBuilder = new GamingPCBuilder();
        customBuilder.buildCpu();
        customBuilder.buildMotherboard();
        customBuilder.buildRam();
        customBuilder.buildGpu();
        customBuilder.buildStorage();
        customBuilder.buildCooling();
        customBuilder.buildPowerSupply();
        customBuilder.buildCase();
        // Skip peripherals — customer has their own
        Computer customPC = customBuilder.getResult();
        System.out.println(customPC);
    }
}
