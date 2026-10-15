/** Writes records of every severity (threshold: DEBUG). */
public class DebugLogger extends Logger {
    public DebugLogger() { super("DEBUG"); }
    @Override protected LogLevel minLevel() { return LogLevel.DEBUG; }
}
