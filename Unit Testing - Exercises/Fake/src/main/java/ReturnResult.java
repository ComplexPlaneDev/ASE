public class ReturnResult {
    private final boolean successful;
    private final String message;
    private final double fine;
    private final BorrowRecord record;

    public ReturnResult(boolean successful, String message, double fine, BorrowRecord record) {
        this.successful = successful;
        this.message = message;
        this.fine = fine;
        this.record = record;
    }

    public boolean isSuccessful() { return successful; }
    public String getMessage() { return message; }
    public double getFine() { return fine; }
    public BorrowRecord getRecord() { return record; }
}
