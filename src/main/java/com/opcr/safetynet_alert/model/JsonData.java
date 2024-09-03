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

    @JsonProperty("medicalrecords")
    public ArrayList<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    @JsonProperty("medicalrecords")
    public void setMedicalRecords(ArrayList<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    @JsonProperty("firestations")
    public ArrayList<FireStation> getFireStations() {
        return fireStations;
    }

    @JsonProperty("firestations")
    public void setFireStations(ArrayList<FireStation> fireStations) {
        this.fireStations = fireStations;
    }

    @JsonProperty("persons")
    public ArrayList<Person> getPersons() {
        return persons;
    }

    @JsonProperty("persons")
    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
}
