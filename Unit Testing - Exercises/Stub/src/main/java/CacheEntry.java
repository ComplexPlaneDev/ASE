/**
 * Cache entry for preloading
 */
public class CacheEntry {
    private String key;
    private String value;

    public CacheEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public String getValue() { return value; }
}
