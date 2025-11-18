public interface BookRepository {
    Book findByIsbn(String isbn) throws DatabaseException;
    BorrowRecord findActiveBorrowRecord(String memberId, String isbn) throws DatabaseException;
    int countActiveBorrowsByMember(String memberId) throws DatabaseException;
    void saveBorrowRecord(BorrowRecord record) throws DatabaseException;
    void updateBorrowRecord(BorrowRecord record) throws DatabaseException;
}
