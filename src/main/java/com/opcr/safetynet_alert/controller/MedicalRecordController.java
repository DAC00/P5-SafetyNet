package com.opcr.safetynet_alert.controller;

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

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PutMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("PUT Request /medicalRecord {}",medicalRecord.toString());
        if (medicalRecordService.addMedicalRecord(medicalRecord)) {
            logger.info("PUT Request completed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord Added.");
        }else{
            logger.error("PUT Request failed.");
            return ResponseEntity.badRequest().body("MedicalRecord already existing.");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("DELETE Request /medicalRecord {}",medicalRecord.toString());
        if(medicalRecordService.deleteMedicalRecord(medicalRecord)){
            logger.info("DELETE Request completed successfully.");
            return ResponseEntity.noContent().build();
        }else{
            logger.error("DELETE Request failed.");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("POST Request /medicalRecord {}",medicalRecord);
        if(medicalRecordService.updateMedicalRecord(medicalRecord)){
            logger.info("POST Request completed successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord updated.");
        }else{
            logger.error("POST Request failed.");
            return ResponseEntity.notFound().build();
        }
    }
}
