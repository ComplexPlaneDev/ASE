import java.util.List;

/**
 * DataProcessor  (AbstractClass)
 *
 * Defines the skeleton for an ETL (Extract-Transform-Load) pipeline.
 * All file-format-specific processors share the same pipeline structure;
 * only the parsing and output steps differ.
 *
 * Template method: process(source)
 *
 * Algorithm skeleton:
 *   1. openConnection(source)   ← hook   (optional setup)
 *   2. readRawData(source)      ← abstract
 *   3. parseRecords(rawData)    ← abstract
 *   4. validateRecords(records) ← concrete with default
 *   5. if shouldTransform()     ← hook gate
 *        → transformRecords()   ← abstract
 *   6. writeResults(records)    ← abstract
 *   7. closeConnection()        ← hook   (optional teardown)
 *
 * ── Design note ───────────────────────────────────────────────────────────
 * validateRecords() has a sensible default (reject blank/null records)
 * but subclasses may override to add format-specific validation.
 * This is a concrete step that is *overridable* — different from a hook
 * in that it provides real default logic rather than a no-op.
 * ─────────────────────────────────────────────────────────────────────────
 */
public abstract class DataProcessor {
    protected int foo = 3;

    /** THE TEMPLATE METHOD — ETL pipeline skeleton. */
    public final void process(String source) {
        System.out.printf("%n  ╔═ Processing [%s] via %s ═╗%n",
                source, getClass().getSimpleName());

        openConnection(source);                       // hook

        String rawData = readRawData(source);         // abstract
        System.out.printf("  │ Read %,d bytes from source%n", rawData.length());

        List<String> records = parseRecords(rawData); // abstract
        System.out.printf("  │ Parsed %d records%n", records.size());

        records = validateRecords(records);           // concrete (overridable)
        System.out.printf("  │ %d records passed validation%n", records.size());

        if (shouldTransform()) {                      // hook gate
            records = transformRecords(records);      // abstract
            System.out.printf("  │ %d records after transform%n", records.size());
        }

        writeResults(records);                        // abstract

        closeConnection();                            // hook
        System.out.println("  ╚═ Pipeline complete ═╝");
    }

    // ── Abstract steps ────────────────────────────────────────────────────

    protected abstract String       readRawData(String source);
    protected abstract List<String> parseRecords(String rawData);
    protected abstract List<String> transformRecords(List<String> records);
    protected abstract void         writeResults(List<String> records);

    // ── Concrete step with default ────────────────────────────────────────

    /**
     * Removes null/blank records.
     * Subclasses may call super.validateRecords() first, then add
     * format-specific checks (e.g. column count for CSV).
     */
    protected List<String> validateRecords(List<String> records) {
        return records.stream()
                .filter(r -> r != null && !r.isBlank())
                .toList();
    }

    // ── Hooks ─────────────────────────────────────────────────────────────

    protected void openConnection(String source) { /* no-op by default */ }
    protected void closeConnection()             { /* no-op by default */ }

    /** Return true to enable the transform step. Default: false. */
    protected boolean shouldTransform() { return false; }
}
