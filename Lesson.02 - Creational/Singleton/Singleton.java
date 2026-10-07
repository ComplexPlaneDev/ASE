package com.course.singleton;

/**
 * Singleton — the INTERFACE of the pattern.
 *
 * Declares the contract that every singleton exposes to its clients:
 * a business operation. Clients program against this interface and never
 * need to know how (or when) the unique instance is created.
 */
public interface Singleton {

    /** Business operation available to all clients. */
    String operation();
}
