public abstract class EmailService {
    protected EmailProtocol protocol;
    protected EmailProvider provider;

    public EmailService(EmailProtocol protocol, EmailProvider provider) {
        this.protocol = protocol;
        this.provider = provider;
    }

    public abstract void execute(String message);
}
