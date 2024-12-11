package com.w2051781_Backend.EventTicketingSystem.Controller;

import com.w2051781_Backend.EventTicketingSystem.Model.TicketPoolStatus;
import com.w2051781_Backend.EventTicketingSystem.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketPoolController {

    @Autowired
    private TicketPoolService ticketPoolService;

    //Endpoint to get the current status of the ticket pool
    @GetMapping("/status")
    public TicketPoolStatus getTicketPoolStatus(){
        return ticketPoolService.getCurrentStatus();
    }

}
