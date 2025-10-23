public class SMTP implements EmailProtocol {
    @Override
    public void initialize() {
        System.out.println("SMTP: Initializing mail transfer protocol");
    }

    @Override
    public void sendEmail(String message) {
        System.out.println("SMTP: Sending email: " + message);
    }

    @Override
    public void shutdown() {
        System.out.println("SMTP: Shutting down protocol");
    }
}
