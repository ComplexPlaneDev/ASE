public interface InventorySystem {
    boolean isBookAvailable(String isbn) throws InventoryException;
    void reserveBook(String isbn) throws InventoryException;
    void returnBook(String isbn) throws InventoryException;
    boolean hasWaitingList(String isbn) throws InventoryException;
}
