import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XMLDataProcessor  (ConcreteClass)
 *
 * Processes XML documents — extracts {@code <record>} elements.
 * - Uses a connection hook to simulate acquiring a SAX/DOM parser resource.
 * - Enables transform to strip XML tags from the final output.
 * - Keeps the default blank-record validation from the superclass.
 */
public class XMLDataProcessor extends DataProcessor {

    private static final Pattern RECORD_PATTERN =
            Pattern.compile("<record>(.*?)</record>", Pattern.DOTALL);

    @Override
    protected void openConnection(String source) {
        System.out.println("  │ Initialising XML parser");
    }

    @Override
    protected void closeConnection() {
        System.out.println("  │ Releasing XML parser resources");
    }

    @Override
    protected String readRawData(String source) {
        return "<?xml version=\"1.0\"?>\n<data>\n" +
               "  <record><id>1</id><name>Alice</name><dept>Engineering</dept></record>\n" +
               "  <record></record>\n" +   // empty → filtered by validateRecords
               "  <record><id>2</id><name>Bob</name><dept>Finance</dept></record>\n" +
               "  <record><id>3</id><name>Carol</name><dept>Marketing</dept></record>\n" +
               "</data>";
    }

    /** Extract text content of every {@code <record>} element. */
    @Override
    protected List<String> parseRecords(String rawData) {
        List<String> records = new ArrayList<>();
        Matcher m = RECORD_PATTERN.matcher(rawData);
        while (m.find()) records.add(m.group(1).trim());
        return records;
    }

    /** Enable the transform step to clean up XML tags. */
    @Override
    protected boolean shouldTransform() { return true; }

    /** Strip all remaining XML tags, collapse whitespace. */
    @Override
    protected List<String> transformRecords(List<String> records) {
        return records.stream()
                .map(r -> r.replaceAll("<[^>]+>", " ")
                            .replaceAll("\\s+", " ")
                            .trim())
                .toList();
    }

    @Override
    protected void writeResults(List<String> records) {
        System.out.println("  │ Inserting into relational table:");
        records.forEach(r -> System.out.println("  │   INSERT → [" + r + "]"));
    }
}
