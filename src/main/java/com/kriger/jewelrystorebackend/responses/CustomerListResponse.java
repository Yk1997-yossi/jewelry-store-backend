package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.models.Customer;
import java.util.List;

public class CustomerListResponse extends BasicResponse {

    private List<Customer> customerList;

    public CustomerListResponse(boolean success, String errorMessage, List<Customer> customerList) {
        super(success, errorMessage);
        this.customerList = customerList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}