// Composite class representing a bundle of products , can contain both Products and other ProductBundles
// implements the Product component interface but also manages child components with a list


import java.util.ArrayList; // for managing child items as a list
import java.util.List;

public class ProductBundle implements InventoryItem {
    private final String name;
    private final List<InventoryItem> children = new ArrayList<>(); // list to hold child items

    public ProductBundle(String name) {
        this.name = name;
    }

    public void add(InventoryItem item) {
        children.add(item);
    }

    public void remove(InventoryItem item) {
        children.remove(item);
    }

    @Override
    public void displayInfo() {
        displayInfo("");
    }

    void display(String indent) {
        System.out.println(indent + "> " + name); 
        String childIndent = indent + "  "; // increase indent for children
        for (InventoryItem child : children) { // iterate through children
            if (child instanceof ProductBundle) {
                ((ProductBundle) child).display(childIndent); // recursive call for bundles
            } else if (child instanceof Product) {
                ((Product) child).display(childIndent); // recursive call for products
            } else {
                child.displayInfo();
            }
        }
    }

    @Override
    public double calculateTotalPrice() {
        return children.stream().mapToDouble(InventoryItem::calculateTotalPrice).sum(); // sum prices of all children (:: is a method reference)
    }

    @Override
    public boolean checkAvailability() {
        return children.stream().allMatch(InventoryItem::checkAvailability); // all children must be available only then return true
    }

    @Override
    public void updateStock(String sku, int newQuantity) {
        for (InventoryItem child : children) {
            child.updateStock(sku, newQuantity); // propagate stock update to all children
        }
    }
}
