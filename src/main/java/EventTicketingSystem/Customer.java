package EventTicketingSystem;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerRetrievalRate;
    private volatile boolean running = true;

    //Constructor to initialize the customer's parameters
    public Customer(TicketPool ticketPool, Configuration configuration) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = configuration.getCustomerRetrievalRate();
    }



    @Override
    public void run(){

        //Loop to buy tickets
        while (running) {
            //Trying to get a ticket from the pool
            Ticket ticket = ticketPool.buyTicket();

            if (ticket == null) { //(If ticket is null, it's interrupted)
                System.out.println(Thread.currentThread().getName() + " is stopping due to interruption!");
                break; //Exit the loop if the thread was interrupted. (Stopping when tickets are not available)
            }

            System.out.println("Ticket Details: " + ticket + " , customer " + Thread.currentThread().getName());
            try {
                Thread.sleep(customerRetrievalRate * 1000); //delay of customers purchasing tickets
            } catch (InterruptedException e) {
                //Stop the operation if the thread is interrupted
                System.out.println("Customer "+ Thread.currentThread().getName() + " was interrupted. Stopping operation.");
                break;
            }
        }

    }

}
