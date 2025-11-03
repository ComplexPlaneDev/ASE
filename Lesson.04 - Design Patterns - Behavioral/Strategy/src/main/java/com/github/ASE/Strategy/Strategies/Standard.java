package com.github.ASE.Strategy.Strategies;

import com.github.ASE.Strategy.PricingStrategy;
import com.github.ASE.Strategy.Shipment;

public class Standard implements PricingStrategy {
    @Override
    public double calculateCost(Shipment shipment) {
        double baseRate = 10.0;
        double weightFactor = shipment.getWeight() * 0.5;
        double distanceFactor = shipment.getDistance() * 0.1;
        return baseRate + weightFactor + distanceFactor;
    }
}
