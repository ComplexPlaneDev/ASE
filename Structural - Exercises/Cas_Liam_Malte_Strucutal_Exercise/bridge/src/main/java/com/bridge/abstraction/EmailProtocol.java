package com.bridge.abstraction;

import com.bridge.implementor.EmailProvider;

public abstract class EmailProtocol {
    protected EmailProvider provider;

    public EmailProtocol(EmailProvider provider) {
        this.provider = provider;
    }

    public abstract void initialize();

    public abstract void sendEmail(String message);

    public abstract void shutdown();
}
