package com.github.ASE.COR;

import com.github.ASE.COR.Handlers.AccountManager;
import com.github.ASE.COR.Handlers.FAQ;
import com.github.ASE.COR.Handlers.Fallback;
import com.github.ASE.COR.Handlers.TechSupport;

public class App {

    public static void main(String[] args) {
        // Build the chain
        Handler faqHandler = new FAQ();
        Handler technicalHandler = new TechSupport();
        Handler accountHandler = new AccountManager();
        Handler fallbackHandler = new Fallback();

        // Link the chain
        faqHandler.setNextHandler(technicalHandler);
        technicalHandler.setNextHandler(accountHandler);
        accountHandler.setNextHandler(fallbackHandler);

        // Submit tickets
        Ticket ticket1 = new Ticket("faq", "How do I reset my password?");
        Ticket ticket2 = new Ticket("technical", "App crashes on login.");
        Ticket ticket3 = new Ticket("account", "Upgrade my subscription.");
        Ticket ticket4 = new Ticket("refund", "I want a refund.");

        System.out.println("Processing Ticket 1:");
        faqHandler.handleRequest(ticket1);

        System.out.println("\nProcessing Ticket 2:");
        faqHandler.handleRequest(ticket2);

        System.out.println("\nProcessing Ticket 3:");
        faqHandler.handleRequest(ticket3);

        System.out.println("\nProcessing Ticket 4:");
        faqHandler.handleRequest(ticket4);
    }
}
