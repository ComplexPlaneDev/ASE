import java.util.Arrays;
import java.util.List;

/**
 * CSVDataProcessor  (ConcreteClass)
 *
 * Processes comma-separated files.
 * - Overrides validateRecords() to also reject rows with wrong column count.
 * - Enables the transform step to normalise whitespace.
 * - No connection setup needed (direct file read) → hooks stay as no-ops.
 */
public class CSVDataProcessor extends DataProcessor {

    private final int expectedColumns;

    public CSVDataProcessor(int expectedColumns) {
        this.expectedColumns = expectedColumns;
    }

    @Override
    protected String readRawData(String source) {
        // Simulate reading a CSV file
        return "id,name,email\n" +
               "1,Alice Mueller,alice@example.com\n" +
               "2,Bob Schmidt,bob@example.com\n" +
               "  \n" +   // blank row — will be filtered
               "3,Carol Braun,carol@example.com\n" +
               "4,INVALID_ROW_ONLY_TWO_COLS\n" +
               "5,David Klein,david@example.com";
    }

    @Override
    protected List<String> parseRecords(String rawData) {
        return Arrays.asList(rawData.split("\n"));
    }

    /**
     * Extended validation: call super to strip blanks, then additionally
     * reject rows that don't have exactly expectedColumns comma-separated fields.
     */
    @Override
    protected List<String> validateRecords(List<String> records) {
        return super.validateRecords(records).stream()
                .filter(r -> r.split(",", -1).length == expectedColumns)
                .toList();
    }

    /** Enable normalisation transform for CSV. */
    @Override
    protected boolean shouldTransform() { return true; }

    @Override
    protected List<String> transformRecords(List<String> records) {
        // Normalise: trim every field, upper-case the email
        return records.stream()
                .map(r -> {
                    String[] cols = r.split(",", -1);
                    for (int i = 0; i < cols.length; i++) cols[i] = cols[i].trim();
                    if (cols.length > 2) cols[2] = cols[2].toLowerCase();
                    return String.join(",", cols);
                })
                .toList();
    }

    @Override
    protected void writeResults(List<String> records) {
        System.out.println("  │ Writing CSV output:");
        records.forEach(r -> System.out.println("  │   " + r));
    }
}
