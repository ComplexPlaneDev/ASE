/**
 * Interface for metrics collection
 */
public interface MetricsCollector {
    void recordCacheAccess(String key);
    void recordCacheHit(String key, long duration);
    void recordCacheMiss(String key, long duration);
    void recordCacheWrite(String key);
    void recordSuccessfulWrite(String key, long duration);
    void recordFailedWrite(String key, long duration);
    void recordError(String message);
    void recordCleanupStart();
    void recordCleanupComplete(int removedCount);
    void recordKeyExpired(String key);
    void recordPreloadStart(int entryCount);
    void recordPreloadComplete(int successCount, int failureCount);
    CacheMetrics getMetrics();
}
