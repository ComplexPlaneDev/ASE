package com.github.ASE.COR.Handlers;

import com.github.ASE.COR.Handler;
import com.github.ASE.COR.Ticket;

public class FAQ extends Handler {
    @Override
    public void handleRequest(Ticket ticket) {
        if ("faq".equalsIgnoreCase(ticket.getRequestType())) {
            System.out.println("FAQ: Resolving ticket - " + ticket.getDescription());
        } else if (nextHandler != null) {
            System.out.println("FAQ: Passing to next handler...");
            nextHandler.handleRequest(ticket);
        } else {
            System.out.println("No handler available.");
        }
    }
}
