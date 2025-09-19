package com.ssinfotech.ecommerce.controller;

import com.ssinfotech.ecommerce.model.Address;
import com.ssinfotech.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Add an address for a specific user
    @PostMapping("/user/{userId}")
    public Address addAddress(@PathVariable Long userId, @RequestBody Address address) {
        return addressService.addAddress(userId, address);
    }

    // Get all addresses for a user
    @GetMapping("/user/{userId}")
    public List<Address> getUserAddresses(@PathVariable Long userId) {
        return addressService.getAddressesByUserId(userId);
    }

    // Update an existing address
    @PutMapping("/{addressId}")
    public Address updateAddress(
            @PathVariable Long addressId,
            @RequestBody Address newData) {
        return addressService.updateAddress(addressId, newData);
    }

    // Delete an address by id
    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
    }
}
