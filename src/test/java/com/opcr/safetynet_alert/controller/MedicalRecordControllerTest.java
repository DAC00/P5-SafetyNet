package com.opcr.safetynet_alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.MedicalRecord;
import com.opcr.safetynet_alert.service.MedicalRecordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private JsonDataUtils jsonDataUtils;

    private static MedicalRecord medicalRecordTest;

    @BeforeAll
    static void setup() {
        medicalRecordTest = new MedicalRecord("Test", "TestName", "10/10/2010", new ArrayList<>(), new ArrayList<>());
    }

    @AfterEach
    public void cleanUp() {
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("One", "Test",
                "01/01/1981", Collections.singletonList("nillacilan"), Arrays.asList("aznol:350mg", "hydrapermazol:100mg")));
        medicalRecords.add(new MedicalRecord("Two", "Test",
                "02/02/2020", new ArrayList<>(), Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")));
        medicalRecords.add(new MedicalRecord("Three", "LeTest",
                "03/03/1933", Collections.singletonList("peanut"), new ArrayList<>()));
        medicalRecords.add(new MedicalRecord("Four", "Example",
                "04/04/1944", new ArrayList<>(), new ArrayList<>()));
        jsonDataUtils.updateMedicalRecords(medicalRecords);
    }

    @Test
    public void addMedicalRecordReturnCreated() throws Exception {
        this.mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordTest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("MedicalRecord Added."));

        jsonDataUtils.getDataFromJSON();
        Assertions.assertTrue(jsonDataUtils.getMedicalRecords().contains(medicalRecordTest));
    }

    @Test
    public void addMedicalRecordReturnBadRequestWhenAlreadyExistTest() throws Exception {
        MedicalRecord medicalRecordAlreadyExisting = new MedicalRecord("Four", "Example",
                "04/04/1944", new ArrayList<>(), new ArrayList<>());

        this.mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordAlreadyExisting)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("MedicalRecord already exist : %s".formatted(medicalRecordAlreadyExisting.toString())));
    }

    @Test
    public void deleteMedicalRecordReturnNoContent() throws Exception {
        MedicalRecord medicalRecordAToDelete = new MedicalRecord("Four", "Example",
                "04/04/1944", new ArrayList<>(), new ArrayList<>());

        this.mockMvc.perform(delete("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordAToDelete)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("MedicalRecord deleted."));

        jsonDataUtils.getDataFromJSON();
        Assertions.assertFalse(jsonDataUtils.getMedicalRecords().contains(medicalRecordAToDelete));
    }

    @Test
    public void deleteMedicalRecordReturnNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordTest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("MedicalRecord not found : %s".formatted(medicalRecordTest.toString())));
    }

    @Test
    public void updateMedicalRecordReturnOkTest() throws Exception {
        MedicalRecord medicalRecordBefore = new MedicalRecord("Four", "Example",
                "04/04/1944", new ArrayList<>(), new ArrayList<>());
        MedicalRecord medicalRecordUpdated = new MedicalRecord("Four", "Example",
                "07/07/1977", new ArrayList<>(), Arrays.asList("fish", "meat"));

        this.mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordUpdated)))
                .andExpect(status().isOk())
                .andExpect(content().string("MedicalRecord updated."));

        jsonDataUtils.getDataFromJSON();
        Optional<MedicalRecord> medicalRecordFromData = jsonDataUtils.getMedicalRecords().stream().filter(mr -> mr.getLastName().equals(medicalRecordUpdated.getLastName())
                && mr.getFirstName().equals(medicalRecordUpdated.getFirstName())).findFirst();
        medicalRecordFromData.ifPresent(mr -> Assertions.assertEquals(medicalRecordUpdated.getBirthdate(), mr.getBirthdate()));
        medicalRecordFromData.ifPresent(mr -> Assertions.assertEquals(medicalRecordUpdated.getMedications(), mr.getMedications()));
        medicalRecordFromData.ifPresent(mr -> Assertions.assertEquals(medicalRecordUpdated.getAllergies(), mr.getAllergies()));
    }

    @Test
    public void updateMedicalRecordReturnNotFoundTest() throws Exception {
        this.mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordTest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("MedicalRecord not found : %s".formatted(medicalRecordTest.toString())));
    }
}
