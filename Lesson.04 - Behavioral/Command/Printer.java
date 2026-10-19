/**
 * Printer — <b>concrete receiver</b>.
 *
 * <p>The printer performs the actual printing service. It is completely
 * decoupled from the spooler: it only ever receives a call to
 * {@link #print(String)}.</p>
 */
public class Printer implements Receiver {

    private final String name;

    public Printer(String name) {
        this.name = name;
    }

    public void print(String document) {
        System.out.println("  [" + name + "] printing: " + document);
    }
}
