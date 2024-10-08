package com.opcr.safetynet_alert.controller;

import com.opcr.safetynet_alert.exceptions.MedicalRecordAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordNotFoundException;
import com.opcr.safetynet_alert.model.MedicalRecord;
import com.opcr.safetynet_alert.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PutMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("PUT Request /medicalRecord : %s".formatted(medicalRecord.toString()));
        try {
            medicalRecordService.addMedicalRecord(medicalRecord);
            logger.info("PUT Request completed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord Added.");
        } catch (MedicalRecordAlreadyExistException e) {
            String errorMessage = "MedicalRecord already exist : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("DELETE Request /medicalRecord : %s".formatted(medicalRecord.toString()));
        try {
            medicalRecordService.deleteMedicalRecord(medicalRecord);
            logger.info("DELETE Request completed successfully.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("MedicalRecord deleted.");
        } catch (MedicalRecordNotFoundException e) {
            String errorMessage = "MedicalRecord not found : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping
    public ResponseEntity<String> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("POST Request /medicalRecord : %s".formatted(medicalRecord));
        try {
            medicalRecordService.updateMedicalRecord(medicalRecord);
            logger.info("POST Request completed successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord updated.");
        } catch (MedicalRecordNotFoundException e) {
            String errorMessage = "MedicalRecord not found : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
