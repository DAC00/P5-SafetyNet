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
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("PUT Request /medicalRecord {}",medicalRecord.toString());
        try {
            medicalRecordService.addMedicalRecord(medicalRecord);
            logger.info("PUT Request completed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord Added.");
        }catch (MedicalRecordAlreadyExistException e){
            return ResponseEntity.badRequest().body("MedicalRecord already exist.");
        }catch (NullPointerException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("MedicalRecord is null.");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("DELETE Request /medicalRecord {}",medicalRecord.toString());
        try {
            medicalRecordService.deleteMedicalRecord(medicalRecord);
            logger.info("DELETE Request completed successfully.");
            return ResponseEntity.noContent().build();
        }catch (MedicalRecordNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MedicalRecord not found.");
        }catch (NullPointerException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("MedicalRecord is null.");
        }
    }

    @PostMapping
    public ResponseEntity<String> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("POST Request /medicalRecord {}",medicalRecord);
        try {
            medicalRecordService.updateMedicalRecord(medicalRecord);
            logger.info("POST Request completed successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord updated.");
        }catch (MedicalRecordNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MedicalRecord not found.");
        }catch (NullPointerException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("MedicalRecord is null.");
        }
    }
}
