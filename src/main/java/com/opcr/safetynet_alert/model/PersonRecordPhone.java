package com.opcr.safetynet_alert.model;

import java.util.List;

public class PersonRecordPhone extends PersonSimple{

    private int age;

    private String phone;

    private List<String> allergies;

    private List<String> medications;

    public PersonRecordPhone(String firstName, String lastName, int age, String phone, List<String> allergies, List<String> medications) {
        super(firstName, lastName);
        this.age = age;
        this.phone = phone;
        this.allergies = allergies;
        this.medications = medications;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
