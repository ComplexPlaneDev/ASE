/**
 * Cache statistics
 */
public class CacheStats {
    private int totalKeys;
    private long hitCount;
    private long missCount;
    private long errorCount;
    private long timestamp;

    public CacheStats(int totalKeys, long hitCount, long missCount, long errorCount, long timestamp) {
        this.totalKeys = totalKeys;
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.errorCount = errorCount;
        this.timestamp = timestamp;
    }

    public int getTotalKeys() { return totalKeys; }
    public long getHitCount() { return hitCount; }
    public long getMissCount() { return missCount; }
    public long getErrorCount() { return errorCount; }
    public long getTimestamp() { return timestamp; }

    public double getHitRatio() {
        long total = hitCount + missCount;
        return total > 0 ? (double) hitCount / total : 0.0;
    }
}
