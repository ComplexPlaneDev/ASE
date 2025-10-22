// Leaf node representing a single product
public class Product implements InventoryItem {
    private final String name;
    private final String sku;
    private final double price;
    private int quantity;
    private final String category;

    public Product(String name, String sku, double price, int quantity, String category) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    @Override
    public void displayInfo() {
        displayInfo("");
    }

    // Helper method to display with indentation
    void display(String indent) {
        System.out.println(indent + "- " + name + " (SKU: " + sku + ", $" + String.format("%.2f", price) // formatted price in 2 decimal places
                + ", " + quantity + " units, " + category + ")");
    }

    @Override
    public double calculateTotalPrice() {
        return price;
    }

    @Override
    public boolean checkAvailability() {
        return quantity > 0;
    }

    @Override
    public void updateStock(String targetSku, int newQuantity) {
        if (this.sku.equalsIgnoreCase(targetSku)) {
            this.quantity = newQuantity;
        }
    }
}
