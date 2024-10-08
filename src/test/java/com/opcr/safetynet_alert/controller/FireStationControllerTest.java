package com.opcr.safetynet_alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.service.FireStationService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private JsonDataUtils jsonDataUtils;

    private static FireStation fireStationTest;

    @BeforeAll
    static void setUp() {
        fireStationTest = new FireStation("TEST", 1);
    }

    @AfterEach
    public void cleanUp() {
        ArrayList<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("11 Test St", 1));
        fireStations.add(new FireStation("22 Test St", 2));
        fireStations.add(new FireStation("33 Test St", 2));
        jsonDataUtils.updateFireStations(fireStations);
    }

    @Test
    public void addFireStationReturnCreatedTest() throws Exception {
        this.mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fireStationTest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("FireStation added."));

        jsonDataUtils.getDataFromJSON();
        Assertions.assertTrue(jsonDataUtils.getFireStations().contains(fireStationTest));
    }

    @Test
    public void addFireStationReturnBadRequestWhenAlreadyExistTest() throws Exception {
        FireStation fireStationAlreadyExisting = new FireStation("11 Test St", 1);

        this.mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fireStationAlreadyExisting)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("FireStation already exist : %s".formatted(fireStationAlreadyExisting.toString())));
    }

    @Test
    public void deleteFireStationReturnNoContent() throws Exception {
        FireStation fireStationToDelete = new FireStation("33 Test St", 2);

        this.mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fireStationToDelete)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("FireStation deleted."));

        jsonDataUtils.getDataFromJSON();
        Assertions.assertFalse(jsonDataUtils.getFireStations().contains(fireStationToDelete));
    }

    @Test
    public void deleteFireStationReturnNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fireStationTest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("FireStation not found : %s".formatted(fireStationTest.toString())));
    }

    @Test
    public void updateFireStationReturnOkTest() throws Exception {
        FireStation fireStationBefore = new FireStation("33 Test St", 2);
        FireStation fireStationUpdated = new FireStation("33 Test St", 99);

        this.mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fireStationUpdated)))
                .andExpect(status().isOk())
                .andExpect(content().string("FireStation updated."));

        jsonDataUtils.getDataFromJSON();
        Assertions.assertFalse(jsonDataUtils.getFireStations().contains(fireStationBefore));
        Assertions.assertTrue(jsonDataUtils.getFireStations().contains(fireStationUpdated));
    }

    @Test
    public void updateFireStationReturnNotFoundTest() throws Exception {
        this.mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fireStationTest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("FireStation not found : %s".formatted(fireStationTest.toString())));
    }
}
