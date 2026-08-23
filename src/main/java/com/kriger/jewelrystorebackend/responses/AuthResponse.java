package com.kriger.jewelrystorebackend.responses;

import com.kriger.jewelrystorebackend.models.Customer;

public class AuthResponse extends BasicResponse{
    private Customer customer;
    private String token;

    public AuthResponse(boolean success, String errorMessage, Customer customer, String token) {
        super(success, errorMessage);
        this.customer = customer;
        this.token = token;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
