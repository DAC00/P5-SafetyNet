package com.opcr.safetynet_alert.controller;

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

    @Autowired
    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PutMapping
    public ResponseEntity<String> addFireStation(@RequestBody FireStation firestation){
        logger.info("PUT Request /firestation {}",firestation.toString());
        if (fireStationService.addFireStation(firestation)) {
            logger.info("PUT Request completed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("FireStation Added.");
        }else{
            logger.error("PUT Request failed.");
            return ResponseEntity.badRequest().body("FireStation already existing.");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFireStation(@RequestBody FireStation firestation){
        logger.info("DELETE Request /firestation {}",firestation.toString());
        if(fireStationService.deleteFireStation(firestation)){
            logger.info("DELETE Request completed successfully.");
            return ResponseEntity.noContent().build();
        }else{
            logger.error("DELETE Request failed.");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> updateFireStation(@RequestBody FireStation firestation){
        logger.info("POST Request /firestation {}",firestation);
        if(fireStationService.updateFireStation(firestation)){
            logger.info("POST Request completed successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("FireStation updated.");
        }else{
            logger.error("POST Request failed.");
            return ResponseEntity.notFound().build();
        }
    }
}
