package com.github.ASE.Strategy.Strategies;

import com.github.ASE.Strategy.PricingStrategy;
import com.github.ASE.Strategy.Shipment;

public class Bulk implements PricingStrategy {
    private final PricingStrategy baseStrategy;

    public Bulk(PricingStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    @Override
    public double calculateCost(Shipment shipment) {
        if (shipment.getItemCount() > 10) {
            double baseCost = baseStrategy.calculateCost(shipment);
            return baseCost * 0.8; // 20% discount for bulk
        }
        return baseStrategy.calculateCost(shipment);
    }
}
