package com.course.singleton;

/**
 * Lecture demo driver — runs the three Singleton examples.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== EXAMPLE 1: classic thread-safe Singleton (double-checked locking) ===");
        ConcreteSingleton s1 = ConcreteSingleton.getInstance();
        ConcreteSingleton s2 = ConcreteSingleton.getInstance();
        System.out.println(s1.operation());
        System.out.println("Same instance? " + (s1 == s2));   // true — the pattern works

        System.out.println();
        System.out.println("=== EXAMPLE 2: enum-based Singleton ===");
        EnumSingleton e1 = EnumSingleton.INSTANCE;
        EnumSingleton e2 = EnumSingleton.INSTANCE;
        System.out.println(e1.operation());
        System.out.println("Same instance? " + (e1 == e2));   // true
        System.out.println("Number of constants: " + EnumSingleton.values().length);  // always 1

        System.out.println();
        System.out.println("=== EXAMPLE 3: practical Singleton — shared Logger ===");
        new OrderService().placeOrder("Alice");
        new PaymentService().charge("Alice", 49.90);
        new OrderService().placeOrder("Bob");
        System.out.println("Total entries in the SHARED logger: " + Logger.getInstance().getEntries().size());
    }
}
