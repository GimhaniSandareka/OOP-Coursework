package com.w2051781_Backend.EventTicketingSystem.Repository;

import com.w2051781_Backend.EventTicketingSystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
