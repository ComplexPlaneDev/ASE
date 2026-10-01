/** Concrete Product B. */
public class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("ConsoleLogger: " + message);
    }

    @Override
    public void close() {
        // nothing to release for the console
    }
}
