package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.models.Address;

public class AddressResponse extends BasicResponse{
    private Address address;

    public AddressResponse(boolean success,String errorMessage,Address address){
        super(success, errorMessage);
        this.address = address;
    }
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
