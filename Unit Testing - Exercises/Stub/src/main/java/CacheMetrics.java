/**
 * Cache metrics data
 */
public class CacheMetrics {
    private long hitCount;
    private long missCount;
    private long errorCount;

    public CacheMetrics(long hitCount, long missCount, long errorCount) {
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.errorCount = errorCount;
    }

    public long getHitCount() { return hitCount; }
    public long getMissCount() { return missCount; }
    public long getErrorCount() { return errorCount; }
}
