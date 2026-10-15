import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Concrete MEDIATOR: a chat room.
 *
 * <p>Its coordination policy is a simple <em>broadcast</em>: every message
 * raised by one user is delivered to all the other users, prefixed with a
 * timestamp and the sender's name. Users never reference each other.
 */
public final class ChatRoom extends AbstractMediator {

    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String roomName;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
    }

    /** Registers a user in the room and wires it to this mediator. */
    public void addUser(User user) {
        addColleague(user);
        user.setMediator(this);
    }

    @Override
    public void createEvent(Colleague colleague, Object event) {
        User sender = (User) colleague;
        String line = "[" + STAMP.format(LocalDateTime.now()) + "] "
                + sender.getName() + ": " + event;
        broadcast(sender, line);   // helper inherited from AbstractMediator
    }

    @Override
    public String toString() {
        return "ChatRoom<" + roomName + ">";
    }
}
