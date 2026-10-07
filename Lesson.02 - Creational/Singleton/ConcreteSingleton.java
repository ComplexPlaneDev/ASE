package com.course.singleton;

/**
 * EXAMPLE 1 — ConcreteSingleton, the CONCRETE CLASS of the pattern.
 *
 * Classic thread-safe Singleton built on double-checked locking.
 * It carries the three mechanics that make it a singleton:
 *   1. private constructor            -> "new" is impossible from outside
 *   2. private static volatile field  -> the unique instance lives here
 *   3. public static getInstance()    -> the single global access point
 */
public final class ConcreteSingleton extends AbstractSingleton {

    /** The unique instance — volatile for double-checked locking. */
    private static volatile ConcreteSingleton instance;

    /** Private constructor: only getInstance() may create the object. */
    private ConcreteSingleton() {
        // initialization of the shared resource would go here
    }

    /** Global access point (lazy, thread-safe). */
    public static ConcreteSingleton getInstance() {
        if (instance == null) {                       // 1st check — no lock (fast path)
            synchronized (ConcreteSingleton.class) {  // lock only when needed
                if (instance == null) {               // 2nd check — inside the lock
                    instance = new ConcreteSingleton();
                }
            }
        }
        return instance;
    }

    @Override
    public String operation() {
        return "ConcreteSingleton says: I am the only instance (id=" + System.identityHashCode(this) + ")";
    }
}
