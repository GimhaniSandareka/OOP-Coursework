package EventTicketingSystem;
import java.util.*;

public class TicketPool {
    private int maximumTicketCapacity;
    private Queue<Ticket> ticketsQueue; //Queue to hold the tickets available in the pool


    //Constructor to initialize the TicketPool with max capacity
    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketsQueue = new LinkedList<>(); //Initialize the ticket queue as an empty LinkedList
    }


    //method for Vendors to add tickets to the pool
    public synchronized void addTicket(Ticket ticket) {

        //Check if the ticketsQueue has reached the maximum capacity
        while (ticketsQueue.size() >= maximumTicketCapacity) {
            try {
                //If tickets pool is already full, the vendor thread will go into waiting status.
                wait();
                System.out.println("Ticket pool is full! Please wait!");
            } catch (InterruptedException e) {
                e.printStackTrace(); //For CLI
                throw new RuntimeException(e.getMessage()); //Throw an exception for Client-Server Application if needed

            }
        }

        this.ticketsQueue.add(ticket);  //Add new ticket to the pool
        notifyAll();
        //Notify all threads  waiting the new ticket has added
        System.out.println("Ticket added by: " + Thread.currentThread().getName() + " - current size is - " + ticketsQueue.size());
    }



    //Method to let Customer  buy ticket from the pool
    public synchronized Ticket buyTicket() {

        //Check if the pool is empty
        while (ticketsQueue.isEmpty()) {
            try {
                // If the pool is empty, the customer thread will go into waiting status
                wait();
                System.out.println("Ticket pool is empty! Wait until add tickets");
            } catch (InterruptedException e) {
                //handle exception and stops the operation
                System.out.println(Thread.currentThread().getName() + " - was interrupted while waiting for a ticket.");
                return null; //return null to indicate tickets aren't available
            }
        }

        //Remove and retrieve the first ticket in the queue
        Ticket ticket = ticketsQueue.poll();
        // Notify the threads that ticket has been purchased
        notifyAll();
        System.out.println("Ticket bought by customer " + Thread.currentThread().getName() + " , current size is - " + ticketsQueue.size() + " , Ticket is - " + ticket);
        return ticket; //return the ticket to the customer
    }


}
