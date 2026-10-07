package com.course.singleton;

/**
 * Another client component (part of EXAMPLE 3).
 * It logs through the SAME Logger instance as OrderService.
 */
public class PaymentService {

    public void charge(String customer, double amount) {
        Logger.getInstance().log("PaymentService", "charged " + amount + " to " + customer);
    }
}
