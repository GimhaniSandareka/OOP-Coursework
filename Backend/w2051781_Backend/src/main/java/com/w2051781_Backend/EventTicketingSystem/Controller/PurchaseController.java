package com.w2051781_Backend.EventTicketingSystem.Controller;

import com.w2051781_Backend.EventTicketingSystem.Model.Purchase;
import com.w2051781_Backend.EventTicketingSystem.Model.TicketPoolStatus;
import com.w2051781_Backend.EventTicketingSystem.Repository.PurchaseRepository;
import com.w2051781_Backend.EventTicketingSystem.Service.TicketPoolService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PurchaseController {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private TicketPoolService ticketPoolService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTickets(@RequestBody Map<String, Object> payload) {
        int customerId = (int) payload.get("customerId");
        int ticketCount = (int) payload.get("ticketCount");
        TicketPoolStatus status = ticketPoolService.getCurrentStatus();
        if (status.getAvailableTickets() < ticketCount) {
            return ResponseEntity.badRequest().body("Not enough tickets available");
        }
        //Update ticket pool
        status.setAvailableTickets(status.getAvailableTickets() - ticketCount);
        status.setSoldTickets(status.getSoldTickets() - ticketCount);
        ticketPoolService.setCurrentStatus(status);
        //save purchase
        Purchase purchase = new Purchase(customerId, ticketCount, LocalDateTime.now());
        purchaseRepository.save(purchase);
        return ResponseEntity.ok(purchase);
    }

    @GetMapping("/purchases/{customerId}")
    public List<Purchase> getPurchases(@PathVariable int customerId) {
        return purchaseRepository.findByCustomerId(customerId);
    }
}
