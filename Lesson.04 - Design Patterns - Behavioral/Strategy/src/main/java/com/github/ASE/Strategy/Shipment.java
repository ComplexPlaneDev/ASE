package com.github.ASE.Strategy;

import com.github.ASE.Strategy.Strategies.Standard;

public class Shipment {
    private PricingStrategy pricingStrategy;
    private final double weight;
    private final double distance;
    private final int itemCount;
    private final boolean isInternational;

    public Shipment(double weight, double distance, int itemCount, boolean isInternational) {
        this.weight = weight;
        this.distance = distance;
        this.itemCount = itemCount;
        this.isInternational = isInternational;
        this.pricingStrategy = new Standard(); // Default
    }

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    public double calculateTotalCost() {
        return pricingStrategy.calculateCost(this);
    }

    // Getters
    public double getWeight() {
        return weight;
    }

    public double getDistance() {
        return distance;
    }

    public int getItemCount() {
        return itemCount;
    }

    public boolean isInternational() {
        return isInternational;
    }
}
