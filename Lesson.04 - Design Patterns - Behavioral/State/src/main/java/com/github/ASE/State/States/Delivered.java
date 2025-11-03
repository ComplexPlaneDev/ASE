package com.github.ASE.State.States;

import com.github.ASE.State.DeliveryPackage;
import com.github.ASE.State.DeliveryState;

public class Delivered implements DeliveryState {
    private final DeliveryPackage deliveryPackage;

    public Delivered(DeliveryPackage deliveryPackage) {
        this.deliveryPackage = deliveryPackage;
    }

    @Override
    public void track() {
        System.out.println("Tracking: Package delivered.");
    }

    @Override
    public void cancel() {
        System.out.println("Package already delivered.");
    }

    @Override
    public void ship() {
        System.out.println("Package already shipped.");
    }

    @Override
    public void deliver() {
        System.out.println("Package already delivered.");
    }

    @Override
    public void returnToSender() {
        System.out.println("Return requested.");
        deliveryPackage.setState(deliveryPackage.getReturnedState());
    }
}
