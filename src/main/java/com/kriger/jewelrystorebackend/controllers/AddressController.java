package com.kriger.jewelrystorebackend.controllers;

import com.kriger.jewelrystorebackend.models.Address;
import com.kriger.jewelrystorebackend.responses.AddressListResponse;
import com.kriger.jewelrystorebackend.responses.AddressResponse;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.services.AddressService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PostMapping
    public AddressResponse addAddress(Authentication authentication,@RequestBody Address address){
        String email = authentication.getName();
        return this.addressService.addAddress(email,address);
    }

    @GetMapping
    public AddressListResponse getAllAddresses(Authentication authentication){
        String email = authentication.getName();
        return this.addressService.getAllAddresses(email);
    }

    @GetMapping("/{addressId}")
    public AddressResponse getAddressById(Authentication authentication,@PathVariable Long addressId){
        String email = authentication.getName();
        return this.addressService.getAddressById(email,addressId);
    }

    @PutMapping("/{addressId}")
    public AddressResponse updateAddress(Authentication authentication,@PathVariable Long addressId,@RequestBody Address address){
        String email = authentication.getName();
        address.setId(addressId);
        return this.addressService.updateAddress(email,address);
    }

    @DeleteMapping("/{addressId}")
    public BasicResponse removeAddress(Authentication authentication,@PathVariable Long addressId){
        String email = authentication.getName();
        return this.addressService.removeAddress(email,addressId);
    }
}