package com.github.extrinsics;

import com.github.intrinsics.TicketType;

public class Ticket {
  private final String holderName;
  private final String seatNumber;
  private final String priceTier;
  private final TicketType ticketType;

  public Ticket(String holderName, String seatNumber, String priceTier, TicketType ticketType) {
    this.holderName = holderName;
    this.seatNumber = seatNumber;
    this.priceTier = priceTier;
    this.ticketType = ticketType;
  }

  public void printTicket() {
    ticketType.printTicket(holderName, seatNumber, priceTier);
  }

}
