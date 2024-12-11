package com.w2051781_Backend.EventTicketingSystem.Model;

public class TicketPoolStatus {
    private int availableTickets;
    private int soldTickets;

    //constructor to initialize the available tickets
    public TicketPoolStatus(int availableTickets, int soldTickets) {
        this.availableTickets = availableTickets;
        this.soldTickets = soldTickets;
    }
    //getter
    public int getAvailableTickets() {
        return availableTickets;
    }
    //Setter
    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
    public int getSoldTickets() {
        return soldTickets;
    }
    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }
}
