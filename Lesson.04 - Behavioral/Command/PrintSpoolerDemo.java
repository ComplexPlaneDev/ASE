import java.util.List;

/**
 * PrintSpoolerDemo — <b>client</b>.
 *
 * <p>Demonstrates queuing and batching of commands. The client builds
 * print jobs (single commands) and a batch (a macro command), enqueues
 * them, and the spooler processes everything in order.</p>
 */
public class PrintSpoolerDemo {

    public static void main(String[] args) {
        Printer laserjet = new Printer("LaserJet-400");
        TaskQueue spooler = new TaskQueue();

        // A single print job.
        spooler.enqueue(new PrintJobCommand(laserjet, "Quarterly report.pdf"));

        // A batch of jobs wrapped in a macro command.
        MacroCommand invoiceBatch = new MacroCommand(List.of(
                new PrintJobCommand(laserjet, "Invoice #1024.pdf"),
                new PrintJobCommand(laserjet, "Invoice #1025.pdf")
        ));
        spooler.enqueue(invoiceBatch);

        // The queue is processed later — the requests were made earlier.
        System.out.println("Processing the print spooler:");
        spooler.processAll();
    }
}
