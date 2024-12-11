package com.w2051781_Backend.EventTicketingSystem.Controller;

import com.w2051781_Backend.EventTicketingSystem.Config.SystemConfig;
import com.w2051781_Backend.EventTicketingSystem.Model.Vendor;
import com.w2051781_Backend.EventTicketingSystem.Repository.VendorRepository;
import com.w2051781_Backend.EventTicketingSystem.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VendorController {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    VendorRepository vendorRepository;
    //get all the vendors
    //localhost:8080/vendors
    @GetMapping("/vendors")
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors;
    }

    //localhost:8080/vendors/1
    @GetMapping("/vendors/{id}")
    public Vendor getVendor(@PathVariable int id) {
        Vendor vendor = vendorRepository.findById(id).get();
        return vendor;
    }

    //localhost:8080/vendor/add
    @PostMapping("/vendor/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createVendor(@RequestBody Vendor vendor) {
        vendorRepository.save(vendor);
    }
    //localhost:8080/vendor/update/1
    @PutMapping ("/vendor/update/{id}")
    public Vendor updateVendors(@PathVariable int id) {
        Vendor vendor = vendorRepository.findById(id).get();
        vendor.setName(vendor.getName());
        vendor.setContact(vendor.getContact());
        vendorRepository.save(vendor);
        return vendor;
    }


    @DeleteMapping ("/vendor/delete/{id}")
    public void removeVendor(@PathVariable int id) {
        Vendor vendor = vendorRepository.findById(id).get();
        vendorRepository.delete(vendor);
    }

    // Endpoint to update system configuration
    @PostMapping("/vendor/update-config")
    @ResponseStatus(code = HttpStatus.OK)
    public String updateConfig(@RequestBody ConfigRequest request) {
        // Update the system configuration values
        int totalTickets = request.getTotalTickets();
        int ticketReleaseRate = request.getTicketReleaseRate();
        int customerRetrievalRate = request.getCustomerRetrievalRate();

        systemConfig.updateConfig(totalTickets, ticketReleaseRate, customerRetrievalRate);

        return "Configuration updated successfully!";
    }




}