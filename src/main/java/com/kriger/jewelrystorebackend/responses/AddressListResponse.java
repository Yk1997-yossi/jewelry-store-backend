package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.models.Address;
import java.util.List;

public class AddressListResponse extends BasicResponse{
    private List<Address> addressList;

    public AddressListResponse(boolean success,String errorMessage,List<Address> addressList){
        super(success, errorMessage);
        this.addressList = addressList;
    }
    public List<Address> getAddressList() {
        return addressList;
    }
    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
