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

import java.util.ArrayList;

@Service
public class InformationService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    // 1
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
                    if (mr.getAge() >= 18) {
                        adults++;
                    } else {
                        children++;
                    }
                }
                listPersonNode.add(personNode);
            }
        }
        responseJson.put("station", stationNumber);
        responseJson.put("adults", adults);
        responseJson.put("children", children);
        responseJson.putArray("persons").addAll(listPersonNode);
        return responseJson;
    }

    // 2
    public ObjectNode findChildFromAddress(String address) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        ArrayNode listChildren = mapper.createArrayNode();
        ArrayNode listAdults = mapper.createArrayNode();
        for(Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(address)).toList()){
            ObjectNode personNode = mapper.createObjectNode();
            MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord-> medicalRecord.getLastName().equals(p.getLastName())
                    && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);
            if (medRecFind != null){
                if(medRecFind.getAge()<18){
                    personNode.put("lastName", p.getLastName());
                    personNode.put("firstName", p.getFirstName());
                    personNode.put("age", medRecFind.getAge());
                    listChildren.add(personNode);
                }else {
                    personNode.put("lastName", p.getLastName());
                    personNode.put("firstName", p.getFirstName());
                    listAdults.add(personNode);
                }
            }
        }
        if(!listChildren.isEmpty()){
            responseJson.putArray("children").addAll(listChildren);
            responseJson.putArray("adults").addAll(listAdults);
        }
        return responseJson;
    }

    // 3
    public ArrayList<String> findPhoneNumberOfPersonFromFireStation(int fireStationNumber) {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        for (FireStation f : jsonDataUtils.getFireStations().stream().filter(f -> f.getStation() == fireStationNumber).toList()) {
            for (Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(f.getAddress())).toList()) {
                phoneNumbers.add(p.getPhone());
            }
        }
        return phoneNumbers;
    }

    // 4
    public ObjectNode findPersonAndFireStationNumberFromAddress(String address) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        ArrayNode listPersonNode = mapper.createArrayNode();
        ArrayNode listStationNumber = mapper.createArrayNode();

        for(FireStation f : jsonDataUtils.getFireStations().stream().filter(fireStation -> fireStation.getAddress().equals(address)).toList()){
            listStationNumber.add(f.getStation());
        }
        responseJson.putArray("stations").addAll(listStationNumber);

        for(Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getAddress().equals(address)).toList()){

            MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord-> medicalRecord.getLastName().equals(p.getLastName())
                    && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);

            ObjectNode personNode = mapper.createObjectNode();
            personNode.put("lastName",p.getLastName());
            personNode.put("firstName",p.getFirstName());
            personNode.put("phone",p.getPhone());

            if (medRecFind != null){
                personNode.put("age",medRecFind.getAge());
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
        return responseJson;
    }

    // 5
    public ArrayNode findPersonFromFireStationNumber(int stationNumbers) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode responseJson = mapper.createArrayNode();
        for(FireStation fireStation : jsonDataUtils.getFireStations().stream().filter(fireStation -> stationNumbers == fireStation.getStation()).toList()){
            ObjectNode addressNode = mapper.createObjectNode();
            ArrayNode listPersonNode = mapper.createArrayNode();
            for(Person person : jsonDataUtils.getPersons().stream().filter(person -> fireStation.getAddress().equals(person.getAddress())).toList()){

                MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord-> medicalRecord.getLastName().equals(person.getLastName())
                        && medicalRecord.getFirstName().equals(person.getFirstName())).findFirst().orElse(null);

                ObjectNode personNode = mapper.createObjectNode();
                personNode.put("lastName",person.getLastName());
                personNode.put("firstName",person.getFirstName());
                personNode.put("phone",person.getPhone());

                if (medRecFind != null){
                    personNode.put("age",medRecFind.getAge());
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
        return responseJson;
    }

    // 6
    public ObjectNode findPersonFromLastName(String lastName) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        ArrayNode listPersonNode = mapper.createArrayNode();

        for(Person p : jsonDataUtils.getPersons().stream().filter(p -> p.getLastName().equals(lastName)).toList()){

            MedicalRecord medRecFind = jsonDataUtils.getMedicalRecords().stream().filter(medicalRecord-> medicalRecord.getLastName().equals(p.getLastName())
                    && medicalRecord.getFirstName().equals(p.getFirstName())).findFirst().orElse(null);

            ObjectNode personNode = mapper.createObjectNode();
            personNode.put("lastName",p.getLastName());
            personNode.put("firstName",p.getFirstName());
            personNode.put("email", p.getEmail());

            if (medRecFind != null){
                personNode.put("age",medRecFind.getAge());
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
        return responseJson;
    }

    // 7
    public ArrayList<String> findEmailFromCity(String city) {
        ArrayList<String> emailFound = new ArrayList<>();
        for (Person person : jsonDataUtils.getPersons().stream().filter(person -> person.getCity().equals(city)).toList()) {
            emailFound.add(person.getEmail());
        }
        return emailFound;
    }
}
