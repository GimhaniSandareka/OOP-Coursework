package com.w2051781_Backend.EventTicketingSystem.Controller;

import com.w2051781_Backend.EventTicketingSystem.Config.SystemConfig;
import com.w2051781_Backend.EventTicketingSystem.Model.TicketPoolStatus;
import com.w2051781_Backend.EventTicketingSystem.Service.CustomerService;
import com.w2051781_Backend.EventTicketingSystem.Service.TicketPoolService;
import com.w2051781_Backend.EventTicketingSystem.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 Controller that handles HTTP requests to start and stop the ticketing system
 Interacts with the system's services to manage the vendor and customer threads
 */

@RestController
@RequestMapping("/api")
public class ButtonController {
    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private SystemConfig systemConfig;

    private Thread vendorThread;
    private Thread customerThread;

    /*
        Starts the system by creating vendor and customer threads
     */
    @GetMapping ("/start")
    public String startSystem(@RequestParam int totalTickets) {
        stopSystem(); //Stop any existing threads before starting new ones

        //Create and start vendor thread
        VendorService vendor = new VendorService(totalTickets, systemConfig.getTicketReleaseRate(), ticketPoolService);
        vendorThread = new Thread(vendor, "Vendor ");
        vendorThread.start();

        //Create and start customer thread
        CustomerService customer = new CustomerService(totalTickets,ticketPoolService, systemConfig.getCustomerRetrievalRate());
        customerThread = new Thread(customer, "Customer ");
        customerThread.start();

        return "System started with vendors and customers!";

    }

    //Stops the system by interrupting vendor and customer threads
    @GetMapping ("/stop")
    public String stopSystem() {
        if (vendorThread != null && vendorThread.isAlive()) {
            vendorThread.interrupt();
            System.out.println("Vendor stopped.");
        }
        if (customerThread != null && customerThread.isAlive()) {
            customerThread.interrupt();
            System.out.println("Customer stopped.");
        }
        return "System stopped.";
    }

    //Endpoint to fetch ticket pool status
    @GetMapping("/ticketpool-status")
    public TicketPoolStatus getTicketPoolStatus(){
        return ticketPoolService.getCurrentStatus();
    }
}
