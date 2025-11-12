import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * LibraryService manages book borrowing and returning operations.
 * This class demonstrates a real-world scenario where Fakes are useful for testing
 * interactions with external dependencies like databases, email systems, and inventory management.
 */
public class LibraryService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private final InventorySystem inventorySystem;

    public LibraryService(BookRepository bookRepository,
                         MemberRepository memberRepository,
                         EmailService emailService,
                         InventorySystem inventorySystem) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.emailService = emailService;
        this.inventorySystem = inventorySystem;
    }

    /**
     * Processes a book borrowing request.
     * Checks member eligibility, book availability, and updates records.
     */
    public BorrowResult borrowBook(String memberId, String isbn) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }

        try {
            // Check if member exists and is active
            Member member = memberRepository.findById(memberId);
            if (member == null) {
                return new BorrowResult(false, "Member not found", null);
            }

            if (!member.isActive()) {
                return new BorrowResult(false, "Member account is suspended", null);
            }

            // Check if member has reached borrowing limit
            int currentBorrows = bookRepository.countActiveBorrowsByMember(memberId);
            if (currentBorrows >= member.getBorrowLimit()) {
                return new BorrowResult(false, "Member has reached borrowing limit", null);
            }

            // Check if book exists and is available
            Book book = bookRepository.findByIsbn(isbn);
            if (book == null) {
                return new BorrowResult(false, "Book not found", null);
            }

            if (!inventorySystem.isBookAvailable(isbn)) {
                return new BorrowResult(false, "Book is currently not available", null);
            }

            // Create borrow record
            LocalDate dueDate = LocalDate.now().plusWeeks(2);
            BorrowRecord record = new BorrowRecord(memberId, isbn, LocalDate.now(), dueDate);

            // Update inventory and save record
            inventorySystem.reserveBook(isbn);
            bookRepository.saveBorrowRecord(record);

            // Send confirmation email
            emailService.sendBorrowConfirmation(member.getEmail(), book.getTitle(), dueDate);

            return new BorrowResult(true, "Book borrowed successfully", record);

        } catch (DatabaseException e) {
            return new BorrowResult(false, "Database error: " + e.getMessage(), null);
        } catch (InventoryException e) {
            return new BorrowResult(false, "Inventory error: " + e.getMessage(), null);
        }
    }

    /**
     * Processes a book return request.
     * Updates records, calculates fines if overdue, and sends notifications.
     */
    public ReturnResult returnBook(String memberId, String isbn) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }

        try {
            // Find the active borrow record
            BorrowRecord record = bookRepository.findActiveBorrowRecord(memberId, isbn);
            if (record == null) {
                return new ReturnResult(false, "No active borrow record found", 0.0, null);
            }

            // Calculate fine if overdue
            LocalDate today = LocalDate.now();
            double fine = 0.0;
            if (today.isAfter(record.getDueDate())) {
                long overdueDays = today.toEpochDay() - record.getDueDate().toEpochDay();
                fine = overdueDays * 0.50; // $0.50 per day
            }

            // Update records
            record.setReturnDate(today);
            record.setFineAmount(fine);
            bookRepository.updateBorrowRecord(record);
            inventorySystem.returnBook(isbn);

            // Get member and book details for notification
            Member member = memberRepository.findById(memberId);
            Book book = bookRepository.findByIsbn(isbn);

            // Send return confirmation
            if (fine > 0) {
                emailService.sendOverdueReturnNotification(member.getEmail(), book.getTitle(), fine);
            } else {
                emailService.sendReturnConfirmation(member.getEmail(), book.getTitle());
            }

            return new ReturnResult(true, "Book returned successfully", fine, record);

        } catch (DatabaseException e) {
            return new ReturnResult(false, "Database error: " + e.getMessage(), 0.0, null);
        } catch (InventoryException e) {
            return new ReturnResult(false, "Inventory error: " + e.getMessage(), 0.0, null);
        }
    }

    /**
     * Renews a book borrowing period if eligible.
     */
    public RenewalResult renewBook(String memberId, String isbn) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }

        try {
            // Find active borrow record
            BorrowRecord record = bookRepository.findActiveBorrowRecord(memberId, isbn);
            if (record == null) {
                return new RenewalResult(false, "No active borrow record found", null);
            }

            // Check if renewal is allowed (not overdue, not renewed before)
            if (LocalDate.now().isAfter(record.getDueDate())) {
                return new RenewalResult(false, "Cannot renew overdue book", null);
            }

            if (record.isRenewed()) {
                return new RenewalResult(false, "Book has already been renewed", null);
            }

            // Check if book is requested by others
            if (inventorySystem.hasWaitingList(isbn)) {
                return new RenewalResult(false, "Book is requested by other members", null);
            }

            // Extend due date and mark as renewed
            LocalDate newDueDate = record.getDueDate().plusWeeks(2);
            record.setDueDate(newDueDate);
            record.setRenewed(true);
            bookRepository.updateBorrowRecord(record);

            // Send renewal confirmation
            Member member = memberRepository.findById(memberId);
            Book book = bookRepository.findByIsbn(isbn);
            emailService.sendRenewalConfirmation(member.getEmail(), book.getTitle(), newDueDate);

            return new RenewalResult(true, "Book renewed successfully", record);

        } catch (DatabaseException e) {
            return new RenewalResult(false, "Database error: " + e.getMessage(), null);
        } catch (InventoryException e) {
            return new RenewalResult(false, "Inventory error: " + e.getMessage(), null);
        }
    }
}

// Supporting interfaces and classes

interface BookRepository {
    Book findByIsbn(String isbn) throws DatabaseException;
    BorrowRecord findActiveBorrowRecord(String memberId, String isbn) throws DatabaseException;
    int countActiveBorrowsByMember(String memberId) throws DatabaseException;
    void saveBorrowRecord(BorrowRecord record) throws DatabaseException;
    void updateBorrowRecord(BorrowRecord record) throws DatabaseException;
}

interface MemberRepository {
    Member findById(String memberId) throws DatabaseException;
}

interface EmailService {
    void sendBorrowConfirmation(String email, String bookTitle, LocalDate dueDate);
    void sendReturnConfirmation(String email, String bookTitle);
    void sendOverdueReturnNotification(String email, String bookTitle, double fine);
    void sendRenewalConfirmation(String email, String bookTitle, LocalDate newDueDate);
}

interface InventorySystem {
    boolean isBookAvailable(String isbn) throws InventoryException;
    void reserveBook(String isbn) throws InventoryException;
    void returnBook(String isbn) throws InventoryException;
    boolean hasWaitingList(String isbn) throws InventoryException;
}

class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final String category;

    public Book(String isbn, String title, String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
}

class Member {
    private final String id;
    private final String name;
    private final String email;
    private final boolean active;
    private final int borrowLimit;

    public Member(String id, String name, String email, boolean active, int borrowLimit) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
        this.borrowLimit = borrowLimit;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isActive() { return active; }
    public int getBorrowLimit() { return borrowLimit; }
}

class BorrowRecord {
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

class BorrowResult {
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

class ReturnResult {
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

class RenewalResult {
    private final boolean successful;
    private final String message;
    private final BorrowRecord record;

    public RenewalResult(boolean successful, String message, BorrowRecord record) {
        this.successful = successful;
        this.message = message;
        this.record = record;
    }

    public boolean isSuccessful() { return successful; }
    public String getMessage() { return message; }
    public BorrowRecord getRecord() { return record; }
}

class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}

class InventoryException extends Exception {
    public InventoryException(String message) {
        super(message);
    }
}
