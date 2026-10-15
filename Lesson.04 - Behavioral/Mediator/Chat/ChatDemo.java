/**
 * Demo: three users in one chat room.
 *
 * <p>Notice that {@code User} has no field for "other users": adding a fourth
 * user requires zero changes to the existing ones (Open/Closed Principle).
 */
public final class ChatDemo {

    public static void main(String[] args) {
        System.out.println("=== Chat room (broadcast mediator) ===");

        ChatRoom room = new ChatRoom("general");
        User alice = new User("Alice");
        User bob   = new User("Bob");
        User carol = new User("Carol");

        room.addUser(alice);
        room.addUser(bob);
        room.addUser(carol);

        System.out.println("\n-- Alice sends a message --");
        alice.send("Hello everyone!");

        System.out.println("\n-- Bob replies --");
        bob.send("Hi Alice, welcome to the room.");

        System.out.println("\n-- Carol joins the conversation --");
        carol.send("Nice to meet you both!");
    }

    private ChatDemo() { }
}
