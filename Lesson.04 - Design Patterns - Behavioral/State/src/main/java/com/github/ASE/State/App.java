package com.github.ASE.State;

public class App {

    public static void main(String[] args) {
        DeliveryPackage delivery = new DeliveryPackage();

        System.out.println("\n--- Initial State: Processing ---");
        delivery.track(); // Allowed
        delivery.ship(); // Moves to Shipped
        delivery.cancel(); // Not allowed

        System.out.println("\n--- State: Shipped ---");
        delivery.deliver(); // Moves to Delivered

        System.out.println("\n--- State: Delivered ---");
        delivery.returnToSender(); // Moves to Returned

        System.out.println("\n--- State: Returned ---");
        delivery.track(); // Allowed
        delivery.ship(); // Not allowed
        delivery.deliver(); // Not allowed
        delivery.returnToSender(); // Already returned

        System.out.println("\n--- Attempting Invalid Actions ---");
        delivery.cancel(); // Not allowed in Delivered state
    }
}
