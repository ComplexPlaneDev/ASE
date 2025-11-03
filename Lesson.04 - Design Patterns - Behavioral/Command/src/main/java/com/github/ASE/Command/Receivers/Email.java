package com.github.ASE.Command.Receivers;

public class Email {
    public void send(String recipient, String subject, String body) {
        System.out.println("Sending email to " + recipient + ": " + subject);
    }

    public void delete(String recipient) {
        System.out.println("Deleting email to " + recipient);
    }
}
