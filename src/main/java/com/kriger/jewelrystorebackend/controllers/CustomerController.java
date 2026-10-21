package com.kriger.jewelrystorebackend.controllers;
import org.springframework.security.core.Authentication;
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

    @PutMapping("/me")
    public BasicResponse updateCurrentCustomer(
            Authentication authentication,
            @RequestBody UpdateCustomerDetailsRequestDTO update) {
        String email = authentication.getName();
        return this.customerService.updateCurrentCustomer(
                email,
                update
        );
    }
    @DeleteMapping("/me")
    public BasicResponse deleteCurrentCustomer(
            Authentication authentication) {
        String email = authentication.getName();
        return this.customerService.deleteCurrentCustomer(email);
    }
    @GetMapping("/me")
    public BasicResponse getCurrentCustomer(Authentication authentication) {
        String email = authentication.getName();
        return this.customerService.getCustomerByEmail(email);
    }
}
