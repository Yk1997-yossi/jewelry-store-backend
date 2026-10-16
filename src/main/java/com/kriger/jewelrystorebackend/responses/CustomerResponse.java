package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.dtos.CustomerResponseDTO;

public class CustomerResponse extends BasicResponse {
    private CustomerResponseDTO customer;

    public CustomerResponse(boolean success, String errorMessage, CustomerResponseDTO  customer) {
        super(success, errorMessage);
        this.customer = customer;
    }

    public CustomerResponseDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponseDTO customer) {
        this.customer = customer;
    }
}