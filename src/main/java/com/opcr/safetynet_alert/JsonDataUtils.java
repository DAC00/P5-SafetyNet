package com.opcr.safetynet_alert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.model.JsonData;
import com.opcr.safetynet_alert.model.MedicalRecord;
import com.opcr.safetynet_alert.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JsonDataUtils {

    private static final Logger logger = LogManager.getLogger(JsonDataUtils.class);
    private String filePath;
    private JsonData jsonData;

    @Autowired
    public JsonDataUtils(Environment environment) {
        this.filePath = environment.getProperty("json.filepath");
        this.jsonData = null;

        getDataFromJSON();

        logger.info("File : %s.".formatted(filePath));
        logger.info("Persons : %s.".formatted(jsonData.getPersons().size()));
        logger.info("Stations : %s.".formatted(jsonData.getFireStations().size()));
        logger.info("MedicalRecords : %s.".formatted(jsonData.getMedicalRecords().size()));
    }

    /**
     * Read the data from the file and update the JsonData Object.
     **/
    public void getDataFromJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonData = mapper.readValue(new File(filePath), JsonData.class);
            logger.debug("Data loaded from : %s.".formatted(filePath));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the file with a JsonData Object.
     *
     * @param jsonDataToDate to write in the file.
     **/
    private void updateJsonData(JsonData jsonDataToDate) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), jsonDataToDate);
            logger.debug("Data updated : %s".formatted(filePath));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the List of Person in jsonData.
     *
     * @param persons the list of Person to update.
     **/
    public void updatePersons(ArrayList<Person> persons) {
        JsonData jsonDataUpdated = jsonData;
        jsonDataUpdated.setPersons(persons);
        updateJsonData(jsonDataUpdated);
    }

    /**
     * Update the List of FireStation in jsonData.
     *
     * @param fireStations the list of FireStation to update.
     **/
    public void updateFireStations(ArrayList<FireStation> fireStations) {
        JsonData jsonDataUpdated = jsonData;
        jsonDataUpdated.setFireStations(fireStations);
        updateJsonData(jsonDataUpdated);
    }

    /**
     * Update the List of MedicalRecord in jsonData.
     *
     * @param medicalRecords the list of MedicalRecord to update.
     **/
    public void updateMedicalRecords(ArrayList<MedicalRecord> medicalRecords) {
        JsonData jsonDataUpdated = jsonData;
        jsonDataUpdated.setMedicalRecords(medicalRecords);
        updateJsonData(jsonDataUpdated);
    }

    public ArrayList<Person> getPersons() {
        return jsonData.getPersons();
    }

    public ArrayList<MedicalRecord> getMedicalRecords() {
        return jsonData.getMedicalRecords();
    }

    public ArrayList<FireStation> getFireStations() {
        return jsonData.getFireStations();
    }
}
