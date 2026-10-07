package com.course.singleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * EXAMPLE 3 — Logger, a practical Singleton: an application-wide log service.
 *
 * Every component that logs goes through the SAME shared instance, so all
 * messages are collected in one place (in memory here). This is the classic
 * real-world use case of the pattern: shared resources such as loggers,
 * configuration holders, connection pools and caches.
 *
 * Note the EAGER initialization variant: the instance is created when the
 * Logger class is loaded, so getInstance() is trivially thread-safe.
 */
public final class Logger {

    /** Eager initialization: one instance, created with the class. */
    private static final Logger INSTANCE = new Logger();

    private final List<String> entries = new ArrayList<>();

    /** Private constructor: no client can create a second logger. */
    private Logger() {
        // no-op
    }

    /** Global access point. */
    public static Logger getInstance() {
        return INSTANCE;
    }

    /** Log a message from any component of the application. */
    public void log(String component, String message) {
        String entry = "[" + LocalDateTime.now() + "] " + component + " -> " + message;
        entries.add(entry);
        System.out.println(entry);
    }

    /** Read-only view of everything logged so far (shared state!). */
    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
