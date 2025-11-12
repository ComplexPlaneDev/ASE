package com.github.ASE.DSFSM;

public interface PaymentGateway {
    boolean charge(String userId, double amount);
}
