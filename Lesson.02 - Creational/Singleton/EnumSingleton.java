package com.course.singleton;

/**
 * EXAMPLE 2 — EnumSingleton, the enum-based Singleton.
 *
 * Recommended by Joshua Bloch (Effective Java, Item 3): a single-element
 * enum is a Singleton that is thread-safe BY CONSTRUCTION and immune to
 * both reflection attacks and serialization attacks, because the JVM
 * guarantees that each enum constant is instantiated exactly once.
 *
 * Trade-off: an enum cannot be extended or lazily initialized with
 * constructor parameters (beyond what the enum allows), so it fits
 * simple singletons.
 */
public enum EnumSingleton implements Singleton {

    /** The only possible value — the JVM creates it exactly once. */
    INSTANCE;

    @Override
    public String operation() {
        return "EnumSingleton says: I am the only constant (id=" + System.identityHashCode(this) + ")";
    }
}
