package com.opcr.safetynet_alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.Person;
import com.opcr.safetynet_alert.service.PersonService;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private JsonDataUtils jsonDataUtils;

    private static Person personTest;

    @BeforeAll
    static void setup() {
        personTest = new Person("Test0", "Test1", "Test2",
                "Test3", "000", "111", "Test6@mail.com");
    }

    @AfterEach
    public void cleanUp() {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("One", "Test", "11 Test St",
                "cityTest", "12345", "111-111-1111", "one@email.com"));
        persons.add(new Person("Three", "LeTest", "22 Test St",
                "cityTest", "12345", "333-333-3333", "three@email.com"));
        persons.add(new Person("Four", "Example", "33 Test St",
                "cityTest", "12345", "444-444-4444", "four@email.com"));
        persons.add(new Person("Two", "Test", "11 Test St",
                "cityTest", "12345", "222-222-2222", "two@email.com"));
        jsonDataUtils.updatePersons(persons);
    }

    @Test
    public void addPersonReturnCreatedTest() throws Exception {
        this.mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personTest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person added."));

        jsonDataUtils.getDataFromJSON();
        Assertions.assertTrue(jsonDataUtils.getPersons().contains(personTest));
    }

    @Test
    public void addPersonReturnBadRequestWhenAlreadyExistTest() throws Exception {
        Person personAlreadyExisting = new Person("Two", "Test", "11 Test St",
                "cityTest", "12345", "222-222-2222", "two@email.com");

        this.mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personAlreadyExisting)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Person already exist : %s".formatted(personAlreadyExisting.toString())));
    }

    @Test
    public void deletePersonReturnNoContent() throws Exception {
        Person personToDelete = new Person("Two", "Test", "11 Test St",
                "cityTest", "12345", "222-222-2222", "two@email.com");

        this.mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personToDelete)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Person deleted."));

        jsonDataUtils.getDataFromJSON();
        Assertions.assertFalse(jsonDataUtils.getPersons().contains(personToDelete));
    }

    @Test
    public void deletePersonReturnNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personTest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Person not found : %s".formatted(personTest.toString())));
    }

    @Test
    public void updatePersonReturnOkTest() throws Exception {
        Person personBefore = new Person("Two", "Test", "11 Test St",
                "cityTest", "12345", "222-222-2222", "two@email.com");
        Person personUpdated = new Person("Two", "Test", "11 Test St",
                "cityTest", "55555", "555-222-2222", "newemail@email.com");

        this.mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personUpdated)))
                .andExpect(status().isOk())
                .andExpect(content().string("Person updated."));

        jsonDataUtils.getDataFromJSON();
        Assertions.assertTrue(jsonDataUtils.getPersons().contains(personUpdated));
        Optional<Person> personFromDataOpt = jsonDataUtils.getPersons().stream().filter(p -> p.getLastName().equals(personUpdated.getLastName())
                && p.getFirstName().equals(personUpdated.getFirstName())).findFirst();
        Assertions.assertTrue(personFromDataOpt.isPresent());
        Assertions.assertEquals(personFromDataOpt.get().getAddress(), personUpdated.getAddress());
        Assertions.assertEquals(personFromDataOpt.get().getCity(), personUpdated.getCity());
        Assertions.assertEquals(personFromDataOpt.get().getZip(), personUpdated.getZip());
        Assertions.assertEquals(personFromDataOpt.get().getPhone(), personUpdated.getPhone());
        Assertions.assertEquals(personFromDataOpt.get().getEmail(), personUpdated.getEmail());
    }

    @Test
    public void updatePersonReturnNotFoundTest() throws Exception {
        this.mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personTest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Person not found : %s".formatted(personTest.toString())));
    }
}
