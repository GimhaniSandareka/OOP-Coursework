package com.w2051781_Backend.EventTicketingSystem.Controller;
import java.util.List;
import com.w2051781_Backend.EventTicketingSystem.Model.Customer;
import com.w2051781_Backend.EventTicketingSystem.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    //get all the customers
    //localhost:8080/customers
    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
       List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    //localhost:8080/customers/1
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable int id){
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }

    //localhost:8080/customer/add
    @PostMapping("/customer/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCustomer(@RequestBody Customer customer){
        customerRepository.save(customer);
    }

    //localhost:8080/customer/update/1
    @PutMapping ("/customer/update/{id}")
    public Customer updateCustomers(@PathVariable int id){
        Customer customer = customerRepository.findById(id).get();
        customer.setName(customer.getName());
        customer.setEmail(customer.getEmail());
        customerRepository.save(customer);
        return customer;
    }

    @DeleteMapping("/customer/delete/{id}")
    public void removeCustomer(@PathVariable int id){
        Customer customer = customerRepository.findById(id).get();
        customerRepository.delete(customer);
    }

}
