package com.github.ASE.State.States;

import com.github.ASE.State.DeliveryPackage;
import com.github.ASE.State.DeliveryState;

public class Shipped implements DeliveryState {
    private final DeliveryPackage deliveryPackage;

    public Shipped(DeliveryPackage deliveryPackage) {
        this.deliveryPackage = deliveryPackage;
    }

    @Override
    public void track() {
        System.out.println("Tracking: Package is in transit.");
    }

    @Override
    public void cancel() {
        System.out.println("Cannot cancel after shipment.");
    }

    @Override
    public void ship() {
        System.out.println("Package already shipped.");
    }

    @Override
    public void deliver() {
        System.out.println("Package delivered.");
        deliveryPackage.setState(deliveryPackage.getDeliveredState());
    }

    @Override
    public void returnToSender() {
        System.out.println("Cannot return before delivery.");
    }
}
