package com.github.ASE.COR.Handlers;

import com.github.ASE.COR.Handler;
import com.github.ASE.COR.Ticket;

public class TechSupport extends Handler {
    @Override
    public void handleRequest(Ticket ticket) {
        if ("technical".equalsIgnoreCase(ticket.getRequestType())) {
            System.out.println("TechSupport: Resolving ticket - " + ticket.getDescription());
        } else if (nextHandler != null) {
            System.out.println("TechSupport: Passing to next handler...");
            nextHandler.handleRequest(ticket);
        } else {
            System.out.println("No handler available.");
        }
    }
}
