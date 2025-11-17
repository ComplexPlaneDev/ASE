import java.time.LocalDate;

public interface EmailService {
    void sendBorrowConfirmation(String email, String bookTitle, LocalDate dueDate);
    void sendReturnConfirmation(String email, String bookTitle);
    void sendOverdueReturnNotification(String email, String bookTitle, double fine);
    void sendRenewalConfirmation(String email, String bookTitle, LocalDate newDueDate);
}
