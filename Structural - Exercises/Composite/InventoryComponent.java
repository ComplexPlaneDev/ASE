public interface InventoryComponent {
    
    void displayInfo(String indent);
    double calculateTotalPrice();
    boolean checkAvailability();
    void updateStock(String sku, int newQuantity);
    
}
