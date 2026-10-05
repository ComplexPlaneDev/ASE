import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Registry (Cache)
 *
 * Stores pre-configured prototype instances under named keys.
 * Clients request clones by name instead of building objects from scratch.
 *
 * USE CASE: When object creation is expensive (e.g., loaded from DB,
 * parsed from file, or requires complex initialization), you create
 * the prototype once, store it in the registry, and clone it on demand.
 *
 * This is the "Prototype Manager" from the GoF book.
 */
public class ShapeRegistry {

    private final Map<String, Shape> prototypes = new HashMap<>();

    /**
     * Register a prototype under a given key.
     * The registry stores a clone — so modifications to the original
     * after registration don't affect the stored prototype.
     */
    public void register(String key, Shape prototype) {
        prototypes.put(key, prototype.clone());
    }

    /**
     * Returns a CLONE of the registered prototype.
     * Never returns the stored instance itself.
     */
    public Shape get(String key) {
        Shape prototype = prototypes.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException("No prototype registered for key: " + key);
        }
        return prototype.clone();
    }

    /**
     * Check if a prototype is registered under the given key.
     */
    public boolean contains(String key) {
        return prototypes.containsKey(key);
    }

    /**
     * List all registered prototype keys.
     */
    public void printRegistry() {
        System.out.println("┌─── Shape Registry ─────────────────────────────┐");
        for (Map.Entry<String, Shape> entry : prototypes.entrySet()) {
            System.out.printf("│ %-12s → %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println("└─────────────────────────────────────────────────┘");
    }
}
