// Component represents for both Product and ProductBundle

public interface InventoryItem {
    void displayInfo();
    double calculateTotalPrice(); 
    boolean checkAvailability();
    void updateStock(String sku, int newQuantity);
}
