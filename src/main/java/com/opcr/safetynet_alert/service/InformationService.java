package com.opcr.safetynet_alert.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.model.MedicalRecord;
import com.opcr.safetynet_alert.model.Person;
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
     * @return an ObjectNode of the result could be empty.
     **/
    public ObjectNode findPersonsFromFireStationNumber(int stationNumber) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        ArrayNode listPersonNode = mapper.createArrayNode();
        int children = 0;
        int adults = 0;
        for (FireStation f : jsonDataUtils.getFireStations().stream().filter(f -> f.getStation() == stationNumber).toList()) {
            for (Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(f.getAddress())).toList()) {
                ObjectNode personNode = mapper.createObjectNode();
                personNode.put("lastName", p.getLastName());
                personNode.put("firstName", p.getFirstName());
                personNode.put("address", p.getAddress());
                personNode.put("phone", p.getPhone());
                for (MedicalRecord mr : jsonDataUtils.getMedicalRecords().stream().filter(mr -> mr.getFirstName().equals(p.getFirstName())
                        && mr.getLastName().equals(p.getLastName())).toList()) {
                    if (getAgeFromMedicalRecord(mr.getBirthdate()) >= 18) {
                        adults++;
                    } else {
                        children++;
                    }
                }
                listPersonNode.add(personNode);
            }
        }

        if (!listPersonNode.isEmpty()) {
            responseJson.put("station", stationNumber);
            responseJson.put("adults", adults);
            responseJson.put("children", children);
            responseJson.putArray("persons").addAll(listPersonNode);
        }
        return responseJson;
    }

    /**
     * (2) Return the list of children who live at the address.
     * The return must contain : lastName, firstName and age of each child.
     * And the lastName and firstName of the other people living at the same address.
     *
     * @param address the address to search.
     * @return an ObjectNode of the result, could be empty.
     **/
    public ObjectNode findChildFromAddress(String address) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        ArrayNode listChildren = mapper.createArrayNode();
        ArrayNode listAdults = mapper.createArrayNode();
        for (Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(address)).toList()) {
            ObjectNode personNode = mapper.createObjectNode();
            MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getLastName().equals(p.getLastName())
                    && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);
            if (medRecFind != null) {
                if (getAgeFromMedicalRecord(medRecFind.getBirthdate()) < 18) {
                    personNode.put("lastName", p.getLastName());
                    personNode.put("firstName", p.getFirstName());
                    personNode.put("age", getAgeFromMedicalRecord(medRecFind.getBirthdate()));
                    listChildren.add(personNode);
                } else {
                    personNode.put("lastName", p.getLastName());
                    personNode.put("firstName", p.getFirstName());
                    listAdults.add(personNode);
                }
            }
        }
        if (!listChildren.isEmpty()) {
            responseJson.putArray("children").addAll(listChildren);
            responseJson.putArray("adults").addAll(listAdults);
        }
        return responseJson;
    }

    /**
     * (3) Return the list of phone numbers of residents served by the FireStation fireStationNumber.
     *
     * @param fireStationNumber the FireStation number.
     * @return a List of phone number.
     **/
    public ArrayList<String> findPhoneNumberOfPersonFromFireStation(int fireStationNumber) {
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
     * @return an objectNode of the result, could be empty.
     **/
    public ObjectNode findPersonAndFireStationNumberFromAddress(String address) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        ArrayNode listPersonNode = mapper.createArrayNode();

        Optional<FireStation> fireStation = jsonDataUtils.getFireStations().stream().filter(f -> f.getAddress().equals(address)).findFirst();

        List<Person> personArrayList = jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(address)).toList();

        if (!personArrayList.isEmpty()) {
            for (Person p : personArrayList) {

                MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getLastName().equals(p.getLastName())
                        && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);

                ObjectNode personNode = mapper.createObjectNode();
                personNode.put("lastName", p.getLastName());
                personNode.put("firstName", p.getFirstName());
                personNode.put("phone", p.getPhone());

                if (medRecFind != null) {
                    personNode.put("age", getAgeFromMedicalRecord(medRecFind.getBirthdate()));
                    ArrayNode listMedications = mapper.createArrayNode();
                    ArrayNode listAllergies = mapper.createArrayNode();
                    for (String med : medRecFind.getMedications()) {
                        listMedications.add(med);
                    }
                    for (String allergies : medRecFind.getAllergies()) {
                        listAllergies.add(allergies);
                    }
                    personNode.putArray("medications").addAll(listMedications);
                    personNode.putArray("allergies").addAll(listAllergies);
                }
                listPersonNode.add(personNode);
            }
            responseJson.put("stations", fireStation.map(FireStation::getStation).orElse(0));
            responseJson.putArray("persons").addAll(listPersonNode);
        }
        return responseJson;
    }

    /**
     * (5) Return a list of all the households served by the FireStation stationNumbers. This list must group the person by address.
     * The return must contain : lastName, firstName, phone number, age and the MedicalRecord for each person.
     *
     * @param stationNumbers the List stationNumber.
     * @return an objectNode of the result, could be empty.
     **/
    public ArrayNode findPersonFromFireStationNumber(List<Integer> stationNumbers) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode responseJson = mapper.createArrayNode();
        for (FireStation fireStation : jsonDataUtils.getFireStations().stream().filter(fireStation -> stationNumbers.contains(fireStation.getStation())).toList()) {
            ObjectNode addressNode = mapper.createObjectNode();
            ArrayNode listPersonNode = mapper.createArrayNode();

            List<Person> personArrayList = jsonDataUtils.getPersons().stream().filter(person -> fireStation.getAddress().equals(person.getAddress())).toList();

            if (!personArrayList.isEmpty()) {
                for (Person person : personArrayList) {

                    MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getLastName().equals(person.getLastName())
                            && medicalRecord.getFirstName().equals(person.getFirstName())).findFirst().orElse(null);

                    ObjectNode personNode = mapper.createObjectNode();
                    personNode.put("lastName", person.getLastName());
                    personNode.put("firstName", person.getFirstName());
                    personNode.put("phone", person.getPhone());

                    if (medRecFind != null) {
                        personNode.put("age", getAgeFromMedicalRecord(medRecFind.getBirthdate()));
                        ArrayNode listMedications = mapper.createArrayNode();
                        ArrayNode listAllergies = mapper.createArrayNode();
                        for (String med : medRecFind.getMedications()) {
                            listMedications.add(med);
                        }
                        for (String allergies : medRecFind.getAllergies()) {
                            listAllergies.add(allergies);
                        }
                        personNode.putArray("medications").addAll(listMedications);
                        personNode.putArray("allergies").addAll(listAllergies);
                    }
                    listPersonNode.add(personNode);
                }
                addressNode.putArray(fireStation.getAddress()).addAll(listPersonNode);
                responseJson.add(addressNode);
            }
        }
        return responseJson;
    }

    /**
     * (6) Return the list of person where the lastName is equal to the param lastName.
     * The return must contain : lastName, firstName, email, age and the MedicalRecord for each person.
     *
     * @param lastName the lastName to search.
     * @return an objectNode of the result, could be empty.
     **/
    public ObjectNode findPersonFromLastName(String lastName) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        ArrayNode listPersonNode = mapper.createArrayNode();

        List<Person> personArrayList = jsonDataUtils.getPersons().stream().filter(p -> p.getLastName().equals(lastName)).toList();

        if (!personArrayList.isEmpty()) {
            for (Person p : personArrayList) {

                MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord -> medicalRecord.getLastName().equals(p.getLastName())
                        && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);

                ObjectNode personNode = mapper.createObjectNode();
                personNode.put("lastName", p.getLastName());
                personNode.put("firstName", p.getFirstName());
                personNode.put("email", p.getEmail());

                if (medRecFind != null) {
                    personNode.put("age", getAgeFromMedicalRecord(medRecFind.getBirthdate()));
                    ArrayNode listMedications = mapper.createArrayNode();
                    ArrayNode listAllergies = mapper.createArrayNode();
                    for (String med : medRecFind.getMedications()) {
                        listMedications.add(med);
                    }
                    for (String allergies : medRecFind.getAllergies()) {
                        listAllergies.add(allergies);
                    }
                    personNode.putArray("medications").addAll(listMedications);
                    personNode.putArray("allergies").addAll(listAllergies);
                }
                listPersonNode.add(personNode);
            }
            responseJson.putArray("persons").addAll(listPersonNode);
        }
        return responseJson;
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
