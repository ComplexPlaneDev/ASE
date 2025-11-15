package com.bridge.abstraction;

import com.bridge.implementor.EmailProvider;

public class SMTPProtocol extends EmailProtocol {
    public SMTPProtocol(EmailProvider provider) {
        super(provider);
    }

    @Override
    public void initialize() {
        System.out.println("SMTP: Initializing mail transfer protocol");
        provider.authenticate();
    }

    @Override
    public void shutdown() {
        System.out.println("SMTP: Shutting down protocol");
        provider.logout();
    }

    @Override
    public void sendEmail(String message) {
        System.out.println("SMTP: Sending Email");
        provider.processEmail(message);
    }
}
