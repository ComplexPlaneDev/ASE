public class IMAP implements EmailProtocol {
    @Override
    public void initialize() {
        System.out.println("IMAP: Initializing message access protocol");
    }

    @Override
    public void sendEmail(String message) {
        System.out.println("IMAP: Sending email: " + message);
    }

    @Override
    public void shutdown() {
        System.out.println("IMAP: Shutting down protocol");
    }
}
