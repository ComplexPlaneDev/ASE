package com.github.ASE.State.States;

import com.github.ASE.State.DeliveryPackage;
import com.github.ASE.State.DeliveryState;

public class Processing implements DeliveryState {
    private final DeliveryPackage deliveryPackage;

    public Processing(DeliveryPackage deliveryPackage) {
        this.deliveryPackage = deliveryPackage;
    }

    @Override
    public void track() {
        System.out.println("Tracking: Package is still in processing.");
    }

    @Override
    public void cancel() {
        System.out.println("Package canceled.");
        deliveryPackage.setState(deliveryPackage.getCanceledState());
    }

    @Override
    public void ship() {
        System.out.println("Package shipped.");
        deliveryPackage.setState(deliveryPackage.getShippedState());
    }

    @Override
    public void deliver() {
        System.out.println("Cannot deliver before shipping.");
    }

    @Override
    public void returnToSender() {
        System.out.println("Cannot return before delivery.");
    }
}
