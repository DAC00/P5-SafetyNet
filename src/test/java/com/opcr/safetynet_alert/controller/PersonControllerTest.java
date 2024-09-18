package com.opcr.safetynet_alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opcr.safetynet_alert.exceptions.MedicalRecordAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordNotFoundException;
import com.opcr.safetynet_alert.exceptions.PersonAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.PersonNotFoundException;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.model.Person;
import com.opcr.safetynet_alert.service.FireStationService;
import com.opcr.safetynet_alert.service.PersonService;
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

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private static Person person;

    @BeforeAll
    static void setup() {
        person = new Person("Test0","Test1","Test2","Test3","000","111","Test6@mail.com");
    }

    @Test
    public void addPersonReturnCreatedTest() throws Exception {
        this.mockMvc.perform(put("/person").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person added."));
    }

    @Test
    public void addPersonReturnBadRequestWhenAlreadyExistTest() throws Exception {
        doThrow(new PersonAlreadyExistException(person.toString()))
                .when(personService).addPerson(person);

        this.mockMvc.perform(put("/person").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Person already exist : %s".formatted(person.toString())));
    }

    @Test
    public void deletePersonReturnNoContent() throws Exception {
        this.mockMvc.perform(delete("/person").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Person deleted."));
    }

    @Test
    public void deletePersonReturnNotFoundTest() throws Exception {
        doThrow(new PersonNotFoundException(person.toString()))
                .when(personService).deletePerson(person);

        this.mockMvc.perform(delete("/person").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Person not found : %s".formatted(person.toString())));
    }

    @Test
    public void updatePersonReturnOkTest() throws Exception {
        this.mockMvc.perform(post("/person").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(content().string("Person updated."));
    }

    @Test
    public void updatePersonReturnNotFoundTest() throws Exception {
        doThrow(new PersonNotFoundException(person.toString()))
                .when(personService).updatePerson(person);

        this.mockMvc.perform(post("/person").contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Person not found : %s".formatted(person.toString())));
    }
}
