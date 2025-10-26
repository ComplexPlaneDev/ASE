public class InventoryMain {
    public static void main(String[] args) {
        //Create Individual Products = Leaves
        Product penSet = new Product("Pen Set", "PEN001", 15.99, 50, "Stationery");
        Product stapler = new Product("Stapler", "STA001", 24.99, 25, "Stationery");
        Product notebook = new Product("Notebook", "NOT001", 8.99, 100, "Stationery");
        Product stickyNotes = new Product("Sticky Notes", "STI001", 3.99, 200, "Stationery");
        Product mouse = new Product("Wireless Mouse", "MOU001", 29.99, 30, "Electronics");
        Product usbCable = new Product("USB Cable", "USB001", 12.99, 75, "Electronics");
        Product powerBank = new Product("Power Bank", "POW001", 39.99, 0, "Electronics"); // Out of stock!

        //Create Composite Bundles
        ProductBundle deskAccessories = new ProductBundle("Desk Accessories");
        deskAccessories.addComponent(penSet);
        deskAccessories.addComponent(stapler);

        ProductBundle paperProducts = new ProductBundle("Paper Products");
        paperProducts.addComponent(notebook);
        paperProducts.addComponent(stickyNotes);

        ProductBundle officeSupplies = new ProductBundle("Office Supplies Bundle");
        officeSupplies.addComponent(deskAccessories);
        officeSupplies.addComponent(paperProducts);

        ProductBundle techEssentials = new ProductBundle("Tech Essentials");
        techEssentials.addComponent(usbCable);
        techEssentials.addComponent(powerBank);

        ProductBundle electronicsBundle = new ProductBundle("Electronics Bundle");
        electronicsBundle.addComponent(mouse);
        electronicsBundle.addComponent(techEssentials);

        //Create Root Inventory
        ProductBundle retailInventory = new ProductBundle("Retail Inventory");
        retailInventory.addComponent(officeSupplies);
        retailInventory.addComponent(electronicsBundle);

        //Perform Operations
        System.out.println("1. Displaying Full Inventory");
        retailInventory.displayInfo("");
        System.out.println("\n---------------------------------------\n");

        System.out.println("2. Calculating Total Inventory Value");
        System.out.printf("Total Inventory Value: $%.2f%n", retailInventory.calculateTotalPrice());
        System.out.println("\n---------------------------------------\n");

        System.out.println("3. Checking Availability");
        System.out.println("Are all Office Supplies available? " + officeSupplies.checkAvailability()); // Should be true
        System.out.println("Are all Electronics available? " + electronicsBundle.checkAvailability()); // Should be false (Power Bank is 0)
        System.out.println("Is the entire inventory available? " + retailInventory.checkAvailability()); // Should be false
        System.out.println("\n---------------------------------------\n");

        System.out.println("4. Updating Stock and Re-checking");
        // The client can update stock by calling the method on the top-level inventory.
        // The request will propagate down the tree to the correct product.
        retailInventory.updateStock("POW001", 15);
        System.out.println("\nAfter restocking Power Bank...");
        System.out.println("Are all Electronics available now? " + electronicsBundle.checkAvailability()); // Should now be true
        System.out.println("Is the entire inventory available now? " + retailInventory.checkAvailability()); // Should now be true
    }
}
