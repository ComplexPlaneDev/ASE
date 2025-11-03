package com.github.ASE.Command.Commands;

import com.github.ASE.Command.Receivers.Email;

public class SendEmail extends Schedulable {
    private final Email emailService;
    private final String recipient;
    private final String subject;
    private final String body;

    public SendEmail(Email emailService, String recipient, String subject, String body) {
        this.emailService = emailService;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public void execute() {
        emailService.send(recipient, subject, body);
    }

    @Override
    public void undo() {
        emailService.delete(recipient);
    }
}
