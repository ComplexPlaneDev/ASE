/**
 * Example 1 - Logging framework (non-exclusive variant).
 *
 * <p>A DEBUG record is written only by the first logger, while an ERROR
 * record is written by ALL four loggers: one request can be processed by
 * several handlers of the chain.
 */
public final class LoggingDemo {

    private LoggingDemo() { }

    public static void main(String[] args) {
        System.out.println("=== Example 1: logging chain (multiple handlers may process) ===");

        Logger chain = new DebugLogger();
        chain.setNext(new InfoLogger())
             .setNext(new WarnLogger())
             .setNext(new ErrorLogger());

        chain.log(LogLevel.DEBUG, "cache miss for key=42");
        System.out.println();
        chain.log(LogLevel.INFO,  "user login: alice@example.com");
        System.out.println();
        chain.log(LogLevel.WARN,  "disk usage above 80%");
        System.out.println();
        chain.log(LogLevel.ERROR, "database connection lost");
    }
}
