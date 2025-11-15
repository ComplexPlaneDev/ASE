// No package declaration needed for this exercise

import java.util.List;

/**
 * CacheManager handles caching operations with external storage and metrics.
 * This class demonstrates dependencies that need to be stubbed during testing.
 */
public class CacheManager {

    private StorageService storageService;
    private MetricsCollector metricsCollector;
    private TimeProvider timeProvider;

    public CacheManager(StorageService storageService,
                       MetricsCollector metricsCollector,
                       TimeProvider timeProvider) {
        this.storageService = storageService;
        this.metricsCollector = metricsCollector;
        this.timeProvider = timeProvider;
    }

    /**
     * Retrieves a value from cache or storage
     * @param key The cache key
     * @return CacheResult containing the value and metadata
     */
    public CacheResult get(String key) {
        long startTime = timeProvider.getCurrentTime();

        try {
            // Validate input
            if (key == null || key.trim().isEmpty()) {
                metricsCollector.recordError("Invalid cache key");
                return new CacheResult(false, null, "Invalid key");
            }

            metricsCollector.recordCacheAccess(key);

            // Try to get from storage
            String value = storageService.retrieve(key);

            long duration = timeProvider.getCurrentTime() - startTime;

            if (value != null) {
                metricsCollector.recordCacheHit(key, duration);
                return new CacheResult(true, value, "Cache hit");
            } else {
                metricsCollector.recordCacheMiss(key, duration);
                return new CacheResult(false, null, "Cache miss");
            }

        } catch (Exception e) {
            long duration = timeProvider.getCurrentTime() - startTime;
            metricsCollector.recordError("Exception during get: " + e.getMessage());
            return new CacheResult(false, null, "Error: " + e.getMessage());
        }
    }

    /**
     * Stores a value in the cache
     * @param key The cache key
     * @param value The value to store
     * @param ttlSeconds Time to live in seconds
     * @return true if stored successfully
     */
    public boolean put(String key, String value, int ttlSeconds) {
        long startTime = timeProvider.getCurrentTime();

        try {
            // Validate inputs
            if (key == null || key.trim().isEmpty()) {
                metricsCollector.recordError("Invalid cache key for put operation");
                return false;
            }

            if (value == null) {
                metricsCollector.recordError("Cannot store null value");
                return false;
            }

            if (ttlSeconds <= 0) {
                metricsCollector.recordError("Invalid TTL: " + ttlSeconds);
                return false;
            }

            metricsCollector.recordCacheWrite(key);

            // Calculate expiration time
            long expirationTime = timeProvider.getCurrentTime() + (ttlSeconds * 1000);

            // Store in external storage
            boolean success = storageService.store(key, value, expirationTime);

            long duration = timeProvider.getCurrentTime() - startTime;

            if (success) {
                metricsCollector.recordSuccessfulWrite(key, duration);
                return true;
            } else {
                metricsCollector.recordFailedWrite(key, duration);
                return false;
            }

        } catch (Exception e) {
            long duration = timeProvider.getCurrentTime() - startTime;
            metricsCollector.recordError("Exception during put: " + e.getMessage());
            return false;
        }
    }

    /**
     * Removes expired entries from the cache
     * @return number of entries removed
     */
    public int cleanupExpired() {
        long currentTime = timeProvider.getCurrentTime();

        try {
            metricsCollector.recordCleanupStart();

            List<String> expiredKeys = storageService.getExpiredKeys(currentTime);

            if (expiredKeys == null || expiredKeys.isEmpty()) {
                metricsCollector.recordCleanupComplete(0);
                return 0;
            }

            int removedCount = 0;
            for (String key : expiredKeys) {
                boolean removed = storageService.remove(key);
                if (removed) {
                    removedCount++;
                    metricsCollector.recordKeyExpired(key);
                }
            }

            metricsCollector.recordCleanupComplete(removedCount);
            return removedCount;

        } catch (Exception e) {
            metricsCollector.recordError("Exception during cleanup: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Gets cache statistics
     * @return CacheStats object with current statistics
     */
    public CacheStats getStats() {
        try {
            int totalKeys = storageService.getTotalKeyCount();
            long currentTime = timeProvider.getCurrentTime();

            CacheMetrics metrics = metricsCollector.getMetrics();

            return new CacheStats(
                totalKeys,
                metrics.getHitCount(),
                metrics.getMissCount(),
                metrics.getErrorCount(),
                currentTime
            );

        } catch (Exception e) {
            metricsCollector.recordError("Exception getting stats: " + e.getMessage());
            return new CacheStats(0, 0, 0, 1, timeProvider.getCurrentTime());
        }
    }

    /**
     * Preloads cache with multiple key-value pairs
     * @param entries List of cache entries to preload
     * @param defaultTtl Default TTL for all entries
     * @return number of successfully loaded entries
     */
    public int preloadCache(List<CacheEntry> entries, int defaultTtl) {
        if (entries == null || entries.isEmpty()) {
            metricsCollector.recordError("No entries provided for preload");
            return 0;
        }

        if (defaultTtl <= 0) {
            metricsCollector.recordError("Invalid default TTL for preload: " + defaultTtl);
            return 0;
        }

        metricsCollector.recordPreloadStart(entries.size());

        int successCount = 0;
        for (CacheEntry entry : entries) {
            if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                boolean success = put(entry.getKey(), entry.getValue(), defaultTtl);
                if (success) {
                    successCount++;
                }
            }
        }

        metricsCollector.recordPreloadComplete(successCount, entries.size() - successCount);
        return successCount;
    }
}

