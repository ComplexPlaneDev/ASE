import java.util.ArrayList;
import java.util.List;

public class ProductBundle implements InventoryComponent {
    private String name;
    private List<InventoryComponent> children = new ArrayList<>();

    public ProductBundle(String name) {
        this.name = name;
    }

    public void addComponent(InventoryComponent component) {
        children.add(component);
    }

    public void removeComponent(InventoryComponent component) {
        children.remove(component);
    }

    @Override
    public void displayInfo(String indent) {
        System.out.println(indent + "├── " + this.name);
        for (InventoryComponent component : children) {
            component.displayInfo(indent + "│   ");
        }
    }

    @Override
    public double calculateTotalPrice() {
        // Use a stream to sum the prices of all children
        return children.stream()
                       .mapToDouble(InventoryComponent::calculateTotalPrice)
                       .sum();
    }

    @Override
    public boolean checkAvailability() {
        // Use a stream to check if all children are available
        return children.stream()
                       .allMatch(InventoryComponent::checkAvailability);
    }

    @Override
    public void updateStock(String sku, int newQuantity) {
        // Pass the update request down to all children
        for (InventoryComponent component : children) {
            component.updateStock(sku, newQuantity);
        }
    }
}
