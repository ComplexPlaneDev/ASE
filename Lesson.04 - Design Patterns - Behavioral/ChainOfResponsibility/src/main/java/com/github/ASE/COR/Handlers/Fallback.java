package com.github.ASE.COR.Handlers;

import com.github.ASE.COR.Handler;
import com.github.ASE.COR.Ticket;

public class Fallback extends Handler {
    @Override
    public void handleRequest(Ticket ticket) {
        System.out.println("Fallback: Cannot resolve ticket. Please contact human support.");
    }
}
