package com.kriger.jewelrystorebackend.responses;

import com.kriger.jewelrystorebackend.dtos.CustomerResponseDTO;

public class AuthResponse extends BasicResponse{
    private CustomerResponseDTO customer;
    private String token;

    public AuthResponse(boolean success, String errorMessage, CustomerResponseDTO customer, String token) {
        super(success, errorMessage);
        this.customer = customer;
        this.token = token;
    }

    public CustomerResponseDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponseDTO customer) {
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
