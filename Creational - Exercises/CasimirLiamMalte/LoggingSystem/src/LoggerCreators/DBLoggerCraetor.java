public class DatabaseLoggerCreator extends LoggerCreator {
    @Override
    public Logger createLogger() {
        return new DatabaseLogger();
    }
}