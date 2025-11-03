package com.github.ASE.State;

import com.github.ASE.State.States.Canceled;
import com.github.ASE.State.States.Delivered;
import com.github.ASE.State.States.Processing;
import com.github.ASE.State.States.Returned;
import com.github.ASE.State.States.Shipped;

public class DeliveryPackage {
    private DeliveryState currentState;

    public DeliveryPackage() {
        // Initial state
        this.currentState = new Processing(this);
    }

    public void setState(DeliveryState state) {
        this.currentState = state;
    }

    public void track() {
        currentState.track();
    }

    public void cancel() {
        currentState.cancel();
    }

    public void ship() {
        currentState.ship();
    }

    public void deliver() {
        currentState.deliver();
    }

    public void returnToSender() {
        currentState.returnToSender();
    }

    // Optional state accessors
    public DeliveryState getProcessingState() {
        return new Processing(this);
    }

    public DeliveryState getShippedState() {
        return new Shipped(this);
    }

    public DeliveryState getDeliveredState() {
        return new Delivered(this);
    }

    public DeliveryState getReturnedState() {
        return new Returned(this);
    }

    public DeliveryState getCanceledState() {
        return new Canceled(this);
    }
}
