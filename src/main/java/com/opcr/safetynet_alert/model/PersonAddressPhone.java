package com.opcr.safetynet_alert.model;

public class PersonAddressPhone extends PersonSimple {

    private String address;

    private String phone;

    public PersonAddressPhone(String firstName, String lastName, String address, String phone) {
        super(firstName, lastName);
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
