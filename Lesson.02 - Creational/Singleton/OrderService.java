package com.course.singleton;

/**
 * A client component of the application (part of EXAMPLE 3).
 * It does not create its own logger: it uses the shared Singleton.
 */
public class OrderService {

    public void placeOrder(String customer) {
        Logger.getInstance().log("OrderService", "order placed for " + customer);
    }
}
