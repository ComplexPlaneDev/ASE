package com.github.intrinsics;

public class TicketType {
  private final String venue;
  private final String concert;
  private final String date;

  TicketType(String venue, String concert, String date) {
    this.venue = venue;
    this.concert = concert;
    this.date = date;
  }

  public void printTicket(String name, String seatNumber, String priceTier) {
    System.out.println("Event Data:");
    System.out.println("Concert: " + concert);
    System.out.println("Venue: " + venue);
    System.out.println("Date: " + date);
    System.out.println("Name: " + name);
    System.out.println("Seat Number: " + seatNumber);
    System.out.println("Price Tier: " + priceTier);
  }

}
