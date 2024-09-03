package com.opcr.safetynet_alert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.model.JsonData;
import com.opcr.safetynet_alert.model.MedicalRecord;
import com.opcr.safetynet_alert.model.Person;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JsonDataUtils {

    private final String pathJSON = "src/main/resources/data.json";
    private JsonData jsonData;

    public JsonDataUtils(){
        this.jsonData = null;
        getDataFromJSON();

        System.out.println("Persons : " + jsonData.getPersons().size());
        System.out.println("Stations : " + jsonData.getFireStations().size());
        System.out.println("MedicalRecords : " + jsonData.getMedicalRecords().size());
    }

    private void getDataFromJSON(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonData = mapper.readValue(new File(pathJSON), JsonData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateJsonData(JsonData jsonDataToDate){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(pathJSON),jsonDataToDate);
            System.out.println(pathJSON + " : Updated");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePersons(ArrayList<Person> persons){
        JsonData jsonDataUpdated = jsonData;
        jsonDataUpdated.setPersons(persons);
        updateJsonData(jsonDataUpdated);
    }

    public void updateFireStations(ArrayList<FireStation> fireStations){
        JsonData jsonDataUpdated = jsonData;
        jsonDataUpdated.setFireStations(fireStations);
        updateJsonData(jsonDataUpdated);
    }

    public void updateMedicalRecords(ArrayList<MedicalRecord> medicalRecords){
        JsonData jsonDataUpdated = jsonData;
        jsonDataUpdated.setMedicalRecords(medicalRecords);
        updateJsonData(jsonDataUpdated);
    }

    public ArrayList<Person> getPersons(){
        return jsonData.getPersons();
    }

    public ArrayList<MedicalRecord> getMedicalRecords(){
        return jsonData.getMedicalRecords();
    }

    public ArrayList<FireStation> getFireStations(){
        return jsonData.getFireStations();
    }
}
