package com.bridge.abstraction;

import com.bridge.implementor.EmailProvider;

public class IMAPProtocol extends EmailProtocol {
    public IMAPProtocol(EmailProvider provider) {
        super(provider);
    }

    @Override
    public void initialize() {
        System.out.println("IMAP: Initializing mail transfer protocol");
        provider.authenticate();
    }

    @Override
    public void shutdown() {
        System.out.println("IMAP: Shutting down protocol");
        provider.logout();
    }

    @Override
    public void sendEmail(String message) {
        System.out.println("IMAP: Sending Email");
        provider.processEmail(message);
    }
}
