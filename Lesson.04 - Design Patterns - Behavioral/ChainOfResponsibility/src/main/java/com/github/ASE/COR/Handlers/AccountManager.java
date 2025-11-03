package com.github.ASE.COR.Handlers;

import com.github.ASE.COR.Handler;
import com.github.ASE.COR.Ticket;

public class AccountManager extends Handler {
    @Override
    public void handleRequest(Ticket ticket) {
        if ("account".equalsIgnoreCase(ticket.getRequestType())) {
            System.out.println("AccountManager: Resolving ticket - " + ticket.getDescription());
        } else if (nextHandler != null) {
            System.out.println("AccountManager: Passing to next handler...");
            nextHandler.handleRequest(ticket);
        } else {
            System.out.println("No handler available.");
        }
    }
}
