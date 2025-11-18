public class BorrowResult {
    private final boolean successful;
    private final String message;
    private final BorrowRecord record;

    public BorrowResult(boolean successful, String message, BorrowRecord record) {
        this.successful = successful;
        this.message = message;
        this.record = record;
    }

    public boolean isSuccessful() { return successful; }
    public String getMessage() { return message; }
    public BorrowRecord getRecord() { return record; }
}
