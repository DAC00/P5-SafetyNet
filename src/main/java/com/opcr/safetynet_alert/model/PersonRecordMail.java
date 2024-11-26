package com.opcr.safetynet_alert.model;

import java.util.List;

public class PersonRecordMail extends PersonSimple {

    private String address;

    private String mail;

    private int age;

    private List<String> allergies;

    private List<String> medications;

    public PersonRecordMail(String firstName, String lastName, String address, int age, List<String> allergies, List<String> medications, String mail) {
        super(firstName, lastName);
        this.address = address;
        this.age = age;
        this.allergies = allergies;
        this.medications = medications;
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
