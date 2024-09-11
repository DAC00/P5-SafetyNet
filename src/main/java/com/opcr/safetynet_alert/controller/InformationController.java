package com.opcr.safetynet_alert.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opcr.safetynet_alert.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class InformationController {

    private static final Logger logger = LogManager.getLogger(InformationController.class);

    @Autowired
    private final InformationService informationService;

    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/firestation")
    public ObjectNode findPersonsFromFireStationNumber(@RequestParam(value = "stationNumber") int stationNumber) {
        logger.info("GET Request /firestation?stationNumber={}",stationNumber);
        return informationService.findPersonsFromFireStationNumber(stationNumber);
    }

    @GetMapping("/childAlert")
    public ObjectNode findChildFromAddress(@RequestParam(value = "address") String address) {
        logger.info("GET Request /childAlert?address={}",address);
        return informationService.findChildFromAddress(address);
    }

    @GetMapping("/phoneAlert")
    public ArrayList<String> findPhoneNumberOfPersonFromFireStation(@RequestParam(value = "firestation") int fireStationNumber) {
        logger.info("GET Request /phoneAlert?firestation={}",fireStationNumber);
        return informationService.findPhoneNumberOfPersonFromFireStation(fireStationNumber);
    }

    @GetMapping("/fire")
    public ObjectNode findPersonAndFireStationNumberFromAddress(@RequestParam(value = "address") String address) {
        logger.info("GET Request /fire?address={}",address);
        return informationService.findPersonAndFireStationNumberFromAddress(address);
    }

    @GetMapping("/flood/stations")
    public ArrayNode findPersonFromFireStationNumber(@RequestParam(value = "stations") int stationNumbers) {
        logger.info("GET Request /flood/stations?stations={}",stationNumbers);
        return informationService.findPersonFromFireStationNumber(stationNumbers);

    }

    @GetMapping("/personInfolastName")
    public ObjectNode findPersonFromLastName(@RequestParam(value = "lastName") String lastName) {
        logger.info("GET Request /personInfolastName?lastName={}",lastName);
        return informationService.findPersonFromLastName(lastName);
    }

    @GetMapping("/communityEmail")
    public ArrayList<String> findEmailFromCity(@RequestParam(value = "city") String city) {
        logger.info("GET Request /communityEmail?city={}",city);
        return informationService.findEmailFromCity(city);
    }
}
