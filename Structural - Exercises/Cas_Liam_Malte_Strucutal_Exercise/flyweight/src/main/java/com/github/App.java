package com.github;

import java.util.ArrayList;
import java.util.List;

import com.github.extrinsics.Ticket;
import com.github.intrinsics.TicketType;
import com.github.intrinsics.TicketTypeFactory;

public class App {
    private final List<Ticket> tickets = new ArrayList<>();

    public void createTicket(String holderName, String seatNumber, String priceTier, String venue, String concert,
            String date) {
        TicketType ticketType = TicketTypeFactory.getTicketType(concert, venue, date);
        Ticket ticket = new Ticket(holderName, seatNumber, priceTier, ticketType);
        tickets.add(ticket);
    }

    public void printTickets() {
        System.out.println("Printing tickets...");
        for (Ticket ticket : tickets) {
            ticket.printTicket();
        }
    }

    public static void main(String[] args) {
        App ticketPrinter = new App();

        for (int i = 0; i < 1000; i++) {
            ticketPrinter.createTicket("Name" + i, "A" + i, "VIP", "Paradiso Amsterdam",
                    "King Gizzard and the Lizzard Wizard", "19-02-2025");
        }
        ticketPrinter.printTickets();
    }
}