package com.w2051781_Backend.EventTicketingSystem.Service;

import com.w2051781_Backend.EventTicketingSystem.Model.Ticket;
import com.w2051781_Backend.EventTicketingSystem.Model.TicketPoolStatus;
import com.w2051781_Backend.EventTicketingSystem.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;

/*
    Service to manage the ticket pool.
    This handles ticket addition, removal and capacity control in a thread-safe manner.
 */

@Service
public class TicketPoolService {

    //Thread safe deque to hold the tickets
    private final ConcurrentLinkedDeque<Ticket> ticketPool = new ConcurrentLinkedDeque<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final int maxCapacity = 150; //Maximum ticket capacity
    private int soldTickets = 0;

    @Autowired
    private TicketRepository ticketRepository;



    //Adds a ticket to the pool if pool isn't full
//    public void addTicket(Ticket ticket) {
//        lock.lock();
//        try{
//            if (ticketPool.size() < maxCapacity) {
//                ticketPool.add(ticket);
//                System.out.println("Vendor added ticket : " + ticket);
//            } else {
//                System.out.println("Ticket pool is full. Cannot add tickets.");
//            }
//        }finally {
//            lock.unlock();
//        }
//    }

    //Remove a ticket from the pool
//    public Ticket removeTicket() {
//        lock.lock();
//        try{
//            if (!ticketPool.isEmpty()) {
//                Ticket ticket = ticketPool.poll();
//                System.out.println("Vendor removed ticket : " + ticket);
//                return ticket;
//            } else {
//                System.out.println("No tickets available.");
//                return null;  //returns null if there's no tickets available
//            }
//        } finally {
//            lock.unlock();
//        }
//    }

    public Ticket removeTicket(String caller) {
        lock.lock();
        try {
            if (!ticketPool.isEmpty()) {
                Ticket ticket = ticketPool.poll();
                System.out.println(caller + " removed ticket: " + ticket);
                soldTickets++;
                return ticket;
            } else {
                System.out.println("No tickets available for " + caller);
                return null;  // Returns null if there's no ticket available
            }
        } finally {
            lock.unlock();
        }
    }

    public void addTicket(Ticket ticket, String caller) {
        lock.lock();
        try {
            if (ticketPool.size() < maxCapacity) {
                ticketPool.add(ticket);
                System.out.println(caller + " added ticket: " + ticket);
            } else {
                System.out.println("Ticket pool is full. " + caller + " cannot add tickets.");
            }
        } finally {
            lock.unlock();
        }
    }



    //Returns the current number of available tickets in the pool
    public int availableTickets() {
        lock.lock();
        try {
            return ticketPool.size();
        } finally {
            lock.unlock();
        }
    }
    //returns the current status of the ticket pool
    public TicketPoolStatus getCurrentStatus(){
//        Ticket ticket = ticketRepository.findById(1).orElseThrow(() -> new RuntimeException("No tickets available "));
//        return new TicketPoolStatus(ticket.getAvailableTickets());
        int availableTickets = ticketPool.size();
        return new TicketPoolStatus(availableTickets, soldTickets);

    }
    //Deduct tickets when a customer buys
    public synchronized boolean purchaseTicket() {
        Ticket ticket = ticketRepository.findById(1).orElseThrow(() -> new RuntimeException("Ticket not found "));
        if (ticket.getAvailableTickets() > 0) {
            ticket.setAvailableTickets(ticket.getAvailableTickets() - 1);
            ticketRepository.save(ticket);
            return true; //Ticket successfully purchased.
        }else {
            return false; //No tickets available
        }
    }

    public synchronized void addTickets(int ticketsToAdd) {
        Ticket ticket = ticketRepository.findById(1).orElseThrow(() -> new RuntimeException("Ticket not found "));
        ticket.setAvailableTickets(ticket.getAvailableTickets() + ticketsToAdd);
        ticketRepository.save(ticket);
    }

}
