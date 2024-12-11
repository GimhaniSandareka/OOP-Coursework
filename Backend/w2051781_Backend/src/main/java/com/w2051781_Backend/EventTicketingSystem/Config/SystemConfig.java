package com.w2051781_Backend.EventTicketingSystem.Config;

import org.springframework.stereotype.Component;

/*
    Configuration class has system parameters like the total number of tickets,
    tickets release rate and customer retrieval rate.
*/

@Component
public class SystemConfig {
    private int totalTickets = 100; //total number of tickets available for sale
    private int ticketReleaseRate = 4; //the rate vendor release tickets (in seconds)
    private int customerRetrievalRate = 2; // the rate customer buys tickets (in seconds)

    //getters
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    /*
        Updates the configuration values for totalTickets, ticketReleaseRate and customerRetrievalRate
     */
    public synchronized void updateConfig(int totalTickets, int ticketReleaseRate, int customerRetrievalRate) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }
}
