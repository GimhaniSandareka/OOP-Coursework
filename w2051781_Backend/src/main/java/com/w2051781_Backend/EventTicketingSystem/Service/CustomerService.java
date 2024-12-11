
package com.w2051781_Backend.EventTicketingSystem.Service;

import com.w2051781_Backend.EventTicketingSystem.Model.Ticket;

/*
    Customer threads that retrieves tickets from the pool at given rate
 */

public class CustomerService implements Runnable {
    private final int ticketsToRetrieve; //number of tickets to retrieve
    private final TicketPoolService ticketPoolService; //ticketpool, which used to add/remove tickets
    private final int customerRetrievalRate; //rate customer retrieve tickets in seconds

    public CustomerService(int ticketsToRetrieve, TicketPoolService ticketPoolService, int customerRetrievalRate) {
        this.ticketsToRetrieve = ticketsToRetrieve;
        this.ticketPoolService = ticketPoolService;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    //Retrives tickets from the pool at given rate
    //Thread stops when customer has retrieved the tickets they wanted, or the thread was interrupted
    @Override
    public void run() {
        int ticketsRetrieved = 0;

        //loop to retrieve tickets until the wanted tickets are retrieved, or the thread is interrupted
        while (!Thread.currentThread().isInterrupted() && ticketsRetrieved < ticketsToRetrieve) {
            try{
                //Retrieve a ticket from the ticket pool
//                Ticket ticket = ticketPoolService.removeTicket();
                Ticket ticket = ticketPoolService.removeTicket("Customer " + Thread.currentThread().getName());

                if (ticket != null) {
                    ticketsRetrieved++; //Incrementing the count of retrieved tickets
                }
                Thread.sleep(1000 / customerRetrievalRate); //controlling customer retrieval rate
            } catch (InterruptedException e) {
                System.out.println("Customer" + Thread.currentThread().getName() + " was interrupted");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Customer" + Thread.currentThread().getName() + " finished retrieving tickets.");
    }

}
