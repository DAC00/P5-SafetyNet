package com.opcr.safetynet_alert.controller;

import com.opcr.safetynet_alert.exceptions.FireStationAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.FireStationNotFoundException;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private static final Logger logger = LogManager.getLogger(FireStationController.class);
    private final FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PutMapping
    public ResponseEntity<String> addFireStation(@RequestBody FireStation firestation) {
        logger.info("PUT Request /firestation {}", firestation.toString());
        try {
            fireStationService.addFireStation(firestation);
            logger.info("PUT Request completed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("FireStation added.");
        } catch (FireStationAlreadyExistException e) {
            String errorMessage = "FireStation already exist : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFireStation(@RequestBody FireStation firestation) {
        logger.info("DELETE Request /firestation {}", firestation.toString());
        try {
            fireStationService.deleteFireStation(firestation);
            logger.info("DELETE Request completed successfully.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("FireStation deleted.");
        } catch (FireStationNotFoundException e) {
            String errorMessage = "FireStation not found : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping
    public ResponseEntity<String> updateFireStation(@RequestBody FireStation firestation) {
        logger.info("POST Request /firestation {}", firestation);
        try {
            fireStationService.updateFireStation(firestation);
            logger.info("POST Request completed successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("FireStation updated.");
        } catch (FireStationNotFoundException e) {
            String errorMessage = "FireStation not found : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
