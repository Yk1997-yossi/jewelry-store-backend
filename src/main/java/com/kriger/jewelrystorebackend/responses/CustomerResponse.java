package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.models.Customer;

public class CustomerResponse extends BasicResponse {
    private Customer customer;

    public CustomerResponse(boolean success, String errorMessage, Customer customer) {
        super(success, errorMessage);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}