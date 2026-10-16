package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.dtos.CustomerResponseDTO;
import java.util.List;

public class CustomerListResponse extends BasicResponse {

    private List<CustomerResponseDTO> customerList;

    public CustomerListResponse(boolean success, String errorMessage, List<CustomerResponseDTO> customerList) {
        super(success, errorMessage);
        this.customerList = customerList;
    }

    public List<CustomerResponseDTO> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerResponseDTO> customerList) {
        this.customerList = customerList;
    }
}