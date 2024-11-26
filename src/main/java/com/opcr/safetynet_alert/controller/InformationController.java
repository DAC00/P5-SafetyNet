package com.opcr.safetynet_alert.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opcr.safetynet_alert.model.*;
import com.opcr.safetynet_alert.service.InformationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InformationController {

    private static final Logger logger = LogManager.getLogger(InformationController.class);

    @Autowired
    private InformationService informationService;

    @GetMapping("/firestation")
    public FireStationCoverage findPersonsFromFireStationNumber(@RequestParam(value = "stationNumber") int stationNumber) {
        logger.info("GET Request /firestation?stationNumber=%s".formatted(stationNumber));
        return informationService.findPersonsFromFireStationNumber(stationNumber);
    }

    @GetMapping("/childAlert")
    public ChildFromAddress findChildFromAddress(@RequestParam(value = "address") String address) {
        logger.info("GET Request /childAlert?address=%s".formatted(address));
        return informationService.findChildFromAddress(address);
    }

    @GetMapping("/phoneAlert")
    public List<String> findPhoneNumberOfPersonFromFireStation(@RequestParam(value = "firestation") int fireStationNumber) {
        logger.info("GET Request /phoneAlert?firestation=%s".formatted(fireStationNumber));
        return informationService.findPhoneNumberOfPersonFromFireStation(fireStationNumber);
    }

    @GetMapping("/fire")
    public HouseAndFireStationNumberFromAddress findPersonAndFireStationNumberFromAddress(@RequestParam(value = "address") String address) {
        logger.info("GET Request /fire?address=%s".formatted(address));
        return informationService.findPersonAndFireStationNumberFromAddress(address);
    }

    @GetMapping("/flood/stations")
    public List<HouseFromFireStationNumber> findPersonFromFireStationNumber(@RequestParam(value = "stations") List<Integer> stationNumbers) {
        logger.info("GET Request /flood/stations?stations=%s".formatted(stationNumbers));
        return informationService.findPersonFromFireStationNumber(stationNumbers);
    }

    @GetMapping("/personInfo")
    public List<PersonRecordMail> findPersonFromLastName(@RequestParam(value = "lastName") String lastName) {
        logger.info("GET Request /personInfo?lastName=%s".formatted(lastName));
        return informationService.findPersonFromLastName(lastName);
    }

    @GetMapping("/communityEmail")
    public List<String> findEmailFromCity(@RequestParam(value = "city") String city) {
        logger.info("GET Request /communityEmail?city=%s".formatted(city));
        return informationService.findEmailFromCity(city);
    }
}
