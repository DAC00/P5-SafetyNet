package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InformationService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    /**
     * (1) Return the list of people covered by the FireStation stationNumber.
     * The return must contain : lastName, firstName, address and phone number of each person
     * and a sum of child and adult.
     *
     * @param stationNumber the number of the station.
     * @return FireStationCoverage or null.
     **/
    public FireStationCoverage findPersonsFromFireStationNumber(int stationNumber) {

        int adultCount = 0;
        int childCount = 0;
        List<PersonAddressPhone> list = new ArrayList<>();

        for (FireStation f : jsonDataUtils.getFireStations().stream().filter(f -> f.getStation() == stationNumber).toList()) {
            for (Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(f.getAddress())).toList()) {

                list.add(new PersonAddressPhone(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone()));

                for (MedicalRecord mr : jsonDataUtils.getMedicalRecords().stream().filter(mr -> mr.getFirstName().equals(p.getFirstName())
                        && mr.getLastName().equals(p.getLastName())).toList()) {
                    if (getAgeFromMedicalRecord(mr.getBirthdate()) >= 18) {
                        adultCount++;
                    } else {
                        childCount++;
                    }
                }
            }
        }
        if (list.isEmpty()) {
            return null;
        } else {
            return new FireStationCoverage(adultCount, childCount, list);
        }
    }

    /**
     * (2) Return the list of children who live at the address.
     * The return must contain : lastName, firstName and age of each child.
     * And the lastName and firstName of the other people living at the same address.
     *
     * @param address the address to search.
     * @return a ChildFromAddress object as a result or null.
     **/
    public ChildFromAddress findChildFromAddress(String address) {
        List<Child> childList = new ArrayList<>();
        List<PersonSimple> adultList = new ArrayList<>();

        for (Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(address)).toList()) {
            MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getLastName().equals(p.getLastName())
                    && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);
            if (medRecFind != null) {
                if (getAgeFromMedicalRecord(medRecFind.getBirthdate()) < 18) {
                    childList.add(new Child(p.getFirstName(), p.getLastName(), getAgeFromMedicalRecord(medRecFind.getBirthdate())));
                } else {
                    adultList.add(new PersonSimple(p.getFirstName(), p.getLastName()));
                }
            }
        }
        if (childList.isEmpty()) {
            return null;
        } else {
            return new ChildFromAddress(childList, adultList);
        }
    }

    /**
     * (3) Return the list of phone numbers of residents served by the FireStation fireStationNumber.
     *
     * @param fireStationNumber the FireStation number.
     * @return a List of phone number.
     **/
    public List<String> findPhoneNumberOfPersonFromFireStation(int fireStationNumber) {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        for (FireStation f : jsonDataUtils.getFireStations().stream().filter(f -> f.getStation() == fireStationNumber).toList()) {
            for (Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(f.getAddress())).toList()) {
                phoneNumbers.add(p.getPhone());
            }
        }
        return phoneNumbers;
    }

    /**
     * (4) Return the list of person living at the given address as well as the number of the FireStation.
     * The return must contain : lastName, firstName, phone number, age and the MedicalRecord for each person.
     *
     * @param address the address to search.
     * @return HouseAndFireStationNumberFromAddress or null.
     **/
    public HouseAndFireStationNumberFromAddress findPersonAndFireStationNumberFromAddress(String address) {
        Optional<FireStation> fireStation = jsonDataUtils.getFireStations().stream().filter(f -> f.getAddress().equals(address)).findFirst();
        List<Person> personArrayList = jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(address)).toList();
        List<PersonRecordPhone> listPersonRecordPhone = new ArrayList<>();

        if (!personArrayList.isEmpty()) {
            for (Person p : personArrayList) {

                MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getLastName().equals(p.getLastName())
                        && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);

                List<String> listMedications = new ArrayList<>();
                List<String> listAllergies = new ArrayList<>();
                int age = 0;

                if (medRecFind != null) {
                    age = getAgeFromMedicalRecord(medRecFind.getBirthdate());
                    listMedications.addAll(medRecFind.getMedications());
                    listAllergies.addAll(medRecFind.getAllergies());
                }
                listPersonRecordPhone.add(new PersonRecordPhone(p.getFirstName(), p.getLastName(), age, p.getPhone(), listAllergies, listMedications));
            }
        }
        if (listPersonRecordPhone.isEmpty()) {
            return null;
        } else {
            return new HouseAndFireStationNumberFromAddress(fireStation.map(FireStation::getStation).orElse(0), listPersonRecordPhone);
        }
    }

    /**
     * (5) Return a list of all the households served by the FireStation stationNumbers. This list must group the person by address.
     * The return must contain : lastName, firstName, phone number, age and the MedicalRecord for each person.
     *
     * @param stationNumbers the List stationNumber.
     * @return a list of HouseFromFireStationNumber, could be empty.
     **/
    public List<HouseFromFireStationNumber> findPersonFromFireStationNumber(List<Integer> stationNumbers) {

        List<HouseFromFireStationNumber> houseList = new ArrayList<>();

        for (FireStation fireStation : jsonDataUtils.getFireStations().stream().filter(fireStation -> stationNumbers.contains(fireStation.getStation())).toList()) {

            List<Person> personArrayList = jsonDataUtils.getPersons().stream().filter(person -> fireStation.getAddress().equals(person.getAddress())).toList();
            List<PersonRecordPhone> listPersonRecordPhone = new ArrayList<>();

            if (!personArrayList.isEmpty()) {
                for (Person person : personArrayList) {

                    MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getLastName().equals(person.getLastName())
                            && medicalRecord.getFirstName().equals(person.getFirstName())).findFirst().orElse(null);

                    List<String> listMedications = new ArrayList<>();
                    List<String> listAllergies = new ArrayList<>();
                    int age = 0;

                    if (medRecFind != null) {
                        age = getAgeFromMedicalRecord(medRecFind.getBirthdate());
                        listMedications.addAll(medRecFind.getMedications());
                        listAllergies.addAll(medRecFind.getAllergies());
                    }
                    listPersonRecordPhone.add(new PersonRecordPhone(person.getFirstName(), person.getLastName(), age, person.getPhone(), listAllergies, listMedications));
                }
                houseList.add(new HouseFromFireStationNumber(fireStation.getAddress(), listPersonRecordPhone));
            }
        }
        return houseList;
    }

    /**
     * (6) Return the list of person where the lastName is equal to the param lastName.
     * The return must contain : lastName, firstName, email, age and the MedicalRecord for each person.
     *
     * @param lastName the lastName to search.
     * @return a list of PersonRecordMail, could be empty.
     **/
    public List<PersonRecordMail> findPersonFromLastName(String lastName) {

        List<PersonRecordMail> listAdultRecordMail = new ArrayList<>();

        List<Person> personArrayList = jsonDataUtils.getPersons().stream().filter(p -> p.getLastName().equals(lastName)).toList();

        if (!personArrayList.isEmpty()) {
            for (Person p : personArrayList) {

                MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getLastName().equals(p.getLastName())
                        && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);

                List<String> listAllergies = new ArrayList<>();
                List<String> listMedications = new ArrayList<>();
                int age = 0;

                if (medRecFind != null) {
                    age = getAgeFromMedicalRecord(medRecFind.getBirthdate());
                    listMedications.addAll(medRecFind.getMedications());
                    listAllergies.addAll(medRecFind.getAllergies());
                }
                listAdultRecordMail.add(new PersonRecordMail(p.getFirstName(), p.getLastName(), p.getAddress(), age, listAllergies, listMedications, p.getEmail()));
            }
        }
        return listAdultRecordMail;
    }

    /**
     * (7) Return the list of email of the person living in the city.
     *
     * @param city where to find the email.
     * @return a List of email.
     **/
    public List<String> findEmailFromCity(String city) {
        return jsonDataUtils.getPersons().stream().filter(person -> person.getCity().equals(city)).map(Person::getEmail).toList();
    }

    /**
     * Return the age of a Person.
     *
     * @param birthdate of a Person in String.
     * @return the age of a Person in int.
     **/
    private int getAgeFromMedicalRecord(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears();
    }
}
