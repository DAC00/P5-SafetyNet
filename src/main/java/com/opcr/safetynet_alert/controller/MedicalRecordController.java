package com.opcr.safetynet_alert.controller;

import com.opcr.safetynet_alert.model.MedicalRecord;
import com.opcr.safetynet_alert.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PutMapping
    public void addMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @DeleteMapping
    public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordService.deleteMedicalRecord(medicalRecord);
    }

    @PostMapping
    public void updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordService.updateMedicalRecord(medicalRecord);
    }
}
