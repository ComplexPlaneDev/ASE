package com.github.ASE.State;

public interface DeliveryState {
    void track();

    void cancel();

    void ship();

    void deliver();

    void returnToSender();
}
