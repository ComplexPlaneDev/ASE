public interface EmailService {
    void sendWelcomeEmail(String email, String name);

    void sendPasswordResetEmail(String email);
}
