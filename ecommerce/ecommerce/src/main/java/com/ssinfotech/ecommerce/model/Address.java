package com.ssinfotech.ecommerce.model;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String fullName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pincode;
    private String state;
    private String country;
    private String addressType = "shipping";
    private Boolean isDefault = false;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getAddressType() {
        return addressType;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}

