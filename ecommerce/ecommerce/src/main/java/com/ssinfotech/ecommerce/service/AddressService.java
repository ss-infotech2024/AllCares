package com.ssinfotech.ecommerce.service;

import com.ssinfotech.ecommerce.model.Address;
import com.ssinfotech.ecommerce.model.User;
import com.ssinfotech.ecommerce.repository.AddressRepository;
import com.ssinfotech.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    // Add a new address for a given user
    public Address addAddress(Long userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        address.setUser(user);
        return addressRepository.save(address);
    }

    // Get all addresses for a user
    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    // Update an address
    public Address updateAddress(Long addressId, Address newData) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        address.setFullName(newData.getFullName());
        address.setPhone(newData.getPhone());
        address.setAddressLine1(newData.getAddressLine1());
        address.setAddressLine2(newData.getAddressLine2());
        address.setCity(newData.getCity());
        address.setPincode(newData.getPincode());
        address.setState(newData.getState());
        address.setCountry(newData.getCountry());
        address.setAddressType(newData.getAddressType());
        address.setIsDefault(newData.getIsDefault());
        return addressRepository.save(address);
    }

    // Delete an address
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}

