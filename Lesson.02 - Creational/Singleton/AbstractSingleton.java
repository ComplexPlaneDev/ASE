package com.course.singleton;

/**
 * AbstractSingleton — the ABSTRACT CLASS of the pattern (the "Singleton" role in GoF).
 *
 * Defines the common type for all singletons and blocks direct instantiation:
 *   - implements the Singleton interface (the business contract)
 *   - exposes only a PROTECTED constructor, so no client can ever call
 *     "new AbstractSingleton(...)" — outside code can only extend it.
 *
 * The actual singleton mechanics (private instance field + static factory
 * method) live in each concrete class, because the unique instance is
 * per-class: one instance OF ConcreteSingleton, not of the abstract type.
 */
public abstract class AbstractSingleton implements Singleton {

    /** Protected constructor: not callable from clients, only by subclasses. */
    protected AbstractSingleton() {
        // no-op — shared initialization could go here
    }
}
