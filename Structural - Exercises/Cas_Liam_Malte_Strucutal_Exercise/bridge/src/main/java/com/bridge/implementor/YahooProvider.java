package com.bridge.implementor;

public class YahooProvider implements EmailProvider {
    @Override
    public void authenticate() {
        System.out.println("Welcome back to Yahoo user!");
    }

    @Override
    public void processEmail(String emailData) {
        System.out.println("Your email with the subject " + emailData + " is processed by Yahoo.");
    }

    @Override
    public void logout() {
        System.out.println("User successfully logged out of Yahoo.");
    }
}
