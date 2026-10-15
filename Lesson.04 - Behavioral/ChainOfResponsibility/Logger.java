/**
 * Abstract logger - the "Handler" role in a logging chain.
 *
 * <p>NOTE the difference from the core {@code Handler}: there is NO early
 * return after writing. Every handler whose threshold matches will process
 * the record, and the chain ALWAYS continues. This is the non-exclusive
 * ("multiple handlers may process one request") variant of the pattern.
 */
public abstract class Logger {

    protected final String name;
    protected Logger next;

    protected Logger(String name) {
        this.name = name;
    }

    public Logger setNext(Logger next) {
        this.next = next;
        return next;
    }

    /** Entry point: write if my threshold matches, then ALWAYS continue. */
    public void log(LogLevel level, String message) {
        if (level.ordinal() == minLevel().ordinal()) {
            write(level, message);
        } else if (next != null) {
            next.log(level, message); // no early return: fan-out along the chain
        }
    }

    /** The minimum severity this logger is interested in. */
    protected abstract LogLevel minLevel();

    private void write(LogLevel level, String message) {
        System.out.printf("  %-5s %s%n", level, message);
    }
}
