package com.opcr.safetynet_alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opcr.safetynet_alert.exceptions.FireStationAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.FireStationNotFoundException;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.service.FireStationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private static FireStation fireStation;

    @BeforeAll
    static void setup() {
        fireStation = new FireStation("TEST",1);
    }

    @Test
    public void addFireStationReturnCreatedTest() throws Exception {
        this.mockMvc.perform(put("/firestation").contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(fireStation)))
                .andExpect(status().isCreated())
                .andExpect(content().string("FireStation added."));
    }

    @Test
    public void addFireStationReturnBadRequestWhenAlreadyExistTest() throws Exception {
        doThrow(new FireStationAlreadyExistException(fireStation.toString()))
                .when(fireStationService).addFireStation(fireStation);

        this.mockMvc.perform(put("/firestation").contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(fireStation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("FireStation already exist : %s".formatted(fireStation.toString())));
    }

    @Test
    public void deleteFireStationReturnNoContent() throws Exception {
        this.mockMvc.perform(delete("/firestation").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(fireStation)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("FireStation deleted."));
    }

    @Test
    public void deleteFireStationReturnNotFoundTest() throws Exception {
        doThrow(new FireStationNotFoundException(fireStation.toString()))
                .when(fireStationService).deleteFireStation(fireStation);

        this.mockMvc.perform(delete("/firestation").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(fireStation)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("FireStation not found : %s".formatted(fireStation.toString())));
    }

    @Test
    public void updateFireStationReturnOkTest() throws Exception {
        this.mockMvc.perform(post("/firestation").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(fireStation)))
                .andExpect(status().isOk())
                .andExpect(content().string("FireStation updated."));
    }

    @Test
    public void updateFireStationReturnNotFoundTest() throws Exception {
        doThrow(new FireStationNotFoundException(fireStation.toString()))
                .when(fireStationService).updateFireStation(fireStation);

        this.mockMvc.perform(post("/firestation").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(fireStation)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("FireStation not found : %s".formatted(fireStation.toString())));
    }
}
