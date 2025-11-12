import java.time.LocalDate;

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
