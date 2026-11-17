package com.kriger.jewelrystorebackend.services;
import com.kriger.jewelrystorebackend.dao.AddressDao;
import com.kriger.jewelrystorebackend.dao.CustomerDAO;
import com.kriger.jewelrystorebackend.models.Address;
import com.kriger.jewelrystorebackend.models.Customer;
import com.kriger.jewelrystorebackend.responses.AddressListResponse;
import com.kriger.jewelrystorebackend.responses.AddressResponse;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.kriger.jewelrystorebackend.errors.AddressErrors.*;
import static com.kriger.jewelrystorebackend.errors.CustomerErrors.CUSTOMER_NOT_FOUND;

@Service
public class AddressService {
    private final AddressDao addressDao;
    private final CustomerDAO customerDAO;

    public AddressService(AddressDao addressDao,CustomerDAO customerDAO){
        this.addressDao = addressDao;
        this.customerDAO = customerDAO;
    }
    public AddressResponse addAddress(String email,Address address){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new AddressResponse(false,CUSTOMER_NOT_FOUND,null);
        if(!isValidAddress(address))
            return new AddressResponse(false,INVALID_ADDRESS,null);
        if(Boolean.TRUE.equals(address.getDefault()))
            this.addressDao.clearDefaultAddressesByCustomerId(customer.getId());
        int rowsAffected = this.addressDao.addAddress(customer.getId(),address);
        if(rowsAffected == 0)
            return new AddressResponse(false,ADDRESS_ADD_FAILED,null);
        return new AddressResponse(true,null,address);
    }
    public AddressListResponse getAllAddresses(String email){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new AddressListResponse(false,CUSTOMER_NOT_FOUND,null);
        List<Address> addressList = this.addressDao.getAllAddressesByCustomerId(customer.getId());
        if(addressList == null|| addressList.isEmpty())
            return new AddressListResponse(false,EMPTY_ADDRESS_LIST,null);
        return new AddressListResponse(true,null,addressList);
    }
    public AddressResponse getAddressById(String email,Long addressId) {
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if (customer == null)
            return new AddressResponse(false, CUSTOMER_NOT_FOUND, null);
        Address address = this.addressDao.getAddressByIdAndCustomerId(addressId,customer.getId());
        if (address == null)
            return new AddressResponse(false, ADDRESS_NOT_FOUND, null);
        return new AddressResponse(true, null, address);
    }
    public AddressResponse updateAddress(String email,Address address){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new AddressResponse(false,CUSTOMER_NOT_FOUND,null);
        if(!isValidAddress(address) || address.getId() == null)
            return new AddressResponse(false,INVALID_ADDRESS,null);
        Address existingAddress = this.addressDao.getAddressByIdAndCustomerId(address.getId(),customer.getId());
        if(existingAddress == null)
            return new AddressResponse(false,ADDRESS_NOT_FOUND,null);
        if(Boolean.TRUE.equals(address.getDefault()))
            this.addressDao.clearDefaultAddressesByCustomerId(customer.getId());
        int rowsAffected = this.addressDao.updateAddress(customer.getId(),address);
        if(rowsAffected == 0)
            return new AddressResponse(false,ADDRESS_UPDATE_FAILED,null);
        return new AddressResponse(true,null,address);
    }
    public BasicResponse removeAddress(String email, Long addressId){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new BasicResponse(false,CUSTOMER_NOT_FOUND);
        Address address = this.addressDao.getAddressByIdAndCustomerId(addressId,customer.getId());
        if(address == null)
            return new BasicResponse(false,ADDRESS_NOT_FOUND);
        int rowsAffected = this.addressDao.removeAddressByIdAndCustomerId(addressId,customer.getId());
        if(rowsAffected == 0)
            return new BasicResponse(false,ADDRESS_DELETE_FAILED);
        return new BasicResponse(true,null);
    }
    private boolean isValidAddress(Address address){
        if(address == null)
            return false;
        if(address.getCity() == null || address.getCity().trim().isEmpty())
            return false;
        if(address.getStreet() == null || address.getStreet().trim().isEmpty())
            return false;
        if(address.getHouseNumber() == null || address.getHouseNumber().trim().isEmpty())
            return false;
        if(address.getZipCode() == null || address.getZipCode().trim().isEmpty())
            return false;
        return true;

    }
}
