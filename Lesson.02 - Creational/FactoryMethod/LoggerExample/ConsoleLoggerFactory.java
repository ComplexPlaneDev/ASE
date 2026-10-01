/** Concrete Creator B. */
public class ConsoleLoggerFactory extends LoggerFactory {

    @Override
    public Logger createLogger() {
        return new ConsoleLogger();
    }
}
