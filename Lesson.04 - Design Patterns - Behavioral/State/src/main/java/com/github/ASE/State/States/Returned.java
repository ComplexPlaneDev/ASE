package com.github.ASE.State.States;

import com.github.ASE.State.DeliveryPackage;
import com.github.ASE.State.DeliveryState;

public class Returned implements DeliveryState {
    private final DeliveryPackage deliveryPackage;

    public Returned(DeliveryPackage deliveryPackage) {
        this.deliveryPackage = deliveryPackage;
    }

    @Override
    public void track() {
        System.out.println("Tracking: Package has been returned.");
    }

    @Override
    public void cancel() {
        System.out.println("Package already returned.");
    }

    @Override
    public void ship() {
        System.out.println("Cannot re-ship a returned package.");
    }

    @Override
    public void deliver() {
        System.out.println("Cannot re-deliver a returned package.");
    }

    @Override
    public void returnToSender() {
        System.out.println("Package already returned.");
    }
}
