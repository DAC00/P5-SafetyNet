package com.opcr.safetynet_alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opcr.safetynet_alert.exceptions.MedicalRecordAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordNotFoundException;
import com.opcr.safetynet_alert.model.MedicalRecord;
import com.opcr.safetynet_alert.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    private static MedicalRecord medicalRecord;

    @BeforeAll
    static void setup() {
        medicalRecord = new MedicalRecord("Test", "TestName", "10/10/2010", new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void addMedicalRecordReturnCreated() throws Exception {
        this.mockMvc.perform(put("/medicalRecord").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isCreated())
                .andExpect(content().string("MedicalRecord Added."));
    }

    @Test
    public void addMedicalRecordReturnBadRequestWhenAlreadyExistTest() throws Exception {
        doThrow(new MedicalRecordAlreadyExistException(medicalRecord.toString()))
                .when(medicalRecordService).addMedicalRecord(medicalRecord);

        this.mockMvc.perform(put("/medicalRecord").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("MedicalRecord already exist : %s".formatted(medicalRecord.toString())));
    }

    @Test
    public void deleteMedicalRecordReturnNoContent() throws Exception {
        this.mockMvc.perform(delete("/medicalRecord").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("MedicalRecord deleted."));
    }

    @Test
    public void deleteMedicalRecordReturnNotFoundTest() throws Exception {
        doThrow(new MedicalRecordNotFoundException(medicalRecord.toString()))
                .when(medicalRecordService).deleteMedicalRecord(medicalRecord);

        this.mockMvc.perform(delete("/medicalRecord").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("MedicalRecord not found : %s".formatted(medicalRecord.toString())));
    }

    @Test
    public void updateMedicalRecordReturnOkTest() throws Exception {
        this.mockMvc.perform(post("/medicalRecord").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string("MedicalRecord updated."));
    }

    @Test
    public void updateMedicalRecordReturnNotFoundTest() throws Exception {
        doThrow(new MedicalRecordNotFoundException(medicalRecord.toString()))
                .when(medicalRecordService).updateMedicalRecord(medicalRecord);

        this.mockMvc.perform(post("/medicalRecord").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("MedicalRecord not found : %s".formatted(medicalRecord.toString())));
    }
}
