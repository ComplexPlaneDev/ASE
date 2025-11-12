import java.util.List;

/**
 * Interface for storage operations
 */
public interface StorageService {
    String retrieve(String key);
    boolean store(String key, String value, long expirationTime);
    boolean remove(String key);
    List<String> getExpiredKeys(long currentTime);
    int getTotalKeyCount();
}
