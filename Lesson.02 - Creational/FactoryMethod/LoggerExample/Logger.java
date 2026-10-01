/**
 * Product interface.
 * Client code depends on this abstraction, never on a concrete logger.
 */
public interface Logger {

    void log(String message);

    void close();
}
