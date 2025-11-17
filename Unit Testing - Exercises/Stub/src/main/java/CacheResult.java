/**
 * Result of cache operations
 */
public class CacheResult {
    private boolean success;
    private String value;
    private String message;

    public CacheResult(boolean success, String value, String message) {
        this.success = success;
        this.value = value;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getValue() { return value; }
    public String getMessage() { return message; }
}
