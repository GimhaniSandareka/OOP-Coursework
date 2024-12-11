package com.w2051781_Backend.EventTicketingSystem.Controller;

public class ConfigRequest {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    // Getters and setters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }



    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }


}

