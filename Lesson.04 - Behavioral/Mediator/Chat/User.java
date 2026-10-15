/**
 * Concrete COLLEAGUE: a user of the chat room.
 *
 * <p>A user knows only its mediator (injected via {@link #setMediator});
 * it has no reference to any other user.
 */
public final class User extends Colleague {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /** Sends a message through the mediator (the room decides who receives it). */
    public void send(String message) {
        notify(message);
    }

    @Override
    public void onEvent(Object event) {
        System.out.println("  [" + name + " received] " + event);
    }
}
