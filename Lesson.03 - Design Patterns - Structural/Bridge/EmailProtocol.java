
public interface EmailProtocol {

    void initialize();

    void sendEmail(String message);

    void shutdown();
}
