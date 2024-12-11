package com.w2051781_Backend.EventTicketingSystem.Model;

import jakarta.persistence.*;

@Entity
@Table (name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "Event_Name")
    private String eventName;
    @Column (name = "Available_No_Of_Tickets")
    private int availableTickets;
    @Column (name = "Ticket_Price")
    private double price;

    public Ticket() {}

    public Ticket(String eventName, int availableTickets, double price) {
        super();
        this.eventName = eventName;
        this.availableTickets = availableTickets;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", availableTickets=" + availableTickets +
                ", price=" + price +
                '}';
    }
}
