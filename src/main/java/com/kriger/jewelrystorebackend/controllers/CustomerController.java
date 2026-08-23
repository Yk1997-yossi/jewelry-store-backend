package com.kriger.jewelrystorebackend.controllers;

import com.kriger.jewelrystorebackend.dtos.LoginRequestDTO;
import com.kriger.jewelrystorebackend.dtos.RegisterRequestDTO;
import com.kriger.jewelrystorebackend.dtos.UpdateCustomerDetailsRequestDTO;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.services.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping
    public BasicResponse getAllCustomers(){
        return this.customerService.getAllCustomers();
    }
    @PostMapping("/register")
    public BasicResponse addNewCustomer(@RequestBody RegisterRequestDTO request){
        return this.customerService.addNewCustomer(request);
    }
    @PostMapping("/login")
    public BasicResponse login(@RequestBody LoginRequestDTO request){
        return this.customerService.login(request);
    }
    @GetMapping("/{id}")
    public BasicResponse getCustomerById(@PathVariable Long id){
        return this.customerService.getCustomerById(id);
    }

    @GetMapping("/email")
    public BasicResponse getCustomerByEmail(@RequestParam String email) {
        return this.customerService.getCustomerByEmail(email);
    }

    @PutMapping("/{id}")
    public BasicResponse updateCustomerDetails(@PathVariable Long id, @RequestBody UpdateCustomerDetailsRequestDTO update){
        return this.customerService.updateCustomerDetails(id, update);
    }
    @DeleteMapping("/{id}")
    public BasicResponse deleteCustomerById(@PathVariable Long id) {
        return this.customerService.deleteCustomerById(id);
    }
}
