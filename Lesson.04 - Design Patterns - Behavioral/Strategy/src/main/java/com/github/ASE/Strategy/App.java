package com.github.ASE.Strategy;

import com.github.ASE.Strategy.Strategies.Bulk;
import com.github.ASE.Strategy.Strategies.Express;
import com.github.ASE.Strategy.Strategies.International;
import com.github.ASE.Strategy.Strategies.Standard;

public class App {

    public static void main(String[] args) {
        // Create a shipment
        Shipment shipment1 = new Shipment(5, 100, 1, false);
        Shipment shipment2 = new Shipment(20, 1500, 15, true);
        Shipment shipment3 = new Shipment(50, 200, 5, false);

        // Apply different pricing strategies
        System.out.println("Shipment 1: Standard Pricing");
        shipment1.setPricingStrategy(new Standard());
        System.out.println("Total Cost: $" + shipment1.calculateTotalCost());

        System.out.println("\nShipment 2: Bulk + International Pricing");
        shipment2.setPricingStrategy(new International(new Bulk(new Standard())));
        System.out.println("Total Cost: $" + shipment2.calculateTotalCost());

        System.out.println("\nShipment 3: Express Pricing");
        shipment3.setPricingStrategy(new Express(new Standard()));
        System.out.println("Total Cost: $" + shipment3.calculateTotalCost());
    }
}
