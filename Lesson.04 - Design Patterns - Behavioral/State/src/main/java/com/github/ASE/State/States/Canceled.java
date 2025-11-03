package com.github.ASE.State.States;

import com.github.ASE.State.DeliveryPackage;
import com.github.ASE.State.DeliveryState;

public class Canceled implements DeliveryState {
    private final DeliveryPackage deliveryPackage;

    public Canceled(DeliveryPackage deliveryPackage) {
        this.deliveryPackage = deliveryPackage;
    }

    @Override
    public void track() {
        System.out.println("Package was canceled.");
    }

    @Override
    public void cancel() {
        System.out.println("Already canceled.");
    }

    @Override
    public void ship() {
        System.out.println("Canceled packages cannot be shipped.");
    }

    @Override
    public void deliver() {
        System.out.println("Package was canceled.");
    }

    @Override
    public void returnToSender() {
        System.out.println("Package was canceled.");
    }
}
