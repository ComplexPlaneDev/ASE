package com.github.ASE.COR;

public class Ticket {
    private String requestType;
    private String description;

    public Ticket(String requestType, String description) {
        this.requestType = requestType;
        this.description = description;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getDescription() {
        return description;
    }
}
