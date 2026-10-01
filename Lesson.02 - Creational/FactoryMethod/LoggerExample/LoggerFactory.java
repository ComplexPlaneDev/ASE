/**
 * Abstract Creator.
 * A single abstract Factory Method; every concrete factory
 * overrides it to return a different Logger implementation.
 */
public abstract class LoggerFactory {

    public abstract Logger createLogger();
}
