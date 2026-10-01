/** Concrete Creator A. */
public class FileLoggerFactory extends LoggerFactory {

    @Override
    public Logger createLogger() {
        return new FileLogger("app.log");
    }
}
