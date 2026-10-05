/**
 * Director: ITDirector
 *
 * The Director controls the ORDER and WHICH steps are executed.
 * It works with ANY builder through the ComputerBuilder interface —
 * it does not know whether it's building a gaming PC or an office PC.
 *
 * KEY INSIGHT: The Director encapsulates "construction recipes."
 * Different methods represent different build configurations.
 * The Director is OPTIONAL — clients can drive the builder directly —
 * but it keeps construction logic out of client code.
 */
public class ITDirector {

    private ComputerBuilder builder;

    public ITDirector(ComputerBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(ComputerBuilder builder) {
        this.builder = builder;
    }

    /**
     * Full build — assembles every component including peripherals.
     * Used for complete workstation orders.
     */
    public void buildFullComputer() {
        builder.buildCpu();
        builder.buildMotherboard();
        builder.buildRam();
        builder.buildGpu();
        builder.buildStorage();
        builder.buildCooling();
        builder.buildPowerSupply();
        builder.buildCase();
        builder.buildPeripherals();
    }

    /**
     * Minimal build — only essential components, no peripherals.
     * Used when the customer already has a monitor/keyboard/mouse.
     */
    public void buildBarebonesComputer() {
        builder.buildCpu();
        builder.buildMotherboard();
        builder.buildRam();
        builder.buildStorage();
        builder.buildCooling();
        builder.buildPowerSupply();
        builder.buildCase();
        // No GPU, no peripherals
    }
}
