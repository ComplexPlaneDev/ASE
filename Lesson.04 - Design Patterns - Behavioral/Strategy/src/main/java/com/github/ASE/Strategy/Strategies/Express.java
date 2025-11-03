package com.github.ASE.Strategy.Strategies;

import com.github.ASE.Strategy.PricingStrategy;
import com.github.ASE.Strategy.Shipment;

public class Express implements PricingStrategy {
    private final PricingStrategy baseStrategy;

    public Express(PricingStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    public double calculateCost(Shipment shipment) {
        double baseCost = baseStrategy.calculateCost(shipment);
        double expressFee = baseCost * 0.3;
        return baseCost + expressFee;
    }
}
