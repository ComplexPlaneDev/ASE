/** Writes ERROR only (terminal handler of the chain). */
public class ErrorLogger extends Logger {
    public ErrorLogger() { super("ERROR"); }
    @Override protected LogLevel minLevel() { return LogLevel.ERROR; }
}
