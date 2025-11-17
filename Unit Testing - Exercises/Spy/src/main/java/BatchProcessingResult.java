import java.util.List;

public class BatchProcessingResult {
    private final List<ProcessingResult> results;
    private final int successCount;
    private final int failureCount;

    public BatchProcessingResult(List<ProcessingResult> results, int successCount, int failureCount) {
        this.results = results;
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public List<ProcessingResult> getResults() { return results; }
    public int getSuccessCount() { return successCount; }
    public int getFailureCount() { return failureCount; }
}
