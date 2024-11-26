package com.opcr.safetynet_alert.model;

import java.util.List;

public class HouseAndFireStationNumberFromAddress {

    private int fireStationNumber;

    private List<PersonRecordPhone> persons;

    public HouseAndFireStationNumberFromAddress(int fireStationNumber, List<PersonRecordPhone> persons) {
        this.fireStationNumber = fireStationNumber;
        this.persons = persons;
    }

    public int getFireStationNumber() {
        return fireStationNumber;
    }

    public void setFireStationNumber(int fireStationNumber) {
        this.fireStationNumber = fireStationNumber;
    }

    public List<PersonRecordPhone> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonRecordPhone> persons) {
        this.persons = persons;
    }
}
