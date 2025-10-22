public class Product implements InventoryComponent {

 private String name;
    private String sku;
    private double price;
    private int quantity;
    private String category;

    
    public Product(String name, String sku, double price, int quantity, String category) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    @Override
    public void displayInfo(String indent) {
        System.out.printf("%s- %s (SKU: %s, Price: $%.2f, Stock: %d, Category: %s)%n",
                indent, name, sku, price, quantity, category);
    }

    @Override
    public double calculateTotalPrice() {
        return this.price;
    }

    @Override
    public boolean checkAvailability() {
        return this.quantity > 0;
    }

    @Override
    public void updateStock(String sku, int newQuantity) {
        if (this.sku.equals(sku)) {
            System.out.printf(">> Updating stock for %s (SKU: %s) from %d to %d%n",
                    this.name, this.sku, this.quantity, newQuantity);
            this.quantity = newQuantity;
        }
    }
    
}
