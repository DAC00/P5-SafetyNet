package com.opcr.safetynet_alert.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "persons",
        "firestations",
        "medicalrecords"
})
public class JsonData {

    @JsonProperty("persons")
    private ArrayList<Person> persons;

    @JsonProperty("firestations")
    private ArrayList<FireStation> fireStations;

    @JsonProperty("medicalrecords")
    private ArrayList<MedicalRecord> medicalRecords;

    public ArrayList<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(ArrayList<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public ArrayList<FireStation> getFireStations() {
        return fireStations;
    }

    public void setFireStations(ArrayList<FireStation> fireStations) {
        this.fireStations = fireStations;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
}
