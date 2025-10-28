package com.bridge.implementor;

public interface EmailProvider {
    void authenticate();
    void processEmail(String emailData);
    void logout();
}
