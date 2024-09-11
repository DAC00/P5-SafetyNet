package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MedicalRecordService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    public boolean addMedicalRecord(MedicalRecord medicalRecordToAdd) {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        if (!medicalRecords.contains(medicalRecordToAdd)) {
            medicalRecords.add(medicalRecordToAdd);
            jsonDataUtils.updateMedicalRecords(medicalRecords);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteMedicalRecord(MedicalRecord medicalRecordToDelete) {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        if (medicalRecords.contains(medicalRecordToDelete)) {
            medicalRecords.remove(medicalRecordToDelete);
            jsonDataUtils.updateMedicalRecords(medicalRecords);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        for (MedicalRecord mr : medicalRecords) {
            if (mr.getFirstName().equals(medicalRecord.getFirstName()) &&
                    mr.getLastName().equals(medicalRecord.getLastName())) {
                mr.setBirthdate(medicalRecord.getBirthdate());
                mr.setMedications(medicalRecord.getMedications());
                mr.setAllergies(medicalRecord.getAllergies());
                jsonDataUtils.updateMedicalRecords(medicalRecords);
                return true;
            }
        }
        return false;
    }
}
