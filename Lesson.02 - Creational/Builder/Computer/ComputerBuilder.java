/**
 * Builder Interface: ComputerBuilder
 *
 * Declares the construction steps that all concrete builders must implement.
 * Each step builds one part of the product.
 *
 * KEY INSIGHT: The builder interface defines WHAT can be built,
 * but not HOW or in what ORDER. The Director controls the order.
 */
public interface ComputerBuilder {

    void buildCpu();
    void buildMotherboard();
    void buildRam();
    void buildGpu();
    void buildStorage();
    void buildCooling();
    void buildPowerSupply();
    void buildCase();
    void buildPeripherals();

    /**
     * Returns the assembled product and resets the builder
     * so it can produce another instance.
     */
    Computer getResult();
}
