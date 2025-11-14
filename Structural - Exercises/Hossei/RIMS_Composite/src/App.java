public class App {
    public static void main(String[] args) {
        // Products
        Product penSet      = new Product("Pen Set",      "PEN001", 15.99, 50,  "Stationery");
        Product stapler     = new Product("Stapler",      "STA001", 24.99, 25,  "Stationery");
        Product notebook    = new Product("Notebook",     "NOT001",  8.99, 100, "Stationery");
        Product stickyNotes = new Product("Sticky Notes", "STI001",  3.99, 200, "Stationery");

        Product mouse       = new Product("Wireless Mouse","MOU001", 29.99, 30, "Electronics");
        Product usbCable    = new Product("USB Cable",     "USB001", 12.99, 75, "Electronics");
        Product powerBank   = new Product("Power Bank",    "POW001", 39.99, 15, "Electronics");

        // Product Bundles
        ProductBundle deskAccessories = new ProductBundle("Desk Accessories");
        deskAccessories.add(penSet);
        deskAccessories.add(stapler);

        ProductBundle paperProducts = new ProductBundle("Paper Products");
        paperProducts.add(notebook);
        paperProducts.add(stickyNotes);

        ProductBundle officeSupplies = new ProductBundle("Office Supplies Bundle");
        officeSupplies.add(deskAccessories);
        officeSupplies.add(paperProducts);

        ProductBundle techEssentials = new ProductBundle("Tech Essentials");
        techEssentials.add(usbCable);
        techEssentials.add(powerBank);

        ProductBundle electronics = new ProductBundle("Electronics Bundle");
        electronics.add(mouse);
        electronics.add(techEssentials);

        // Root inventory
        ProductBundle inventory = new ProductBundle("Retail Inventory");
        inventory.add(officeSupplies);
        inventory.add(electronics);

        inventory.displayInfo();

        System.out.println("\nTOTAL PRICE");
        System.out.printf("$%.2f%n", inventory.calculateTotalPrice());

        System.out.println("\nAVAILABILITY (all products must be in stock)");
        System.out.println(inventory.checkAvailability()); // true initially

        System.out.println("\nUPDATE STOCK: set POW001 (Power Bank) to 0");
        inventory.updateStock("POW001", 0);
        inventory.displayInfo();
        System.out.println("Availability after update: " + inventory.checkAvailability()); // now false
    }
}
