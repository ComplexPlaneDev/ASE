package com.github.intrinsics;

import java.util.HashMap;
import java.util.Map;

public class TicketTypeFactory {
  private static final Map<String, TicketType> ticketTypes = new HashMap<>();

  public static TicketType getTicketType(String concert, String venue, String date) {
    String key = concert + venue + date;
    if (!ticketTypes.containsKey(key)) {
      ticketTypes.put(key, new TicketType(venue, concert, date));
    }
    return ticketTypes.get(key);
  }

  public static int getTicketTypeCount() {
    return ticketTypes.size();
  }
}
