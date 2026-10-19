/**
 * PrintJobCommand — <b>concrete command</b>.
 *
 * <p>This command implements the {@link Command} interface <em>directly</em>
 * (instead of extending {@code AbstractCommand}), because it does not need
 * undo: a printed page cannot be un-printed. It therefore relies on the
 * default {@code undo()} implementation, which throws if anyone tries to
 * undo it.</p>
 */
public class PrintJobCommand implements Command {

    private final Printer printer;
    private final String document;

    public PrintJobCommand(Printer printer, String document) {
        this.printer = printer;
        this.document = document;
    }

    @Override
    public void execute() {
        printer.print(document);
    }
}
