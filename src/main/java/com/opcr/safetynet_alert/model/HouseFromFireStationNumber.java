package com.opcr.safetynet_alert.model;

import java.util.List;

public class HouseFromFireStationNumber {

    private String address;

    private List<PersonRecordPhone> persons;

    public HouseFromFireStationNumber(String address, List<PersonRecordPhone> persons) {
        this.address = address;
        this.persons = persons;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonRecordPhone> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonRecordPhone> persons) {
        this.persons = persons;
    }
}
