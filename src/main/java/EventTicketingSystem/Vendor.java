package EventTicketingSystem;
import java.math.BigDecimal;


public class Vendor implements Runnable {
    private int totalTickets; //number of tickets to sell
    private int ticketReleaseRate; // the frequency of vendors adding tickets
    private TicketPool ticketPool; //Shared resource between Vendors and Customers


    //Constructor to initialize the Vendor's parameters
    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;

    }

    @Override
    public void run() {

        //Loop to add tickets to the pool
        for (int i = 0; i < totalTickets; i++) {

            Ticket ticket = new Ticket(i, "Test Event", new BigDecimal("1000"));
            ticketPool.addTicket(ticket); //add ticket to the ticket pool

            try {
                //(Calculating the s to ms)
                Thread.sleep(ticketReleaseRate * 1000);   //wait for the given release rate
            } catch (InterruptedException e) {
                //If interrupted, exit the method and stop the thread
                System.out.println(Thread.currentThread().getName() + " was interrupted. Stopping operation...");
                return;
            }

        }
    }
}
