package com.w2051781_Backend.EventTicketingSystem.Repository;

import com.w2051781_Backend.EventTicketingSystem.Model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomerId(int customerId);
}
