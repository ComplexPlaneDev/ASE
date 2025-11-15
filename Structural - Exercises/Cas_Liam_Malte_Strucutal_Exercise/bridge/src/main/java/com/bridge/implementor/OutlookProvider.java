package com.bridge.implementor;

public class OutlookProvider implements EmailProvider {
    @Override
    public void authenticate() {
        System.out.println("Welcome back to Outlook user!");
    }

    @Override
    public void processEmail(String emailData) {
        System.out.println("Your email with the subject " + emailData + " is processed by Outlook.");
    }

    @Override
    public void logout() {
        System.out.println("User successfully logged out of Outlook.");
    }
}
