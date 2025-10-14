public class ConsoleLoggerCreator extends LoggerCreator {
    @Override
    public Logger createLogger() {
        return new ConsoleLogger();
    }
}