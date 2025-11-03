package com.github.ASE.Strategy.Strategies;

import com.github.ASE.Strategy.PricingStrategy;
import com.github.ASE.Strategy.Shipment;

public class International implements PricingStrategy {
    private final PricingStrategy baseStrategy;

    public International(PricingStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    @Override
    public double calculateCost(Shipment shipment) {
        double baseCost = baseStrategy.calculateCost(shipment);
        double customsFee = 20.0;
        double handlingFee = 15.0;
        return baseCost + customsFee + handlingFee;
    }
}
