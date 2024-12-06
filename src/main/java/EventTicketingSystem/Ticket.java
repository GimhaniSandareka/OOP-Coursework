package EventTicketingSystem;
import java.math.BigDecimal;


public class Ticket {
    private int ticketID;
    private String eventName;
    private BigDecimal ticketPrice;

    public Ticket(int ticketID, String eventName, BigDecimal ticketPrice) {
        this.ticketID = ticketID;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }
    public int getTicketID() {

        return ticketID;
    }
    public String getEventName() {

        return eventName;
    }
    public BigDecimal getTicketPrice() {

        return ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket {Ticket ID = " + ticketID + ", Event Name = " + eventName + ", Ticket Price = " + ticketPrice + "}";
    }
}
