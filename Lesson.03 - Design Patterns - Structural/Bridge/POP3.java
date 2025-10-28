public class POP3 implements EmailProtocol {
    @Override
    public void initialize() {
        System.out.println("POP3: Initializing post office protocol");
    }

    @Override
    public void sendEmail(String message) {
        System.out.println("POP3: Sending email: " + message);
    }

    @Override
    public void shutdown() {
        System.out.println("POP3: Shutting down protocol");
    }
}
