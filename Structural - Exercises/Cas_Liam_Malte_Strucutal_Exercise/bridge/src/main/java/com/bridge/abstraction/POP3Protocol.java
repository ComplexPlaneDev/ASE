package com.bridge.abstraction;

import com.bridge.implementor.EmailProvider;

public class POP3Protocol extends EmailProtocol {
    public POP3Protocol(EmailProvider provider) {
        super(provider);
    }

    @Override
    public void initialize() {
        System.out.println("POP3: Initializing mail transfer protocol");
        provider.authenticate();
    }

    @Override
    public void shutdown() {
        System.out.println("POP3: Shutting down protocol");
        provider.logout();
    }

    @Override
    public void sendEmail(String message) {
        System.out.println("POP3: Sending Email");
        provider.processEmail(message);
    }
}
