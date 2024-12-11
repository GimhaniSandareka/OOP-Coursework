package com.w2051781_Backend.EventTicketingSystem.Service;

import com.w2051781_Backend.EventTicketingSystem.Model.Ticket;

public class VendorService implements Runnable {
    private final int totalTickets; //Total tickets to be released by vendor
    private final int ticketReleaseRate; //Frequency of releasing tickets (in seconds)
    private final TicketPoolService ticketPoolService; //Shared ticket pool

    private boolean running = true;

    public VendorService(int totalTickets, int ticketReleaseRate, TicketPoolService ticketPoolService) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPoolService = ticketPoolService;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalTickets; i++) {
                if (!running) {
                    System.out.println("Vendor " + Thread.currentThread().getName() + " is stopping");
                    break;
                }

                //Create a new ticket
                Ticket ticket = new Ticket("Test Event"+ i, i, 1000.00);
//                ticketPoolService.addTicket(ticket);
                ticketPoolService.addTicket(ticket, "Vendor " + Thread.currentThread().getName());



                System.out.println("Vendor " + Thread.currentThread().getName() + " added ticket: " + ticket);

                //Delay for the ticket release rate
                Thread.sleep(ticketReleaseRate*1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor " + Thread.currentThread().getName() + " was interrupted");
        }
    }

    //Method to stop the vendor
    public void stop() {
        running = false;
    }
}
