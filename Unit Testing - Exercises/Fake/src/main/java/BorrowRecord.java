import java.time.LocalDate;

public class BorrowRecord {
    private final String memberId;
    private final String isbn;
    private final LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fineAmount;
    private boolean renewed;

    public BorrowRecord(String memberId, String isbn, LocalDate borrowDate, LocalDate dueDate) {
        this.memberId = memberId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.renewed = false;
    }

    public String getMemberId() { return memberId; }
    public String getIsbn() { return isbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getFineAmount() { return fineAmount; }
    public boolean isRenewed() { return renewed; }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }
    public void setRenewed(boolean renewed) { this.renewed = renewed; }
}
