package com.bridge;

import com.bridge.abstraction.EmailProtocol;
import com.bridge.abstraction.POP3Protocol;
import com.bridge.abstraction.SMTPProtocol;
import com.bridge.implementor.EmailProvider;
import com.bridge.implementor.GmailProvider;
import com.bridge.implementor.OutlookProvider;

public class Main {
    public static void main(String[] args) {
        EmailProvider gmail = new GmailProvider();
        EmailProtocol smtp = new SMTPProtocol(gmail);
        smtp.initialize();
        smtp.sendEmail("Structual Design Pattern Exercise");
        smtp.shutdown();

        System.out.println();

        EmailProvider outlook = new OutlookProvider();
        EmailProtocol pop3 = new POP3Protocol(outlook);
        pop3.initialize();
        pop3.sendEmail("Outlook email using POP3");
        pop3.shutdown();
    }
}