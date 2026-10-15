/** Writes WARN and above. */
public class WarnLogger extends Logger {
    public WarnLogger() { super("WARN "); }
    @Override protected LogLevel minLevel() { return LogLevel.WARN; }
}
