/** Concrete Creator C. */
public class DatabaseLoggerFactory extends LoggerFactory {

    @Override
    public Logger createLogger() {
        return new DatabaseLogger("jdbc:postgresql://localhost/appdb");
    }
}
