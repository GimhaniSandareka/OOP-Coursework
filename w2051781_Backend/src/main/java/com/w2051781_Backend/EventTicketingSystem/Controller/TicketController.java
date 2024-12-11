package com.w2051781_Backend.EventTicketingSystem.Controller;

import com.w2051781_Backend.EventTicketingSystem.Model.Ticket;
import com.w2051781_Backend.EventTicketingSystem.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    //get all the tickets
    //localhost:8080/tickets
    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets;
    }

    //localhost:8080/tickets/1
    @GetMapping("/tickets/{id}")
    public Ticket getTicket(@PathVariable int id) {
        Ticket ticket = ticketRepository.findById(id).get();
        return ticket;
    }

    //localhost:8080/ticket/add
    @PostMapping("/ticket/add")
    @ResponseStatus (code = HttpStatus.CREATED)
    public void createTicket(@RequestBody Ticket ticket) {
        ticketRepository.save(ticket);
    }

    //localhost:8080/ticket/update/1
    @PutMapping("/ticket/update/{id}")
    public Ticket updateTickets(@PathVariable int id) {

        Ticket ticket = ticketRepository.findById(id).get();
        ticket.setAvailableTickets(ticket.getAvailableTickets() + 1);
        ticket.setEventName(ticket.getEventName());
        ticket.setPrice(ticket.getPrice());
        ticketRepository.save(ticket);
        return ticket;
    }

    @DeleteMapping ("/ticket/delete/{id}")
    public void removeTicket(@PathVariable int id) {
        Ticket ticket = ticketRepository.findById(id).get();
        ticketRepository.delete(ticket);
    }



}
