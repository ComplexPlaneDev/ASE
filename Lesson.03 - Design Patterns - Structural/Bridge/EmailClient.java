public class EmailClient extends EmailService {

    public EmailClient(EmailProtocol protocol, EmailProvider provider) {
        super(protocol, provider);
    }

    @Override
    public void execute(String message) {
        provider.authenticate();
        protocol.initialize();
        protocol.sendEmail(message);
        provider.processEmail(message);
        protocol.shutdown();
        provider.logout();
    }
}
