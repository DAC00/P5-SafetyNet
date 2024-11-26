package com.opcr.safetynet_alert.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findPersonsFromFireStationNumberFoundTest() throws Exception {
        this.mockMvc.perform(get("/firestation?stationNumber=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount").value("1"))
                .andExpect(jsonPath("$.childCount").value("1"))
                .andExpect(jsonPath("$.listPersonAddressPhoneList", hasSize(2)))
                .andExpect(jsonPath("$.listPersonAddressPhoneList[0].firstName", is("One")))
                .andExpect(jsonPath("$.listPersonAddressPhoneList[0].lastName", is("Test")))
                .andExpect(jsonPath("$.listPersonAddressPhoneList[0].address", is("11 Test St")))
                .andExpect(jsonPath("$.listPersonAddressPhoneList[0].phone", is("111-111-1111")))
                .andExpect(jsonPath("$.listPersonAddressPhoneList[1].firstName", is("Two")))
                .andExpect(jsonPath("$.listPersonAddressPhoneList[1].lastName", is("Test")))
                .andExpect(jsonPath("$.listPersonAddressPhoneList[1].address", is("11 Test St")))
                .andExpect(jsonPath("$.listPersonAddressPhoneList[1].phone", is("222-222-2222")));
    }

    @Test
    public void findPersonsFromFireStationNumberEmptyTest() throws Exception {
        this.mockMvc.perform(get("/firestation?stationNumber=99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void findChildFromAddressFoundTest() throws Exception {
        this.mockMvc.perform(get("/childAlert?address=11 Test St")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children", hasSize(1)))
                .andExpect(jsonPath("$.children[0].lastName", is("Test")))
                .andExpect(jsonPath("$.children[0].firstName", is("Two")))
                .andExpect(jsonPath("$.children[0].age", is(4)))
                .andExpect(jsonPath("$.adults", hasSize(1)))
                .andExpect(jsonPath("$.adults[0].lastName", is("Test")))
                .andExpect(jsonPath("$.adults[0].firstName", is("One")));
    }

    @Test
    public void findChildFromAddressEmptyTest() throws Exception {
        this.mockMvc.perform(get("/childAlert?address=no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void findPhoneNumberOfPersonFromFireStationFoundTest() throws Exception {
        this.mockMvc.perform(get("/phoneAlert?firestation=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("111-111-1111")))
                .andExpect(jsonPath("$[1]", is("222-222-2222")));
    }

    @Test
    public void findPhoneNumberOfPersonFromFireStationEmptyTest() throws Exception {
        this.mockMvc.perform(get("/phoneAlert?firestation=99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void findPersonAndFireStationNumberFromAddressFoundTest() throws Exception {
        this.mockMvc.perform(get("/fire?address=22 Test St")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fireStationNumber", is(2)))
                .andExpect(jsonPath("$.persons", hasSize(1)))
                .andExpect(jsonPath("$.persons[0].firstName", is("Three")))
                .andExpect(jsonPath("$.persons[0].lastName", is("LeTest")))
                .andExpect(jsonPath("$.persons[0].phone", is("333-333-3333")))
                .andExpect(jsonPath("$.persons[0].age", is(91)))
                .andExpect(jsonPath("$.persons[0].medications", hasSize(0)))
                .andExpect(jsonPath("$.persons[0].allergies", hasSize(1)))
                .andExpect(jsonPath("$.persons[0].allergies[0]", is("peanut")));
    }

    @Test
    public void findPersonAndFireStationNumberFromAddressEmptyTest() throws Exception {
        this.mockMvc.perform(get("/fire?address=no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void findPersonFromFireStationNumberFoundTest() throws Exception {
        this.mockMvc.perform(get("/flood/stations?stations=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].address", is("22 Test St")))
                .andExpect(jsonPath("$[0].persons[0].lastName", is("LeTest")))
                .andExpect(jsonPath("$[0].persons[0].firstName", is("Three")))
                .andExpect(jsonPath("$[0].persons[0].age", is(91)))
                .andExpect(jsonPath("$[0].persons[0].medications", hasSize(0)))
                .andExpect(jsonPath("$[0].persons[0].allergies", hasSize(1)))
                .andExpect(jsonPath("$[0].persons[0].allergies[0]", is("peanut")))
                .andExpect(jsonPath("$[1].address", is("33 Test St")))
                .andExpect(jsonPath("$[1].persons[0].lastName", is("Example")))
                .andExpect(jsonPath("$[1].persons[0].firstName", is("Four")))
                .andExpect(jsonPath("$[1].persons[0].age", is(80)))
                .andExpect(jsonPath("$[1].persons[0].medications", hasSize(0)))
                .andExpect(jsonPath("$[1].persons[0].allergies", hasSize(0)));
    }

    @Test
    public void findPersonFromFireStationNumberEmptyTest() throws Exception {
        this.mockMvc.perform(get("/flood/stations?stations=3,4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void findPersonFromLastNameFoundTest() throws Exception {
        this.mockMvc.perform(get("/personInfo?lastName=Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].lastName", is("Test")))
                .andExpect(jsonPath("$[0].firstName", is("One")))
                .andExpect(jsonPath("$[0].mail", is("one@email.com")))
                .andExpect(jsonPath("$[0].age", is(43)))
                .andExpect(jsonPath("$[0].medications", hasSize(2)))
                .andExpect(jsonPath("$[0].allergies", hasSize(1)))
                .andExpect(jsonPath("$[1].lastName", is("Test")))
                .andExpect(jsonPath("$[1].firstName", is("Two")))
                .andExpect(jsonPath("$[1].mail", is("two@email.com")))
                .andExpect(jsonPath("$[1].age", is(4)))
                .andExpect(jsonPath("$[1].medications", hasSize(3)))
                .andExpect(jsonPath("$[1].allergies", hasSize(0)));
    }

    @Test
    public void findPersonFromLastNameEmptyTest() throws Exception {
        this.mockMvc.perform(get("/personInfo?lastName=Bob")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void findEmailFromCityFoundTest() throws Exception {
        this.mockMvc.perform(get("/communityEmail?city=cityTest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0]", is("one@email.com")));
    }

    @Test
    public void findEmailFromCityEmptyTest() throws Exception {
        this.mockMvc.perform(get("/communityEmail?city=no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
