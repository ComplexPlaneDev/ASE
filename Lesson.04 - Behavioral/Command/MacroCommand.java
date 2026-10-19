import java.util.List;

/**
 * MacroCommand — <b>concrete command</b> (composite of commands).
 *
 * <p>A macro is a command that groups several other commands and executes
 * them as a single unit. This is the classic combination of Command with
 * the Composite pattern: because a macro itself is a {@link Command}, it
 * can be queued, invoked or combined with other macros without any special
 * handling.</p>
 */
public class MacroCommand implements Command {

    private final List<Command> commands;

    public MacroCommand(List<Command> commands) {
        this.commands = List.copyOf(commands);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    /**
     * Undoes the contained commands in reverse order.
     * Only valid if every contained command supports undo.
     */
    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
}
