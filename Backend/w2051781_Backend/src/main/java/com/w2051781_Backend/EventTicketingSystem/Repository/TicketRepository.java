package com.w2051781_Backend.EventTicketingSystem.Repository;

import com.w2051781_Backend.EventTicketingSystem.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
