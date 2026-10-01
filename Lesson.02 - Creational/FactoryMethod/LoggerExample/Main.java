/**
 * Client code.
 * The application logic is identical for every logging backend:
 * it only knows the Logger interface and the LoggerFactory abstraction.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Example 2: Logging backends ===");

        run(new FileLoggerFactory());
        run(new ConsoleLoggerFactory());
        run(new DatabaseLoggerFactory());
    }

    private static void run(LoggerFactory factory) {
        Logger logger = factory.createLogger();
        try {
            logger.log("Application started");
            logger.log("Processing batch #42");
        } finally {
            logger.close();
        }
        System.out.println();
    }
}
