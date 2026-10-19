import java.util.ArrayList;
import java.util.List;

/**
 * JSONDataProcessor  (ConcreteClass)
 *
 * Processes newline-delimited JSON (NDJSON) records.
 * - Uses connection hooks to simulate opening/closing an HTTP stream.
 * - Skips the transform step (data is already canonical).
 * - Adds JSON-specific validation (checks for required keys).
 */
public class JSONDataProcessor extends DataProcessor {

    private final String apiEndpoint;

    public JSONDataProcessor(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    @Override
    protected void openConnection(String source) {
        System.out.printf("  │ Opening HTTP stream to: %s%n", apiEndpoint);
    }

    @Override
    protected void closeConnection() {
        System.out.println("  │ Closing HTTP stream");
    }

    @Override
    protected String readRawData(String source) {
        // Simulate fetching NDJSON from an API
        return "{\"id\":1,\"name\":\"Alice\",\"score\":92}\n" +
               "{\"id\":2,\"name\":\"Bob\",\"score\":87}\n" +
               "\n" +   // blank line
               "{\"id\":3,\"score\":75}\n" +  // missing "name" — invalid
               "{\"id\":4,\"name\":\"David\",\"score\":91}";
    }

    @Override
    protected List<String> parseRecords(String rawData) {
        return new ArrayList<>(List.of(rawData.split("\n")));
    }

    /**
     * JSON-specific validation: also requires "name" key to be present.
     */
    @Override
    protected List<String> validateRecords(List<String> records) {
        return super.validateRecords(records).stream()
                .filter(r -> r.contains("\"name\""))
                .toList();
    }

    // shouldTransform() → false (inherited) — no transform needed for JSON

    @Override
    protected List<String> transformRecords(List<String> records) {
        return records; // Not called — shouldTransform() is false
    }

    @Override
    protected void writeResults(List<String> records) {
        System.out.println("  │ Writing to JSON store:");
        records.forEach(r -> System.out.println("  │   → " + r));
    }
}
