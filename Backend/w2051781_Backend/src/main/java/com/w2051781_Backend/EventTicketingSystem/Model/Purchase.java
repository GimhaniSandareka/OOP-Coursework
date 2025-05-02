package com.w2051781_Backend.EventTicketingSystem.Model;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int customerId;
    private int ticketCount;
    private LocalDateTime purchaseDate;

    public Purchase() {}

    public Purchase(int customerId, int ticketCount, LocalDateTime purchaseDate) {
        this.customerId = customerId;
        this.ticketCount = ticketCount;
        this.purchaseDate = purchaseDate;
    }

    public Long getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTicketCount() {
        return ticketCount;
    }
    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

}
