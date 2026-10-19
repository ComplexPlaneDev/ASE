import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * TaskQueue — <b>invoker</b>.
 *
 * <p>A queue of commands. Commands can be created and enqueued at any time
 * (even long before they are executed) and the queue is processed later,
 * in order. This demonstrates the decoupling in <em>time</em>: the moment
 * a request is made and the moment it is carried out do not have to
 * coincide.</p>
 *
 * <p>In a real system {@link #processAll()} would be called from a worker
 * thread; here it is called synchronously to keep the demo deterministic.</p>
 */
public class TaskQueue {

    private final Queue<Command> queue = new ConcurrentLinkedQueue<>();

    /** Adds a command to the end of the queue. */
    public void enqueue(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("command must not be null");
        }
        queue.add(command);
    }

    /** Executes all queued commands, in FIFO order. */
    public void processAll() {
        Command command;
        while ((command = queue.poll()) != null) {
            command.execute();
        }
    }
}
