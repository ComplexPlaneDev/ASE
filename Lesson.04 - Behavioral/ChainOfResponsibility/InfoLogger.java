/** Writes INFO and above. */
public class InfoLogger extends Logger {
    public InfoLogger() { super("INFO "); }
    @Override protected LogLevel minLevel() { return LogLevel.INFO; }
}
